package de.phoenix.wgtest.payload.response;

public class AddAppointmentResponse {
    private String message;
    private Long id;

    public AddAppointmentResponse(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
