package de.phoenix.wgtest.Utils;

import de.phoenix.wgtest.model.management.ERole;
import de.phoenix.wgtest.model.management.ReferenceObject;
import de.phoenix.wgtest.payload.request.RoleObject;
import de.phoenix.wgtest.payload.request.SpecifiedPersonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReferenceObjectMap extends HashMap<ReferenceObject, RoleObject> {

    public ReferenceObjectMap() {
        super();
    }

    public void putIfNamePresent(ReferenceObject ref, RoleObject roleObject) {
        if (Objects.nonNull(ref.getName()) && !ref.getName().equals("")) {
            put(ref, roleObject);
        }
    }

    public void putReferenceObjectList(List<SpecifiedPersonRequest> list, ERole eRole) {
        for (SpecifiedPersonRequest spr : list) {
            RoleObject roleObject = new RoleObject(eRole, spr.getType());
            putIfNamePresent(spr.getPerson(), roleObject);
        }
    }
}
