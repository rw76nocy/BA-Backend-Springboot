package de.phoenix.wgtest.model.management;

import javax.persistence.*;

@Entity
public class Supply {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_supplier_id")
    private FoodSupplier foodSupplier;

    @Column(name = "customerNumber")
    private String customerNumber;

    public Supply() {

    }

    public Supply(FoodSupplier foodSupplier, String customerNumber) {
        this.foodSupplier = foodSupplier;
        this.customerNumber = customerNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public FoodSupplier getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplier foodSupplier) {
        this.foodSupplier = foodSupplier;
    }
}
