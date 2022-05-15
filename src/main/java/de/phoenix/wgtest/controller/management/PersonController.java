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
                if (!all.contains(p)) {
                    all.add(p);
                }
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
                if (!all.contains(p)) {
                    all.add(p);
                }
            }
        }

        return all;
    }
}
