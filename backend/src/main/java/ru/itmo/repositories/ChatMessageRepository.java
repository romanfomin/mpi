package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.ChatMessage;
import ru.itmo.models.EMessageStatus;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    long countBySender_IdAndRecipient_IdAndStatus_Name(
            Long senderId, Long recipientId, EMessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    List<ChatMessage> findBySender_IdAndRecipient_Id(Long senderId, Long recipientId);
}