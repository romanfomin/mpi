package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.*;
import ru.itmo.sevices.ChatMessageService;
import ru.itmo.sevices.ChatRoomService;
import ru.itmo.sevices.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/api/messages")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserService userService;

    @MessageMapping("/chat")
    public void processMessage(ChatMessageWS chatMessageWS) throws ResourceNotFoundException {
        User sender = userService.getById(chatMessageWS.getSenderId());
        User recipient = userService.getById(chatMessageWS.getRecipientId());
        Optional<String> chatId = chatRoomService.getChatId(sender, recipient, true);
        MessageStatus status = chatMessageService.getStatusByName(EMessageStatus.DELIVERED);

        ChatMessage chatMessage = new ChatMessage(chatMessageWS);
        chatMessage.setSender(sender);
        chatMessage.setRecipient(recipient);
        chatMessage.setChatId(chatId.get());
        chatMessage.setStatus(status);

        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(saved.getRecipient().getId()),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSender().getId(),
                        saved.getSender().getUsername()));
    }

    @GetMapping("/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long senderId,
                                                @PathVariable Long recipientId) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
