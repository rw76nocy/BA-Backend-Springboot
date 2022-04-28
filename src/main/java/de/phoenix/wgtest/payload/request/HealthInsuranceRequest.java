package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;

public class HealthInsuranceRequest extends SpecifiedInstitutionRequest {

    private String holder;
    private String cNumber;

    public HealthInsuranceRequest() {
    }

    public HealthInsuranceRequest(String name, Address address, String phone, String fax, String email, String holder, String cNumber) {
        super(name, address, phone, fax, email);
        this.holder = holder;
        this.cNumber = cNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }
}
