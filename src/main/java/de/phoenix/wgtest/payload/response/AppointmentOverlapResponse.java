package de.phoenix.wgtest.payload.response;

import java.util.Date;
import java.util.Set;

public class AppointmentOverlapResponse {

    private String title;
    private Date startDate;
    private Date endDate;
    private Set<String> members;
    private Set<String> children;

    public AppointmentOverlapResponse(String title, Date startDate, Date endDate,
                                      Set<String> members, Set<String> children) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
        this.children = children;
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

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public Set<String> getChildren() {
        return children;
    }

    public void setChildren(Set<String> children) {
        this.children = children;
    }
}
