package de.futjikato.nutrition.timetrek.modules.times;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "TimeMeta")
@Table(appliesTo = "TimeMeta")
public class TimeMetaValueEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "id", strategy = "increment")
    private Long id;

    @ManyToOne(targetEntity = TimeEntity.class, fetch = FetchType.EAGER, optional = false)
    private TimeEntity timeEntity;

    @ManyToOne(targetEntity = TimeMetaEntity.class, fetch = FetchType.EAGER, optional = false)
    private TimeMetaEntity metaEntity;

    @Column
    @Type(type = "text")
    private String value;

    public Long getId() {
        return id;
    }

    public TimeEntity getTimeEntity() {
        return timeEntity;
    }

    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

    public TimeMetaEntity getMetaEntity() {
        return metaEntity;
    }

    public void setMetaEntity(TimeMetaEntity metaEntity) {
        this.metaEntity = metaEntity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
