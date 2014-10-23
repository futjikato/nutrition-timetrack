package de.futjikato.nutrition.timetrek.modules.times;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;

@Entity(name = "Time")
@Table(appliesTo = "Time")
public class TimeEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "id", strategy = "increment")
    private Long id;

    @Column(nullable = false)
    private Long fk_project;

    @Column(nullable = false)
    private Long fk_user;

    @Column(nullable = false)
    private Calendar filed;

    @Column(nullable = false)
    private Long duration;

    @OneToMany(mappedBy = "timeEntity", targetEntity = TimeMetaValueEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<TimeMetaValueEntity> metaInformations;

    public Long getId() {
        return id;
    }

    public Long getFk_project() {
        return fk_project;
    }

    public void setFk_project(Long fk_project) {
        this.fk_project = fk_project;
    }

    public Long getFk_user() {
        return fk_user;
    }

    public void setFk_user(Long fk_user) {
        this.fk_user = fk_user;
    }

    public Calendar getFiled() {
        return filed;
    }

    public void setFiled(Calendar filed) {
        this.filed = filed;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Collection<TimeMetaValueEntity> getMetaInformations() {
        return metaInformations;
    }

    public void setMetaInformations(Collection<TimeMetaValueEntity> metaInformations) {
        this.metaInformations = metaInformations;
    }
}