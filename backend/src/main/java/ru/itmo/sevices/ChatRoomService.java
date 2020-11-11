package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.models.ChatRoom;
import ru.itmo.models.User;
import ru.itmo.repositories.ChatRoomRepository;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(User sender, User recipient, boolean createIfNotExist) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findChatRoomBySender_IdAndRecipient_Id(sender.getId(), recipient.getId());
        if (chatRoom.isPresent())
            return Optional.ofNullable(chatRoom.get().getChatId());

        if (!createIfNotExist)
            return Optional.empty();

        String chatId = String.format("%d_%d", sender.getId(), recipient.getId());
        ChatRoom senderRecipient = new ChatRoom(chatId, sender, recipient);
        ChatRoom recipientSender = new ChatRoom(chatId, recipient, sender);

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return Optional.of(chatId);

    }
}
