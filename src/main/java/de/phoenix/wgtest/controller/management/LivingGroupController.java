package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.services.LivingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/livinggroup")
public class LivingGroupController {

    @Autowired
    LivingGroupService livingGroupService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LivingGroup> getAllLivingGroups() {
        return livingGroupService.getAllLivingGroups();
    }

    @GetMapping("/get/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LivingGroup> getLivingGroupByName(@PathVariable String name) {
        return livingGroupService.getLivingGroupByName(name);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addLivingGroup(@Valid @RequestBody LivingGroup livingGroup) {
        return livingGroupService.addLivingGroup(livingGroup);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteLivingGroup(@PathVariable Long id) {
        return livingGroupService.deleteLivingGroup(id);
    }
}
