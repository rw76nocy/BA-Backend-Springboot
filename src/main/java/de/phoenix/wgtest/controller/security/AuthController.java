package de.phoenix.wgtest.controller.security;

import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.payload.request.LoginRequest;
import de.phoenix.wgtest.payload.request.SignupRequest;
import de.phoenix.wgtest.payload.response.JwtResponse;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import de.phoenix.wgtest.repository.security.UserRepository;
import de.phoenix.wgtest.security.jwt.JwtUtils;
import de.phoenix.wgtest.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Benutzername ist bereits vergeben!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: E-Mail-Adresse wird bereits verwendet!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<UserRole> userRoles = new HashSet<>();

        if (strRoles == null) {
            UserRole userRole = userRoleRepository.findByName(EUserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fehler: Rolle nicht gefunden!"));
            userRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        UserRole adminUserRole = userRoleRepository.findByName(EUserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Fehler: Rolle nicht gefunden!"));
                        userRoles.add(adminUserRole);

                        break;
                    case "mod":
                        UserRole modUserRole = userRoleRepository.findByName(EUserRole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Fehler: Rolle nicht gefunden!"));
                        userRoles.add(modUserRole);

                        break;
                    default:
                        UserRole userRole = userRoleRepository.findByName(EUserRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Fehler: Rolle nicht gefunden!"));
                        userRoles.add(userRole);
                }
            });
        }

        user.setRoles(userRoles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Benutzer erfolgreich registriert!"));
    }
}
