package de.phoenix.wgtest;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.repository.security.UserRepository;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class WgTestApplication implements CommandLineRunner {

	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_RAW_PASSWORD = "12345678";
	private static final String ADMIN_EMAIL = "admin@mail.de";

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(WgTestApplication.class, args);
	}


	@Override
	public void run(String... args) {
		initialize();
	}

	public void initialize() {
		initializeUserRoles();
		initializeRoles();
		initializeAdminAccount();
	}

	public void initializeUserRoles() {
		//create user roles
		for (EUserRole role : EUserRole.values()) {
			if (userRoleRepository.findByName(role).isEmpty()) {
				userRoleRepository.save(new UserRole(role));
			}
		}
	}

	public void initializeRoles() {
		//create roles for person/institution
		for (ERole role : ERole.values()) {
			if (roleRepository.findByTypeAndSpecification(role, null).isEmpty()) {
				Role r = new Role();
				r.setType(role);
				roleRepository.save(r);
			}
		}
	}

	public void initializeAdminAccount() {
		//create user admin
		if (!userRepository.existsByUsername(ADMIN_USERNAME)) {
			User user = new User(ADMIN_USERNAME, ADMIN_EMAIL, encoder.encode(ADMIN_RAW_PASSWORD));
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(userRoleRepository.findByName(EUserRole.ROLE_ADMIN).orElse(null));
			user.setRoles(userRoles);
			userRepository.save(user);
		}
	}
}
