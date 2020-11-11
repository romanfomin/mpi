package ru.itmo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @SequenceGenerator(name = "chat_room_seq", sequenceName = "chat_room_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_room_seq")
    private Long id;

    private String chatId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    public ChatRoom() {
    }

    public ChatRoom(String chatId, User sender, User recipient) {
        this.chatId = chatId;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}