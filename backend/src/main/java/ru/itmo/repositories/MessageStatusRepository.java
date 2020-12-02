package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.EMessageStatus;
import ru.itmo.models.MessageStatus;

import java.util.Optional;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
    Optional<MessageStatus> findByName(EMessageStatus status);
}