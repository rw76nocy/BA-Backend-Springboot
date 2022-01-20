package de.phoenix.wgtest.model.management;

import javax.persistence.*;

@Entity
public class Supply {

    @EmbeddedId
    SupplyPK id;

    @Column(name = "customer_number")
    private String customerNumber;

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("foodSupplierId")
    @JoinColumn(name = "food_supplier_id")
    FoodSupplier foodSupplier;

    public Supply() {

    }

    public Supply(String costumerNumber, Child child, FoodSupplier foodSupplier, String customerNumber) {
        this.child = child;
        this.foodSupplier = foodSupplier;
        this.customerNumber = customerNumber;
    }

    public SupplyPK getId() {
        return id;
    }

    public void setId(SupplyPK id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public FoodSupplier getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplier foodSupplier) {
        this.foodSupplier = foodSupplier;
    }
}
