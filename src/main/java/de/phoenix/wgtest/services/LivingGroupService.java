package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.LivingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivingGroupService {

    @Autowired
    LivingGroupRepository livingGroupRepository;

    public List<LivingGroup> getAllLivingGroups() {
        return livingGroupRepository.findAll();
    }

    public List<LivingGroup> getLivingGroupByName(String name) {
        LivingGroup lg = livingGroupRepository.findByName(name).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return List.of(lg);
    }

    @Transactional
    public ResponseEntity<?> addLivingGroup(LivingGroup livingGroup) {
        if (livingGroupRepository.findByName(livingGroup.getName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit diesem Namen existiert bereits!"));
        }

        livingGroupRepository.save(livingGroup);
        return ResponseEntity.ok(new MessageResponse("Wohngruppe erfolgreich angelegt!"));
    }

    @Transactional
    public ResponseEntity<?> deleteLivingGroup(Long id) {
        if (livingGroupRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit dieser ID existiert nicht!"));
        }

        livingGroupRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Wohngruppe erfolgreich gelöscht!"));
    }
}
