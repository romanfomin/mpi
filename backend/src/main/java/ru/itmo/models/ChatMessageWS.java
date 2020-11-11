package ru.itmo.models;

import java.util.Date;

public class ChatMessageWS {

    private Long id;
    private String chatId;
    private Long senderId;
    private Long recipientId;
    private Date timestamp;
    private EMessageStatus status;
    private String content;

    public ChatMessageWS() {
    }

    public ChatMessageWS(Long id, String chatId, Long senderId, Long recipientId, Date timestamp, EMessageStatus status, String content) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public EMessageStatus getStatus() {
        return status;
    }

    public void setStatus(EMessageStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}