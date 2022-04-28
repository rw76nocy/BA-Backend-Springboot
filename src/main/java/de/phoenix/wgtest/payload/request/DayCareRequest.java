package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;

public class DayCareRequest extends SpecifiedInstitutionRequest {

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
}
