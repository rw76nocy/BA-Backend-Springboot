package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity( name = "daycare")
@Table( name = "day_care")
public class DayCare extends Institution {

    @OneToMany(mappedBy = "dayCare")
    private Set<Teach> teach = new HashSet<>();

    public DayCare() {
    }

    public DayCare(String name, String phone, String email, Address address, Set<InstitutionRole> institutionRoles) {
        super(name, phone, email, address, institutionRoles);
    }

    public Set<Teach> getTeach() {
        return teach;
    }

    public void setTeach(Set<Teach> teach) {
        this.teach = teach;
    }
}
