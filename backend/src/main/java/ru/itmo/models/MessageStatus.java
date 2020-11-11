package ru.itmo.models;

import javax.persistence.*;

@Entity
@Table(name = "message_statuses")
public class MessageStatus {

    @Id
    @SequenceGenerator(name = "message_status_seq", sequenceName = "message_status_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_status_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EMessageStatus name;

    public MessageStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EMessageStatus getName() {
        return name;
    }

    public void setName(EMessageStatus name) {
        this.name = name;
    }
}
