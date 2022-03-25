package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity( name = "daycare")
@Table( name = "day_care")
public class DayCare extends Institution {

    @OneToMany(mappedBy = "dayCare", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Teach> teaches = new ArrayList<>();

    public DayCare() {
    }

    public DayCare(String name, String phone, String email, Address address, List<InstitutionRole> institutionRoles) {
        super(name, phone, email, address, institutionRoles);
    }

    public List<Teach> getTeach() {
        return teaches;
    }

    public void setTeach(List<Teach> teaches) {
        this.teaches = teaches;
    }
}
