package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity( name = "healthinsurance")
@Table( name = "health_insurance")
public class HealthInsurance extends Institution {

    @OneToMany(mappedBy = "healthInsurance", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Insured> insureds = new ArrayList<>();

    public HealthInsurance() {

    }

    public HealthInsurance(String name, String phone, String fax, String email, Address address, List<InstitutionRole> institutionRoles) {
        super(name, phone, fax, email, address, institutionRoles);
    }

    public List<Insured> getInsureds() {
        return insureds;
    }

    public void setInsureds(List<Insured> insureds) {
        this.insureds = insureds;
    }
}
