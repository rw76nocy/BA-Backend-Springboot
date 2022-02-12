package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.LivingGroupRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/livinggroup")
public class LivingGroupController {

    @Autowired
    LivingGroupRepository livingGroupRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LivingGroup> getAllLivingGroups() {
        List<LivingGroup> all = new ArrayList<>();
        if (!livingGroupRepository.findAll().isEmpty()) {
            all = livingGroupRepository.findAll();
        }
        return all;
    }

    @GetMapping("/get/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LivingGroup> getLivingGroupByName(@PathVariable String name) {
        List<LivingGroup> all = new ArrayList<>();
        if (!livingGroupRepository.findAll().isEmpty()) {
            if (livingGroupRepository.findByName(name).isPresent()) {
                all.add(livingGroupRepository.findByName(name).get());
            }
        }
        return all;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addLivingGroup(@Valid @RequestBody LivingGroup livingGroup) {
        if (livingGroupRepository.findByName(livingGroup.getName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit diesem Namen existiert bereits!"));
        }

        livingGroupRepository.save(livingGroup);
        return ResponseEntity.ok(new MessageResponse("Wohngruppe erfolgreich angelegt!"));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (livingGroupRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit dieser ID existiert nicht!"));
        }

        livingGroupRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Wohngruppe erfolgreich gelöscht!"));
    }
}
