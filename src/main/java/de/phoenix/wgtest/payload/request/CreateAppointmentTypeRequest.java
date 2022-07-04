package de.phoenix.wgtest.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateAppointmentTypeRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @NotBlank
    private String livingGroup;

    public CreateAppointmentTypeRequest() {
    }

    public CreateAppointmentTypeRequest(String name, String color, String livingGroup) {
        this.name = name;
        this.color = color;
        this.livingGroup = livingGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(String livingGroup) {
        this.livingGroup = livingGroup;
    }
}
