package de.phoenix.wgtest.payload.response;

public class ChildResponse {

    private String message;
    private Long childId;

    public ChildResponse(String message, Long childId) {
        this.message = message;
        this.childId = childId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
}
