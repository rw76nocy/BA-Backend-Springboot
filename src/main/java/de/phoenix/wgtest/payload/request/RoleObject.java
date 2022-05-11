package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.ERole;

public class RoleObject {

    private ERole eRole;
    private String specification;

    public RoleObject(ERole eRole) {
        this.eRole = eRole;
    }

    public RoleObject(ERole eRole, String specification) {
        this.eRole = eRole;
        this.specification = specification;
    }

    public ERole getERole() {
        return eRole;
    }

    public void setERole(ERole eRole) {
        this.eRole = eRole;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
