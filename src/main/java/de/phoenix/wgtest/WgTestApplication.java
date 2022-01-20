package de.phoenix.wgtest;

import de.phoenix.wgtest.model.management.Address;
import de.phoenix.wgtest.model.management.DayCare;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.repository.management.AddressRepository;
import de.phoenix.wgtest.repository.management.DayCareRepository;
import de.phoenix.wgtest.repository.management.InstitutionRepository;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		/*Address address = new Address("Atriumstraße","4","04315","Leipzig");
		if (!addressRepository.findOne(Example.of(address)).isPresent()) {
			addressRepository.save(address);
		}
		//List<Address> addressList = addressRepository.findAll();

		DayCare dayCare = new DayCare("Kita Leipzig","123456789","kita@mail.de", address);
		if (!dayCareRepository.existsByName(dayCare.getName())) {
			dayCareRepository.save(dayCare);
		}

		if (dayCareRepository.existsByName("Kita Leipzig")) {
			Optional<DayCare> test = dayCareRepository.findByName("Kita Leipzig");
			if (test.isPresent()) {
				DayCare dc = test.get();
				System.out.println("ID: "+test.get().getId());
				System.out.println("NAME: "+test.get().getName());
			}
		}*/
	}
}
