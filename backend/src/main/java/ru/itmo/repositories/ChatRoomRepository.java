package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.Application;
import ru.itmo.models.ChatRoom;
import ru.itmo.models.EApplicationType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findChatRoomBySender_IdAndRecipient_Id(Long senderId, Long recipientId);
}