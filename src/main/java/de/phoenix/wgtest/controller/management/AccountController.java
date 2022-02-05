package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.LivingGroup;
import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.AddressRepository;
import de.phoenix.wgtest.repository.management.LivingGroupRepository;
import de.phoenix.wgtest.repository.management.PersonRepository;
import de.phoenix.wgtest.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/accounts")
public class AccountController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getAllAccounts() {
        List<User> all = new ArrayList<>();
        if (!userRepository.findAll().isEmpty()) {
            all = userRepository.findAll();
            all.removeIf(u -> u.getUsername().equals("admin"));
        }
        return all;
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Konto mit dieser ID existiert nicht!"));
        }

        User user = userRepository.getById(id);
        user.setPerson(null);

        for (UserRole role : user.getRoles()) {
            user.removeRole(role);
            role.removeUser(user);
        }

        userRepository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("Konto erfolgreich gelöscht!"));
    }
}
