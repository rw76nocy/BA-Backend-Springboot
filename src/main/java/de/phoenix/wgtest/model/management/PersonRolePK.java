package de.phoenix.wgtest.model.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PersonRolePK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "role_id")
    private Long roleId;

    public PersonRolePK() {
    }

    public PersonRolePK(Long childId, Long personId, Long roleId) {
        this.childId = childId;
        this.personId = personId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonRolePK))
            return false;

        PersonRolePK other = (PersonRolePK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
                personId != null &&
                personId.equals(other.getPersonId())&&
                roleId != null &&
                roleId.equals(other.getRoleId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
