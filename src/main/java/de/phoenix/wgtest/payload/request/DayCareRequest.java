package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.DayCare;
import de.phoenix.wgtest.model.management.ReferenceObject;

public class DayCareRequest extends SpecifiedInstitutionRequest implements ReferenceObject {

    private String teacher;
    private String group;

    public DayCareRequest() {
    }

    public DayCareRequest(String name, Address address, String phone, String fax, String email, String teacher, String group) {
        super(name, address, phone, fax, email);
        this.teacher = teacher;
        this.group = group;
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
        return new DayCare(getName(), getPhone(), getFax(), getEmail(), getAddress());
    }
}
