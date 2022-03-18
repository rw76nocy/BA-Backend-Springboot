package de.phoenix.wgtest.model.management;

import de.phoenix.wgtest.model.embeddable.InstitutionRolePK;

import javax.persistence.*;

@Entity
public class InstitutionRole {

    @EmbeddedId
    InstitutionRolePK id = new InstitutionRolePK();

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    Child child;

    @ManyToOne
    @MapsId("institutionId")
    @JoinColumn(name = "institution_id")
    Institution institution;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    Role role;

    public InstitutionRole() {

    }

    public InstitutionRole(Child child, Institution institution, Role role) {
        this.child = child;
        this.institution = institution;
        this.role = role;
    }

    public InstitutionRolePK getId() {
        return id;
    }

    public void setId(InstitutionRolePK id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
