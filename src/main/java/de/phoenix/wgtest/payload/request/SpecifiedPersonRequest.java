package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.management.ReferenceObject;

import java.util.Date;

public class SpecifiedPersonRequest implements ReferenceObject {

    private String type;
    private String name;
    private Date birthday;
    private Address address;
    private String phone;
    private String fax;
    private String email;

    public SpecifiedPersonRequest() {
    }

    public SpecifiedPersonRequest(String type, String name, Date birthday, Address address, String phone, String fax, String email) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Person getPerson() {
        return new Person(name, phone, fax, email, birthday, address);
    }
}
