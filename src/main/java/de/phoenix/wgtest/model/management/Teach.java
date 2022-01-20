package de.phoenix.wgtest.model.management;

import javax.persistence.*;

@Entity
public class Teach {

    @EmbeddedId
    TeachPK id;

    @Column(name = "dayCareGroup")
    private String dayCareGroup;

    @Column(name = "dayCareTeacher")
    private String dayCareTeacher;

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("dayCareId")
    @JoinColumn(name = "day_care_id")
    DayCare dayCare;

    public Teach() {

    }

    public Teach(Child child, DayCare dayCare, String dayCareGroup, String dayCareTeacher) {
        this.child = child;
        this.dayCare = dayCare;
        this.dayCareGroup = dayCareGroup;
        this.dayCareTeacher = dayCareTeacher;
    }

    public TeachPK getId() {
        return id;
    }

    public void setId(TeachPK id) {
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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public DayCare getDayCare() {
        return dayCare;
    }

    public void setDayCare(DayCare dayCare) {
        this.dayCare = dayCare;
    }
}
