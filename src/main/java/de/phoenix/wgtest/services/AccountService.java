package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.security.UserRepository;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    public List<User> getAllAccounts() {
        return StreamEx.of(userRepository.findAll()).filter(u -> !u.getUsername().equals("admin")).toList();
    }

    public User getUserAccountById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getUserAccountByLivingGroup(String livingGroup) {
        UserRole role = createOrLoadUserRole(EUserRole.ROLE_USER);
        return StreamEx.of(userRepository.findAll())
                .filter(u -> u.getRoles().contains(role))
                .filter(u -> u.getPerson().getLivingGroup().getName().equals(livingGroup))
                .toList();
    }

    @Transactional
    public ResponseEntity<?> deleteAccount(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Konto mit dieser ID existiert nicht!"));
        }

        user.setPerson(null);
        for (UserRole role : user.getRoles()) {
            user.removeRole(role);
            role.removeUser(user);
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Konto erfolgreich gelöscht!"));
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
}
