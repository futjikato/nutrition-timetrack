package de.futjikato.nutrition.timetrek.modules.times;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TimeMeta")
@Table(appliesTo = "TimeMeta")
public class TimeMetaEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "id", strategy = "increment")
    private Long id;

    @Column
    private String name;
}
