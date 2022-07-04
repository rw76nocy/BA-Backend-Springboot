package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppointmentType {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 500)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String color;

    @ManyToOne( fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "living_group_id", nullable = true)
    @JsonIgnore
    private LivingGroup livingGroup;

    @OneToMany(mappedBy = "appointmentType", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    public AppointmentType() {
    }

    public AppointmentType(String name, String color, LivingGroup livingGroup, List<Appointment> appointments) {
        this.name = name;
        this.color = color;
        this.livingGroup = livingGroup;
        this.appointments = appointments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LivingGroup getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(LivingGroup livingGroup) {
        this.livingGroup = livingGroup;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
