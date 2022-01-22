package de.phoenix.wgtest.model.management;

import javax.persistence.*;

@Entity
public class Teach {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "day_care_id")
    private DayCare dayCare;

    @Column(name = "dayCareGroup")
    private String dayCareGroup;

    @Column(name = "dayCareTeacher")
    private String dayCareTeacher;

    public Teach() {

    }

    public Teach(DayCare dayCare, String dayCareGroup, String dayCareTeacher) {
        this.dayCare = dayCare;
        this.dayCareGroup = dayCareGroup;
        this.dayCareTeacher = dayCareTeacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayCareGroup() {
        return dayCareGroup;
    }

    public void setDayCareGroup(String dayCareGroup) {
        this.dayCareGroup = dayCareGroup;
    }

    public String getDayCareTeacher() {
        return dayCareTeacher;
    }

    public void setDayCareTeacher(String dayCareTeacher) {
        this.dayCareTeacher = dayCareTeacher;
    }

    public DayCare getDayCare() {
        return dayCare;
    }

    public void setDayCare(DayCare dayCare) {
        this.dayCare = dayCare;
    }
}
