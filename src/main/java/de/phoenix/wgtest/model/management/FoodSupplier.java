package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity( name = "foodsupplier")
@Table( name = "food_supplier")
public class FoodSupplier extends Institution {

    @OneToMany(mappedBy = "foodSupplier")
    private Set<Supply> supplies = new HashSet<>();

    public FoodSupplier() {

    }

    public FoodSupplier(String name, String phone, String email, Address address,
                        Set<InstitutionRole> institutionRoles) {
        super(name, phone, email, address, institutionRoles);
    }

    public Set<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(Set<Supply> supplies) {
        this.supplies = supplies;
    }
}
