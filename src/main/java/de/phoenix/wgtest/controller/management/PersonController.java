package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/persons")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AsdRepository asdRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping( value = "/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Person getPersonById(@PathVariable Long id) {
        Person p = null;
        if (personRepository.findById(id).isPresent()) {
            p = personRepository.findById(id).get();
        }
        return p;
    }

    @GetMapping( value = "/get/asd/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Asd getAsdById(@PathVariable Long id) {
        Asd a = null;
        if (asdRepository.findById(id).isPresent()) {
            a = asdRepository.findById(id).get();
        }
        return a;
    }

    @GetMapping("/get/guardian/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllGuardians() {
        List<Person> all = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByTypeAndSpecification(ERole.GUARDIAN, null);

        if (optionalRole.isPresent()) {
            List<PersonRole> personRoles = optionalRole.get().getPersonRoles();
            for (PersonRole pr : personRoles) {
                Person p = pr.getPerson();
                all.add(p);
            }
        }

        return all;
    }

    @GetMapping("/get/asd/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Asd> getAllAsds() {
        return asdRepository.findAll();
    }

    @GetMapping("/get/childdoctor/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllChilddoctors() {
        List<Person> all = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByTypeAndSpecification(ERole.CHILDDOCTOR, null);

        if (optionalRole.isPresent()) {
            List<PersonRole> personRoles = optionalRole.get().getPersonRoles();
            for (PersonRole pr : personRoles) {
                Person p = pr.getPerson();
                all.add(p);
            }
        }

        return all;
    }

    /*@GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllEmployees() {
        List<Person> all = new ArrayList<>();
        if (!personRepository.findAll().isEmpty()) {
            all = personRepository.findAll();
            all.removeIf(p -> p.getLivingGroup() == null);
        }
        return all;
    }

    @GetMapping( value = "/get/employee/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Person getEmployeeById(@PathVariable Long id) {
        Person p = null;
        if (personRepository.findById(id).isPresent()) {
            p = personRepository.findById(id).get();
        }
        return p;
    }

    @GetMapping( value = "/get/supervisor/all/{livingGroup}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllEmployeesByLivingGroup(@PathVariable String livingGroup) {
        List<Person> all = new ArrayList<>();
        if (!personRepository.findAll().isEmpty()) {
            all = personRepository.findAll();
            all.removeIf(p -> p.getLivingGroup() == null);
            all.removeIf(p -> !p.getLivingGroup().getName().equals(livingGroup));
        }
        return all;
    }

    @GetMapping( value = "/get/all/{livingGroup}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getEmployeesByLivingGroup(@PathVariable String livingGroup) {
        List<Person> all = new ArrayList<>();
        if (!personRepository.findAll().isEmpty()) {
            all = personRepository.findAll();
            all.removeIf(p -> !p.getLivingGroup().getName().equals(livingGroup));
            UserRole role = userRoleRepository.findByName(EUserRole.ROLE_USER).get();
            all.removeIf(p -> p.getUser() != null && !p.getUser().getRoles().contains(role));
        }
        return all;
    }

    @GetMapping( value = "/get/{livingGroup}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getEmployeesByLivingGroupWithoutAccount(@PathVariable String livingGroup) {
        List<Person> all = new ArrayList<>();
        if (!personRepository.findAll().isEmpty()) {
            all = personRepository.findAll();
            all.removeIf(p -> p.getLivingGroup() == null);
            all.removeIf(p -> !p.getLivingGroup().getName().equals(livingGroup));
            all.removeIf(p -> p.getUser() != null);
        }
        return all;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Person person) {
        if (person.getId() != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Dieser Mitarbeiter existiert bereits!"));
        }

        Address address = person.getAddress();
        if (addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).isEmpty()) {
            addressRepository.save(address);
        } else {
            address = addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).get();
            person.setAddress(address);
        }

        LivingGroup lg = person.getLivingGroup();
        if (livingGroupRepository.findByName(lg.getName()).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Die angegebene Wohngruppe existiert nicht!"));
        } else {
            lg = livingGroupRepository.findByName(lg.getName()).get();
            person.setLivingGroup(lg);
        }

        personRepository.save(person);
        return ResponseEntity.ok(new MessageResponse("Mitarbeiter erfolgreich angelegt!"));
    }

    @PutMapping(value = "/put/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Person person) {
        if (personRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Der angegebene Mitarbeiter existiert nicht!"));
        }

        Person updated = personRepository.findById(id).get();
        updated.setGender(person.getGender());
        updated.setName(person.getName());
        updated.setPhone(person.getPhone());
        updated.setFax(person.getFax());
        updated.setEmail(person.getEmail());
        updated.setBirthday(person.getBirthday());

        Address address = person.getAddress();
        if (addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).isEmpty()) {
            addressRepository.save(address);
        } else {
            address = addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).get();
        }
        updated.setAddress(address);

        LivingGroup livingGroup = person.getLivingGroup();
        if (livingGroupRepository.findByName(livingGroup.getName()).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit diesen Namen existiert nicht!"));
        } else {
            livingGroup = livingGroupRepository.findByName(livingGroup.getName()).get();
        }
        updated.setLivingGroup(livingGroup);
        personRepository.save(updated);

        return ResponseEntity.ok(new MessageResponse("Mitarbeiter mit ID: "+id+" erfolgreich bearbeitet!"));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (personRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Mitarbeiter mit dieser ID existiert nicht!"));
        }

        personRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Mitarbeiter erfolgreich gelöscht!"));
    }*/
}
