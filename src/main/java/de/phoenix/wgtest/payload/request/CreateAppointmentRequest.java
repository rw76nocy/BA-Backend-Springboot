package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.AppointmentType;
import de.phoenix.wgtest.model.management.Child;
import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class CreateAppointmentRequest {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private Date startDate;

    @NotBlank
    private Date endDate;

    private String location;

    @NotBlank
    private AppointmentType appointmentType;

    private String rRule;

    private LivingGroup livingGroup;

    @NotBlank
    private List<String> members;

    private List<String> children;

    public CreateAppointmentRequest() {
    }

    public CreateAppointmentRequest(Long id, String title, Date startDate, Date endDate, String location,
                                    AppointmentType appointmentType, String rRule, LivingGroup livingGroup,
                                    List<String> members, List<String> children) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.appointmentType = appointmentType;
        this.rRule = rRule;
        this.livingGroup = livingGroup;
        this.members = members;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getrRule() {
        return rRule;
    }

    public void setrRule(String rRule) {
        this.rRule = rRule;
    }

    public LivingGroup getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(LivingGroup livingGroup) {
        this.livingGroup = livingGroup;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
