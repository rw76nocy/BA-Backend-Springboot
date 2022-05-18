package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping( value = "get/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User getUserAccountById(@PathVariable Long id) {
        return accountService.getUserAccountById(id);
    }

    @GetMapping( value = "/get/{livingGroup}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getUserAccountByLivingGroup(@PathVariable String livingGroup) {
        return accountService.getUserAccountByLivingGroup(livingGroup);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id);
    }
}
