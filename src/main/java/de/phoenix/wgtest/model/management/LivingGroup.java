package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "LivingGroup")
public class LivingGroup {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @OneToMany( mappedBy = "livingGroup", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    private List<Child> children = new ArrayList<>();

    @OneToMany( mappedBy = "livingGroup", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    private List<Person> employees = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH}, orphanRemoval = true)
    @JoinColumn(name = "appointment_type_id", nullable = true)
    private AppointmentType defaultType;

    @OneToMany( mappedBy = "livingGroup", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnore
    private List<AppointmentType> appointmentTypes = new ArrayList<>();

    public LivingGroup() {

    }

    public LivingGroup(String name, List<Child> children, List<Person> employees, AppointmentType defaultType,
                       List<AppointmentType> appointmentTypes) {
        this.name = name;
        this.children = children;
        this.employees = employees;
        this.defaultType = defaultType;
        this.appointmentTypes = appointmentTypes;
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

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }

    public AppointmentType getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(AppointmentType defaultType) {
        this.defaultType = defaultType;
    }

    public List<AppointmentType> getAppointmentTypes() {
        return appointmentTypes;
    }

    public void setAppointmentTypes(List<AppointmentType> appointmentTypes) {
        this.appointmentTypes = appointmentTypes;
    }
}
