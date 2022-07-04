package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.AppointmentType;
import de.phoenix.wgtest.model.management.Child;
import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CreateAppointmentRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String startDate;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endDate;

    @NotBlank
    private String endTime;

    @NotBlank
    private String location;

    @NotBlank
    private AppointmentType appointmentType;

    @NotBlank
    private String hasInterval;

    @NotBlank
    private String interval;

    @NotBlank
    private String intervalEnd;

    private LivingGroup livingGroup;

    private List<Person> employees;

    private List<Child> children;

    public CreateAppointmentRequest() {
    }

    public CreateAppointmentRequest(String title, String startDate, String startTime, String endDate, String endTime,
                                    String location, AppointmentType appointmentType, String hasInterval, String interval,
                                    String intervalEnd, LivingGroup livingGroup, List<Person> employees,
                                    List<Child> children) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.location = location;
        this.appointmentType = appointmentType;
        this.hasInterval = hasInterval;
        this.interval = interval;
        this.intervalEnd = intervalEnd;
        this.livingGroup = livingGroup;
        this.employees = employees;
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String hasInterval() {
        return hasInterval;
    }

    public void setHasInterval(String hasInterval) {
        this.hasInterval = hasInterval;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(String intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public LivingGroup getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(LivingGroup livingGroup) {
        this.livingGroup = livingGroup;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
