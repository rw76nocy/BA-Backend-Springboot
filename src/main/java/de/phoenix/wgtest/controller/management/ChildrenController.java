package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.Utils.ChildrenService;
import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.*;
import de.phoenix.wgtest.payload.response.ChildResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/children")
public class ChildrenController {

    @Autowired
    ChildrenService childrenService;

    @GetMapping( value = "/get/all/{livingGroup}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Child> getChildrenByLivingGroup(@PathVariable String livingGroup) {
        return childrenService.getChildrenByLivingGroup(livingGroup);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addChild(@RequestBody CreateChildRequest request) {
        Child child = childrenService.insertChild(request);
        return ResponseEntity.ok(new ChildResponse("Kind erfolgreich angelegt!", child.getId()));
    }
}
