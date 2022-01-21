package de.phoenix.wgtest;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class WgTestApplication implements CommandLineRunner {

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	InstitutionRepository institutionRepository;

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

	public static void main(String[] args) {
		SpringApplication.run(WgTestApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//create user roles
		for (EUserRole role : EUserRole.values()) {
			if (!userRoleRepository.findByName(role).isPresent()) {
				userRoleRepository.save(new UserRole(role));
			}
		}

		//Run 1
		Address address = new Address("Atriumstraße","4","04315","Leipzig");
		if (!addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).isPresent()) {
			addressRepository.save(address);
		} else {
			address = addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).get();
		}

		DayCare dayCare = new DayCare("Kita Leipzig","123456789","kita@mail.de", address, Set.of());
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

		DayCare dayCare2 = new DayCare("Kita 2 Leipzig","123456789","kita@mail.de", address1, Set.of());
		if (dayCareRepository.findByName(dayCare2.getName()).isPresent()) {
			dayCare2 = dayCareRepository.findByName(dayCare2.getName()).get();
		} else {
			dayCareRepository.save(dayCare2);
		}

		if(childRepository.findByFirstName("Rico").isPresent()) {
			Child child = childRepository.findByFirstName("Rico").get();

			Teach teach = new Teach();
			teach.setDayCare(dayCare2);
			teach.setDayCareGroup("Sonne");
			teach.setDayCareTeacher("Fr. Meier");
			if (teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(dayCare2, "Sonne", "Fr. Meier").isPresent()) {
				teach = teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(dayCare2, "Sonne", "Fr. Meier").get();
			} else {
				teachRepository.save(teach);
			}

			child.setTeach(teach);

			childRepository.save(child);
		}

	}
}
