package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.AddressRepository;
import de.phoenix.wgtest.repository.management.LivingGroupRepository;
import de.phoenix.wgtest.repository.management.PersonRepository;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
public class EmployeeService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    LivingGroupRepository livingGroupRepository;

    public List<Person> getAllEmployees() {
        return StreamEx.of(personRepository.findAll()).filter(p -> p.getLivingGroup() != null).toList();
    }

    public Person getEmployeeById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> getAllEmployeesByLivingGroup(String livingGroup) {
        LivingGroup lg = livingGroupRepository.findByName(livingGroup).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return personRepository.findAllByLivingGroup(lg);
    }

    public List<Person> getEmployeesByLivingGroup(String livingGroup) {
        LivingGroup lg = livingGroupRepository.findByName(livingGroup).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return StreamEx.of(personRepository.findAllByLivingGroup(lg)).filter(isNotModerator()).toList();
    }

    public List<Person> getEmployeesByLivingGroupWithoutAccount(String livingGroup) {
        LivingGroup lg = livingGroupRepository.findByName(livingGroup).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return StreamEx.of(personRepository.findAllByLivingGroup(lg)).filter(hasNotUser()).toList();
    }

    @Transactional
    public ResponseEntity<?> addEmployee(Person person) {
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
        }
        person.setAddress(address);

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

    @Transactional
    public ResponseEntity<?> updateEmployee(Long id, Person person) {
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

    @Transactional
    public ResponseEntity<?> deleteEmployee(Long id) {
        if (personRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Mitarbeiter mit dieser ID existiert nicht!"));
        }

        personRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Mitarbeiter erfolgreich gelöscht!"));
    }

    private UserRole createOrLoadUserRole(EUserRole eUserRole) {
        UserRole userRole = userRoleRepository.findByName(eUserRole).orElse(null);
        if (userRole != null) {
            return userRole;
        }

        UserRole uRole = new UserRole(eUserRole);
        userRoleRepository.save(uRole);
        return uRole;
    }

    private Predicate<Person> isNotModerator() {
        UserRole role = createOrLoadUserRole(EUserRole.ROLE_USER);
        return p-> p.getUser() == null || p.getUser().getRoles().contains(role);
    }

    private Predicate<Person> hasNotUser() {
        return p-> p.getUser() == null;
    }
}
