package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import de.phoenix.wgtest.services.PersonService;
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
    PersonService personService;

    @GetMapping( value = "/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping( value = "/get/asd/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Asd getAsdById(@PathVariable Long id) {
        return personService.getAsdById(id);
    }

    @GetMapping("/get/guardian/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllGuardians() {
        return personService.getAllGuardians();
    }

    @GetMapping("/get/asd/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Asd> getAllAsds() {
        return personService.getAllAsds();
    }

    @GetMapping("/get/childdoctor/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Person> getAllChilddoctors() {
        return personService.getAllChilddoctors();
    }
}
