package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.services.ChildrenService;
import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.*;
import de.phoenix.wgtest.payload.response.ChildResponse;
import de.phoenix.wgtest.payload.response.MessageResponse;
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public List<Child> getChildrenByLivingGroup(@PathVariable String livingGroup) {
        return childrenService.getChildrenByLivingGroup(livingGroup);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public ResponseEntity<?> addChild(@RequestBody CreateChildRequest request) {
        return childrenService.insertChild(request);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public ResponseEntity<?> updateChild(@RequestBody CreateChildRequest request) {
        return childrenService.updateChild(request);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteChild(@PathVariable Long id) {
        return childrenService.deleteChild(id);
    }
}
