package de.phoenix.wgtest;

import de.phoenix.wgtest.controller.security.AuthController;
import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.payload.request.SignupRequest;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.repository.security.UserRepository;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@SpringBootApplication
public class WgTestApplication implements CommandLineRunner {

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthController authController;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	InstitutionRepository institutionRepository;

	@Autowired
	InstitutionRoleRepository institutionRoleRepository;

	@Autowired
	DayCareRepository dayCareRepository;

	@Autowired
	ChildRepository childRepository;

	@Autowired
	LivingGroupRepository livingGroupRepository;

	@Autowired
	TeachRepository teachRepository;

	@Autowired
	FoodSupplierRepository foodSupplierRepository;

	@Autowired
	SupplyRepository supplyRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	PersonRoleRepository personRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(WgTestApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//create user roles
		for (EUserRole role : EUserRole.values()) {
			if (userRoleRepository.findByName(role).isEmpty()) {
				userRoleRepository.save(new UserRole(role));
			}
		}

		for (ERole role : ERole.values()) {
			if (roleRepository.findByType(role).isEmpty()) {
				Role r = new Role();
				r.setType(role);
				roleRepository.save(r);
			}
		}

		//create user admin
		if (!userRepository.existsByUsername("admin")) {
			User user = new User("admin", "admin@mail.de", encoder.encode("12345678"));

			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(userRoleRepository.findByName(EUserRole.ROLE_ADMIN).get());
			user.setRoles(userRoles);
			userRepository.save(user);
		}

		//Run 1
		Address address = new Address("Atriumstraße","4","04315","Leipzig");
		if (!addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).isPresent()) {
			addressRepository.save(address);
		} else {
			address = addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).get();
		}

		DayCare dayCare = new DayCare("Kita Leipzig","123456789","kita@mail.de", address, List.of());
		if (!dayCareRepository.existsByName(dayCare.getName())) {
			dayCareRepository.save(dayCare);
		} else {
			dayCare = dayCareRepository.findByName(dayCare.getName()).get();
		}

		if (livingGroupRepository.findByName("Phoenix").isEmpty()) {
			LivingGroup lg = new LivingGroup();
			lg.setName("Phoenix");
			livingGroupRepository.save(lg);
		}
		LivingGroup lg1 = livingGroupRepository.findByName("Phoenix").get();

		if(!childRepository.findByFirstName("Rico").isPresent()) {
			Child child = new Child();
			child.setGender(EGender.MALE);
			child.setFirstName("Rico");
			child.setLastName("Warnke");
			child.setBirthday(Date.from(LocalDate.of(1990, 4, 24).atStartOfDay().toInstant(ZoneOffset.UTC)));
			child.setEntranceDate(Date.from(LocalDate.of(1990, 4, 25).atStartOfDay().toInstant(ZoneOffset.UTC)));
			child.setReason("Nur mal so zum testen");
			child.setLivingGroup(lg1);

			DayCare dayCare1 = dayCareRepository.findByName("Kita Leipzig").get();
			Teach teach = new Teach();
			teach.setDayCare(dayCare1);
			teach.setDayCareGroup("Regenbogen");
			teach.setDayCareTeacher("Hr. Mueller");
			if (!teachRepository.findAll().contains(teach)) {
				teachRepository.save(teach);
			} else {
				teach = teachRepository.findOne(Example.of(teach)).get();
			}

			child.setTeach(teach);

			childRepository.save(child);
		}

		if (childRepository.findByFirstName("Rico").isPresent()) {
			Child child1 = childRepository.findByFirstName("Rico").get();

			if (child1.getInstitutionRoles().isEmpty()) {
				DayCare dayCare1 = dayCareRepository.findByName("Kita Leipzig").get();
				InstitutionRole iRole = new InstitutionRole(child1, dayCare1, roleRepository.findByType(ERole.DAYCARE).get());
				institutionRoleRepository.save(iRole);

				List<InstitutionRole> roles = child1.getInstitutionRoles();
				roles.add(iRole);
				child1.setInstitutionRoles(roles);

				childRepository.save(child1);
			}

			if (child1.getPersonRoles().isEmpty()) {
				Person person = new Person();
				person.setName("Hans Harald");
				person.setPhone("0123/456789");
				personRepository.save(person);

				PersonRole pRole = new PersonRole(child1, person, roleRepository.findByType(ERole.GUARDIAN).get());
				personRoleRepository.save(pRole);

				List<PersonRole> roles = child1.getPersonRoles();
				roles.add(pRole);
				child1.setPersonRoles(roles);

				childRepository.save(child1);
			}
		}


		if(!childRepository.findByFirstName("Josefine").isPresent()) {
			Child child = new Child();
			child.setGender(EGender.FEMALE);
			child.setFirstName("Josefine");
			child.setLastName("Dreifke");
			child.setBirthday(Date.from(LocalDate.of(1990, 4, 24).atStartOfDay().toInstant(ZoneOffset.UTC)));
			child.setEntranceDate(Date.from(LocalDate.of(1990, 4, 25).atStartOfDay().toInstant(ZoneOffset.UTC)));
			child.setReason("Nur mal so zum testen");
			child.setLivingGroup(lg1);

			DayCare dayCare1 = dayCareRepository.findByName("Kita Leipzig").get();
			Teach teach = new Teach();
			teach.setDayCare(dayCare1);
			teach.setDayCareGroup("Regenbogen");
			teach.setDayCareTeacher("Hr. Mueller");
			if (!teachRepository.findAll().contains(teach)) {
				teachRepository.save(teach);
			} else {
				teach = teachRepository.findOne(Example.of(teach)).get();
			}
			child.setTeach(teach);

			childRepository.save(child);
		}

		//Run 2 Änderung
		Address address1;
		if (addressRepository.findById(1L).isPresent()) {
			address1 = addressRepository.findById(1L).get();
		} else {
			address1 = new Address("Atriumstraße","4","04315","Leipzig");
			addressRepository.save(address1);
		}

		DayCare dayCare2 = new DayCare("Kita 2 Leipzig","123456789","kita@mail.de", address1, List.of());
		if (dayCareRepository.findByName(dayCare2.getName()).isPresent()) {
			dayCare2 = dayCareRepository.findByName(dayCare2.getName()).get();
		} else {
			dayCareRepository.save(dayCare2);
		}

		if(childRepository.findByFirstName("Rico").isPresent()) {
			Child child = childRepository.findByFirstName("Rico").get();

			Teach teach = child.getTeach();
			teach.setDayCare(dayCare2);
			teach.setDayCareGroup("Sonne");
			teach.setDayCareTeacher("Fr. Meier");
			if (teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(
					dayCare2, teach.getDayCareGroup(), teach.getDayCareTeacher()).isPresent()) {
				teach = teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(
						dayCare2, teach.getDayCareGroup(), teach.getDayCareTeacher()).get();
			} else {
				teachRepository.save(teach);
			}

			child.setTeach(teach);

			childRepository.save(child);
		}

		Person person = new Person();
		person.setGender(EGender.FEMALE.getShortName());
		person.setName("Susanne Dreifke");
		person.setBirthday(Date.from(LocalDate.of(1967, 7, 18).atStartOfDay().toInstant(ZoneOffset.UTC)));

		Address address2 = addressRepository.findById(1L).get();
		LivingGroup lg2 = livingGroupRepository.findByName("Phoenix").get();

		person.setPhone("123456789");
		person.setFax("");
		person.setEmail("");

		person.setAddress(address2);
		person.setLivingGroup(lg2);

		if (personRepository.findByName("Susanne Dreifke").isEmpty()) {
			personRepository.save(person);
		}
	}
}
