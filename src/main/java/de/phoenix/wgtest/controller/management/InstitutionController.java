package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.repository.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/institutions")
public class InstitutionController {

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    DayCareRepository dayCareRepository;

    @Autowired
    HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    FoodSupplierRepository foodSupplierRepository;

    @Autowired
    RoleRepository roleRepository;

    /*@GetMapping( value = "/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Institution getInstitutionById(@PathVariable Long id) {
        Institution i = null;
        if (institutionRepository.findById(id).isPresent()) {
            i = institutionRepository.findById(id).get();
        }
        return i;
    }*/

    @GetMapping( value = "/get/daycare/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public DayCare getDaycareById(@PathVariable Long id) {
        DayCare d = null;
        if (dayCareRepository.findById(id).isPresent()) {
            d = dayCareRepository.findById(id).get();
        }
        return d;
    }

    /*@GetMapping( value = "/get/healthinsurance/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public HealthInsurance getHealthInsuranceById(@PathVariable Long id) {
        HealthInsurance h = null;
        if (healthInsuranceRepository.findById(id).isPresent()) {
            h = healthInsuranceRepository.findById(id).get();
        }
        return h;
    }

    @GetMapping( value = "/get/foodsupplier/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public FoodSupplier getFoodsupplierById(@PathVariable Long id) {
        FoodSupplier f = null;
        if (foodSupplierRepository.findById(id).isPresent()) {
            f = foodSupplierRepository.findById(id).get();
        }
        return f;
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }*/

    @GetMapping("/get/daycare/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<DayCare> getAllDaycares() {
        return dayCareRepository.findAll();
    }

    /*@GetMapping("/get/healthinsurance/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<HealthInsurance> getAllHealthinsurances() {
        return healthInsuranceRepository.findAll();
    }

    @GetMapping("/get/foodsupplier/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<FoodSupplier> getAllFoodsuppliers() {
        return foodSupplierRepository.findAll();
    }

    @GetMapping("/get/driver/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Institution> getAllDrivers() {
        List<Institution> all = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByType(ERole.DRIVER);

        if (optionalRole.isPresent()) {
            List<InstitutionRole> institutionRoles = optionalRole.get().getInstitutionRoles();
            for (InstitutionRole ir : institutionRoles) {
                Institution i = ir.getInstitution();
                all.add(i);
            }
        }

        return all;
    }*/

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
