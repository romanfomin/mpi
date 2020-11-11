package ru.itmo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @SequenceGenerator(name = "chat_message_seq", sequenceName = "chat_message_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_seq")
    private Long id;

    private String chatId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Temporal(TemporalType.DATE)
    private Date timestamp;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private MessageStatus status;

    private String content;

    public ChatMessage() {
    }

    public ChatMessage(ChatMessageWS chatMessageWS) {
        this.chatId = chatMessageWS.getChatId();
        this.timestamp = chatMessageWS.getTimestamp();
        this.content = chatMessageWS.getContent();
    }

    public ChatMessage(String chatId, User sender, User recipient, Date timestamp, MessageStatus status, String content) {
        this.chatId = chatId;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.status = status;
        this.content = content;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}