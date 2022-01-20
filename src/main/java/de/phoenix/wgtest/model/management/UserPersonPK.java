package de.phoenix.wgtest.model.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserPersonPK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "person_id")
    private Long personId;

    public UserPersonPK() {
    }

    public UserPersonPK(Long userId, Long personId) {
        this.userId = userId;
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserPersonPK))
            return false;

        UserPersonPK other = (UserPersonPK) o;

        return userId != null &&
                userId.equals(other.getUserId()) &&
                personId != null &&
                personId.equals(other.getPersonId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
