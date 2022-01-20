package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "role")
public class Role {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size( max = 100)
    private String type;

    @NotBlank
    @Size( max = 100)
    private String specification;

    @OneToMany(mappedBy = "role")
    private Set<PersonRole> personRoles = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<InstitutionRole> institutionRoles = new HashSet<>();

    public Role() {

    }

    public Role(String type, String specification, Set<PersonRole> personRoles, Set<InstitutionRole> institutionRoles) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Set<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(Set<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }

    public Set<InstitutionRole> getInstitutionRoles() {
        return institutionRoles;
    }

    public void setInstitutionRoles(Set<InstitutionRole> institutionRoles) {
        this.institutionRoles = institutionRoles;
    }
}
