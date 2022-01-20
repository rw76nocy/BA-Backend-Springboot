package de.phoenix.wgtest.model.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InsuredPK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "health_insurance_id")
    private Long healthInsuranceId;

    public InsuredPK() {
    }

    public InsuredPK(Long childId, Long healthInsuranceId) {
        this.childId = childId;
        this.healthInsuranceId = healthInsuranceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof InsuredPK))
            return false;

        InsuredPK other = (InsuredPK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
                healthInsuranceId != null &&
                healthInsuranceId.equals(other.getHealthInsuranceId());
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

    public Long getHealthInsuranceId() {
        return healthInsuranceId;
    }

    public void setHealthInsuranceId(Long healthInsuranceId) {
        this.healthInsuranceId = healthInsuranceId;
    }
}
