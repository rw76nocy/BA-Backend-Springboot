package de.phoenix.wgtest.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Institution")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Institution implements ReferenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 100)
    private String phone;

    @Size(max = 100)
    private String fax;

    @Size(max = 100)
    private String email;

    @ManyToOne( fetch = FetchType.EAGER, optional = true)
    @JoinColumn( name = "address_id", nullable = true)
    private Address address;

    //FETCHTYPE.LAZY hier ganz wichtig, sonst wird zuviel unnötiges Zeug geladen!!!!!!!!!
    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<InstitutionRole> institutionRoles = new ArrayList<>();

    public Institution() {

    }

    public Institution(String name, String phone, String fax, String email, Address address) {
        this.name = name;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.address = address;
    }

    public Institution(String name, String phone, String fax, String email, Address address,
                       List<InstitutionRole> institutionRoles) {
        this(name, phone, fax, email, address);
        this.institutionRoles = institutionRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<InstitutionRole> getInstitutionRoles() {
        return institutionRoles;
    }

    public void setInstitutionRoles(List<InstitutionRole> institutionRoles) {
        this.institutionRoles = institutionRoles;
    }
}
