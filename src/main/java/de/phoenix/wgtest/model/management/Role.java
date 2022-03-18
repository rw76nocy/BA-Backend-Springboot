package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "role")
public class Role {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column( length = 100)
    private ERole type;

    @Size( max = 100)
    private String specification;

    @OneToMany(mappedBy = "role")
    private List<PersonRole> personRoles = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    private List<InstitutionRole> institutionRoles = new ArrayList<>();

    public Role() {

    }

    public Role(ERole type, String specification, List<PersonRole> personRoles, List<InstitutionRole> institutionRoles) {
        this.type = type;
        this.specification = specification;
        this.personRoles = personRoles;
        this.institutionRoles = institutionRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getType() {
        return type;
    }

    public void setType(ERole type) {
        this.type = type;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public List<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(List<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }

    public List<InstitutionRole> getInstitutionRoles() {
        return institutionRoles;
    }

    public void setInstitutionRoles(List<InstitutionRole> institutionRoles) {
        this.institutionRoles = institutionRoles;
    }
}
