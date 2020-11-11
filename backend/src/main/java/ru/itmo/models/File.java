package ru.itmo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {
    @Id
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;

    private String name;

    private String contentType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "file_type_id", nullable = false)
    private FileType fileType;

    @Lob
    @JsonIgnore
    private byte[] data;

    public File() {
    }

    public File(String name, String contentType, FileType fileType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.fileType = fileType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}