package ru.itmo.models;

import javax.persistence.*;

@Entity
@Table(name = "file_types")
public class FileType {

    @Id
    @SequenceGenerator(name = "file_type_seq", sequenceName = "file_type_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_type_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EFileType name;

    public FileType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EFileType getName() {
        return name;
    }

    public void setName(EFileType name) {
        this.name = name;
    }
}
