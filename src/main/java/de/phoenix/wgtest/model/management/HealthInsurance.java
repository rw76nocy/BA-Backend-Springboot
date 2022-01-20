package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity( name = "healthinsurance")
@Table( name = "health_insurance")
public class HealthInsurance extends Institution {

    @OneToMany(mappedBy = "healthInsurance")
    private Set<Insured> insureds = new HashSet<>();

    public HealthInsurance() {

    }

    public HealthInsurance(String name, String phone, String email, Address address, Set<InstitutionRole> institutionRoles) {
        super(name, phone, email, address, institutionRoles);
    }

    public Set<Insured> getInsureds() {
        return insureds;
    }

    public void setInsureds(Set<Insured> insureds) {
        this.insureds = insureds;
    }
}
