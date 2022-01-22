package de.phoenix.wgtest.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InstitutionRolePK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "institution_id")
    private Long institutionId;

    @Column(name = "role_id")
    private Long roleId;

    public InstitutionRolePK() {
    }

    public InstitutionRolePK(Long childId, Long institutionId, Long roleId) {
        this.childId = childId;
        this.institutionId = institutionId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof InstitutionRolePK))
            return false;

        InstitutionRolePK other = (InstitutionRolePK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
                institutionId != null &&
                institutionId.equals(other.getInstitutionId())&&
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

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
