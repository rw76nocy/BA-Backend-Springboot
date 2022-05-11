package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.FoodSupplier;
import de.phoenix.wgtest.model.management.ReferenceObject;

public class FoodSupplierRequest implements ReferenceObject {

    private Long id;
    private String name;
    private Address address;
    private String phone;
    private String fax;
    private String email;
    private String cNumber;
    private String pin;

    public FoodSupplierRequest() {
    }

    public FoodSupplierRequest(String name, Address address, String phone, String fax, String email, String cNumber, String pin) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.cNumber = cNumber;
        this.pin = pin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
