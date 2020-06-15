package ru.itmo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scripts",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "airDate")
})
public class Script {

    @Id
    @SequenceGenerator(name = "script_seq", sequenceName = "script_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "script_seq")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date airDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModificationDate;

    private String text;

    public Script() {
    }

    public Script(Date airDate, Date lastModificationDate, String text) {
        this.airDate = airDate;
        this.lastModificationDate = lastModificationDate;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAirDate() {
        return airDate;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
