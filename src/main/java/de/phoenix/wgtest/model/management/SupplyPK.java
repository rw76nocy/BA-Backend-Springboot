package de.phoenix.wgtest.model.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SupplyPK implements Serializable {

    @Column(name = "child_id")
    private Long childId;

    @Column(name = "food_supplier_id")
    private Long foodSupplierId;

    public SupplyPK() {
    }

    public SupplyPK(Long childId, Long foodSupplierId) {
        this.childId = childId;
        this.foodSupplierId = foodSupplierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SupplyPK))
            return false;

        SupplyPK other = (SupplyPK) o;

        return childId != null &&
                childId.equals(other.getChildId()) &&
                foodSupplierId != null &&
                foodSupplierId.equals(other.getFoodSupplierId());
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

    public Long getFoodSupplierId() {
        return foodSupplierId;
    }

    public void setFoodSupplierId(Long foodSupplierId) {
        this.foodSupplierId = foodSupplierId;
    }
}
