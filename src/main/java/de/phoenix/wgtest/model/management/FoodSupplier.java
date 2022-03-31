package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity( name = "foodsupplier")
@Table( name = "food_supplier")
public class FoodSupplier extends Institution {

    @OneToMany(mappedBy = "foodSupplier", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Supply> supplies = new ArrayList<>();

    public FoodSupplier() {

    }

    public FoodSupplier(String name, String phone, String email, Address address,
                        List<InstitutionRole> institutionRoles) {
        super(name, phone, email, address, institutionRoles);
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }
}
