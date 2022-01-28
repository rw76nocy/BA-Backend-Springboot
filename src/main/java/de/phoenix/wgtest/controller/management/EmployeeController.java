package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.LivingGroupRepository;
import de.phoenix.wgtest.repository.management.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/employees")
public class EmployeeController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllEmployees() {
        List<Person> all = new ArrayList<>();
        if (!personRepository.findAll().isEmpty()) {
            all = personRepository.findAll();
            all.removeIf(p -> p.getLivingGroup() == null);
        }
        return all;
    }

    //TODO noch unfertig!!!!
    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Person person) {
        if (person.getId() != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Dieser Mitarbeiter existiert bereits!"));
        }
        personRepository.save(person);
        return ResponseEntity.ok(new MessageResponse("Mitarbeiter erfolgreich angelegt!"));
    }

    /*@DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (livingGroupRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit dieser ID existiert nicht!"));
        }

        livingGroupRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Wohngruppe erfolgreich gelöscht!"));
    }*/
}
