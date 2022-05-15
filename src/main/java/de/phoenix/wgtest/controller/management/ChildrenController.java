package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.Utils.ChildrenService;
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

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateChild(@RequestBody CreateChildRequest request) {
        Child child = childrenService.updateChild(request);
        if (child == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Kind mit dieser ID existiert nicht!"));
        }
        return ResponseEntity.ok(new ChildResponse("Kind erfolgreich geändert!", child.getId()));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteChild(@PathVariable Long id) {
        return childrenService.deleteChild(id);
    }
}
