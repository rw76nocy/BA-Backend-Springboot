package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.FoodSupplier;
import de.phoenix.wgtest.model.management.ReferenceObject;

public class FoodSupplierRequest extends SpecifiedInstitutionRequest  implements ReferenceObject {

    private String cNumber;
    private String pin;

    public FoodSupplierRequest() {
    }

    public FoodSupplierRequest(String name, Address address, String phone, String fax, String email, String cNumber, String pin) {
        super(name, address, phone, fax, email);
        this.cNumber = cNumber;
        this.pin = pin;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public FoodSupplier getFoodSupplier() {
        return new FoodSupplier(getName(), getPhone(), getFax(), getEmail(), getAddress());
    }
}
