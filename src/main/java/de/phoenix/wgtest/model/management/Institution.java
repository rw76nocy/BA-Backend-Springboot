package de.phoenix.wgtest.model.management;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Institution")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String phone;

    @NotBlank
    @Size(max = 100)
    private String email;

    @OneToOne( fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JoinColumn( name = "address_id", nullable = true)
    private Address address;

    @OneToMany(mappedBy = "institution")
    private List<InstitutionRole> institutionRoles = new ArrayList<>();

    public Institution() {

    }

    public Institution(String name, String phone, String email, Address address,
                       List<InstitutionRole> institutionRoles) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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
