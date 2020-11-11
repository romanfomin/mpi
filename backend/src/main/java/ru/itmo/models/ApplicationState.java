package ru.itmo.models;

import javax.persistence.*;

@Entity
@Table(name = "application_states")
public class ApplicationState {

    @Id
    @SequenceGenerator(name = "application_state_seq", sequenceName = "application_state_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_state_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EApplicationState name;

    public ApplicationState() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EApplicationState getName() {
        return name;
    }

    public void setName(EApplicationState name) {
        this.name = name;
    }
}
