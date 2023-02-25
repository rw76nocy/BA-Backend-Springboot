package de.phoenix.wgtest.Utils;

import de.phoenix.wgtest.model.management.ERole;
import de.phoenix.wgtest.model.management.ReferenceObject;
import de.phoenix.wgtest.payload.request.RoleObject;
import de.phoenix.wgtest.payload.request.SpecifiedPersonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReferenceObjectMap extends HashMap<RoleObject, ReferenceObject> {

    public ReferenceObjectMap() {
        super();
    }

    public void putIfNamePresent(RoleObject roleObject, ReferenceObject ref) {
        if (Objects.nonNull(ref) && Objects.nonNull(ref.getName()) && !ref.getName().equals("")) {
            put(roleObject, ref);
        }
    }

    public void putReferenceObjectList(ERole eRole, List<SpecifiedPersonRequest> list) {
        for (SpecifiedPersonRequest spr : list) {
            RoleObject roleObject = new RoleObject(eRole, spr.getType());
            putIfNamePresent(roleObject, spr.getPerson());
        }
    }
}
