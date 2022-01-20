package de.phoenix.wgtest.model.management;

import javax.persistence.*;

@Entity
public class Insured {

    @EmbeddedId
    InsuredPK id;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "holder")
    private String holder;

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("healthInsuranceId")
    @JoinColumn(name = "health_insurance_id")
    HealthInsurance healthInsurance;

    public Insured() {

    }

    public Insured(Child child, HealthInsurance healthInsurance, String customerNumber, String holder) {
        this.child = child;
        this.healthInsurance = healthInsurance;
        this.customerNumber = customerNumber;
        this.holder = holder;
    }

    public InsuredPK getId() {
        return id;
    }

    public void setId(InsuredPK id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public HealthInsurance getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(HealthInsurance healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
}
