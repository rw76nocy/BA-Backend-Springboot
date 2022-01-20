package de.phoenix.wgtest.model.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TeachPK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "day_care_id")
    private Long dayCareId;

    public TeachPK() {
    }

    public TeachPK(Long childId, Long dayCareId) {
        this.childId = childId;
        this.dayCareId = dayCareId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TeachPK))
            return false;

        TeachPK other = (TeachPK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
                dayCareId != null &&
                dayCareId.equals(other.getDayCareId());
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

    public Long getDayCareId() {
        return dayCareId;
    }

    public void setDayCareId(Long dayCareId) {
        this.dayCareId = dayCareId;
    }
}
