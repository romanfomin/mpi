package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.*;
import ru.itmo.repositories.ChatMessageRepository;
import ru.itmo.repositories.ChatRoomRepository;
import ru.itmo.repositories.MessageStatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    @Autowired
    private MessageStatusRepository messageStatusRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserService userService;

    public MessageStatus getStatusByName(EMessageStatus status) throws ResourceNotFoundException {
        return messageStatusRepository.findByName(status)
                .orElseThrow(() -> new ResourceNotFoundException("MessageStatus not found"));
    }

    public ChatMessage save(ChatMessage chatMessage) throws ResourceNotFoundException {
        chatMessage.setStatus(getStatusByName(EMessageStatus.RECEIVED));
        return chatMessageRepository.save(chatMessage);
    }

    public long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countBySender_IdAndRecipient_IdAndStatus_Name(
                senderId, recipientId, EMessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) throws ResourceNotFoundException {
        User sender = userService.getById(senderId);
        User recipient = userService.getById(recipientId);
        Optional<String> chatId = chatRoomService.getChatId(sender, recipient, false);

        List<ChatMessage> messages = chatId.map(cId -> chatMessageRepository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, EMessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessage findById(Long id) throws ResourceNotFoundException {
        ChatMessage chatMessage = chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Error: ChatMessage is not found."));
        chatMessage.setStatus(getStatusByName(EMessageStatus.DELIVERED));
        return chatMessageRepository.save(chatMessage);
    }

    public void updateStatuses(Long senderId, Long recipientId, EMessageStatus status) throws ResourceNotFoundException {
        List<ChatMessage> messages = chatMessageRepository.findBySender_IdAndRecipient_Id(senderId, recipientId);
        MessageStatus storedStatus = getStatusByName(status);
        for (ChatMessage chatMessage: messages) {
            chatMessage.setStatus(storedStatus);
            chatMessageRepository.save(chatMessage);
        }
    }
}
