package ru.itmo.models;

import javax.persistence.*;

@Entity
@Table(name = "application_types")
public class ApplicationType {

    @Id
    @SequenceGenerator(name = "application_type_seq", sequenceName = "application_type_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_type_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EApplicationType name;

    public ApplicationType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EApplicationType getName() {
        return name;
    }

    public void setName(EApplicationType name) {
        this.name = name;
    }
}
