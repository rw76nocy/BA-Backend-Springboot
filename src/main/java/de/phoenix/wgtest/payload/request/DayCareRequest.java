package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.DayCare;
import de.phoenix.wgtest.model.management.ReferenceObject;

import java.util.Objects;

public class DayCareRequest implements ReferenceObject {

    private Long id;
    private String name;
    private Address address;
    private String phone;
    private String fax;
    private String email;
    private String teacher;
    private String group;

    public DayCareRequest() {
    }

    public DayCareRequest(String name, Address address, String phone, String fax, String email, String teacher, String group) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.teacher = teacher;
        this.group = group;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public DayCare getDayCare() {
        return new DayCare(name, phone, fax, email, address);
    }
}
