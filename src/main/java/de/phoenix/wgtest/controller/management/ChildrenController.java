package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.*;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/children")
public class ChildrenController {

    @Autowired
    LivingGroupRepository livingGroupRepository;

    @Autowired
    ChildRepository childRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AsdRepository asdRepository;

    @Autowired
    DayCareRepository dayCareRepository;

    @Autowired
    TeachRepository teachRepository;

    @Autowired
    HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    InsuredRepository insuredRepository;

    @Autowired
    FoodSupplierRepository foodSupplierRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    PersonRoleRepository personRoleRepository;

    @Autowired
    InstitutionRoleRepository institutionRoleRepository;

    @Autowired
    Validator validator;

    @GetMapping( value = "/get/all/{livingGroup}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Child> getChildrenByLivingGroup(@PathVariable String livingGroup) {
        //TODO noch hübscher machen!!!
        Optional<LivingGroup> optLg = livingGroupRepository.findByName(livingGroup);
        LivingGroup lg = optLg.get();
        return childRepository.findByLivingGroup(lg);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addChild(@RequestBody CreateChildRequest request) {
        Child child = new Child();

        child.setLivingGroup(request.getLivingGroup());
        child.setGender(EGender.findByName(request.getGender()));

        //TODO nachforschen wie man nun mit der URL an das Bild kommt
        String image = request.getImage();
        /*child.setImage(request.getImage());*/

        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setBirthday(request.getBirthday());
        child.setEntranceDate(request.getEntranceDate());
        child.setReleaseDate(request.getReleaseDate());
        child.setReason(request.getReason());
        child.setCare(request.getCare());
        child.setVisit(request.getVisit());
        child.setDiseases(request.getDiseases());

        childRepository.save(child);

        List<PersonRole> pRoles = new ArrayList<>();
        List<InstitutionRole> iRoles = new ArrayList<>();

        if (request.getSuperVisor1().getName() != null) {
            Optional<Person> opt1 = personRepository.findByName(request.getSuperVisor1().getName());
            if (opt1.isPresent()) {
                PersonRole pRole = new PersonRole(child, opt1.get(), roleRepository.findByTypeAndSpecification(ERole.SUPERVISOR1, null).get());
                personRoleRepository.save(pRole);
                pRoles.add(pRole);
            }
        }

        if (request.getSuperVisor2().getName() != null) {
            Optional<Person> opt2 = personRepository.findByName(request.getSuperVisor2().getName());
            if (opt2.isPresent()) {
                PersonRole pRole = new PersonRole(child, opt2.get(), roleRepository.findByTypeAndSpecification(ERole.SUPERVISOR2, null).get());
                personRoleRepository.save(pRole);
                pRoles.add(pRole);
            }
        }

        if (request.getGuardian().getName() != null) {
            Person guard = createOrLoadPerson(request.getGuardian());

            PersonRole pRole = new PersonRole(child, guard, roleRepository.findByTypeAndSpecification(ERole.GUARDIAN, null).get());
            personRoleRepository.save(pRole);
            pRoles.add(pRole);
        }

        if (request.getAsd().getName() != null) {
            Person asd = createOrLoadPerson(request.getAsd());

            PersonRole pRole = new PersonRole(child, asd, roleRepository.findByTypeAndSpecification(ERole.ASD, null).get());
            personRoleRepository.save(pRole);
            pRoles.add(pRole);
        }

        if (request.getMother().getName() != null) {
            Person mother = createOrLoadPerson(request.getMother());

            PersonRole pRole = new PersonRole(child, mother, roleRepository.findByTypeAndSpecification(ERole.MOTHER, null).get());
            personRoleRepository.save(pRole);
            pRoles.add(pRole);
        }

        if (request.getFather().getName() != null) {
            Person father = createOrLoadPerson(request.getFather());

            PersonRole pRole = new PersonRole(child, father, roleRepository.findByTypeAndSpecification(ERole.FATHER, null).get());
            personRoleRepository.save(pRole);
            pRoles.add(pRole);
        }

        if (request.getChilddoctor().getName() != null) {
            Person childdoctor = createOrLoadPerson(request.getChilddoctor());

            PersonRole pRole = new PersonRole(child, childdoctor, roleRepository.findByTypeAndSpecification(ERole.CHILDDOCTOR, null).get());
            personRoleRepository.save(pRole);
            pRoles.add(pRole);
        }

        if (request.getDayCare().getName() != null) {
            Institution dayCare = createOrLoadInstitution(request.getDayCare());
            Teach teach = createOrLoadTeach((DayCare) dayCare, request.getDayCare());
            child.setTeach(teach);

            InstitutionRole iRole = new InstitutionRole(child, dayCare, roleRepository.findByTypeAndSpecification(ERole.DAYCARE, null).get());
            institutionRoleRepository.save(iRole);
            iRoles.add(iRole);
        }

        if (request.getHealthInsurance().getName() != null) {
            Institution healthInsurance = createOrLoadInstitution(request.getHealthInsurance());
            Insured insured = createOrLoadInsured((HealthInsurance) healthInsurance, request.getHealthInsurance());
            child.setInsured(insured);

            InstitutionRole iRole = new InstitutionRole(child, healthInsurance, roleRepository.findByTypeAndSpecification(ERole.HEALTHINSURANCE, null).get());
            institutionRoleRepository.save(iRole);
            iRoles.add(iRole);
        }

        if (request.getFoodSupplier().getName() != null) {
            Institution foodSupplier = createOrLoadInstitution(request.getFoodSupplier());
            Supply supply = createOrLoadSupply((FoodSupplier) foodSupplier, request.getFoodSupplier());
            child.setSupply(supply);

            InstitutionRole iRole = new InstitutionRole(child, foodSupplier, roleRepository.findByTypeAndSpecification(ERole.FOODSUPPLIER, null).get());
            institutionRoleRepository.save(iRole);
            iRoles.add(iRole);
        }

        if (request.getDriver().getName() != null) {
            Institution driver = createOrLoadInstitution(request.getDriver());

            InstitutionRole iRole = new InstitutionRole(child, driver, roleRepository.findByTypeAndSpecification(ERole.DRIVER, null).get());
            institutionRoleRepository.save(iRole);
            iRoles.add(iRole);
        }

        //persons
        createSpecifiedPersonsAsRole(request.getReferencePersons(), ERole.REFERENCE_PERSON, child, pRoles);

        //doctors
        createSpecifiedPersonsAsRole(request.getDoctors(), ERole.DOCTOR, child, pRoles);

        //therapists
        createSpecifiedPersonsAsRole(request.getTherapists(), ERole.THERAPIST, child, pRoles);

        //partners
        createSpecifiedPersonsAsRole(request.getPartners(), ERole.PARTNER, child, pRoles);

        child.setPersonRoles(pRoles);
        child.setInstitutionRoles(iRoles);

        childRepository.save(child);
        return ResponseEntity.ok(new MessageResponse("Kind erfolgreich angelegt!"));
    }

    private void createSpecifiedPersonsAsRole(List<SpecifiedPersonRequest> persons, ERole eRole, Child child, List<PersonRole> pRoles) {
        for (SpecifiedPersonRequest spr : persons) {
            Person person = createOrLoadPerson(spr);
            Role role = createOrLoadRoleByTypeAndSpecification(eRole, spr.getType());

            PersonRole pRole = new PersonRole(child, person, role);
            personRoleRepository.save(pRole);
            pRoles.add(pRole);
        }
    }

    private Person createOrLoadPerson(SpecifiedPersonRequest request) {
        Person person = null;
        Optional<Person> optPerson = personRepository.findByName(request.getName());
        if (optPerson.isPresent()) {
            person = optPerson.get();
        }

        if (person == null) {
            person = new Person();
            person.setName(request.getName());
        }

        Address address = getAddressOrNull(request.getAddress());
        if (address != null) {
            person.setAddress(address);
        }

        person.setBirthday(request.getBirthday());
        person.setPhone(request.getPhone());
        person.setFax(request.getFax());
        person.setEmail(request.getEmail());
        personRepository.save(person);

        return person;
    }

    private Person createOrLoadPerson(Person p) {
        Person person = null;
        if (p instanceof Asd) {
            Optional<Asd> optAsd = asdRepository.findByName(p.getName());
            if (optAsd.isPresent()) {
                person = optAsd.get();
            }
        } else {
            Optional<Person> optPerson = personRepository.findByName(p.getName());
            if (optPerson.isPresent()) {
                person = optPerson.get();
            }
        }

        if (person == null) {
            person = new Person();
            person.setName(p.getName());
        }

        Address address = getAddressOrNull(p.getAddress());
        if (address != null) {
            person.setAddress(address);
        }

        person.setBirthday(p.getBirthday());
        person.setPhone(p.getPhone());
        person.setFax(p.getFax());
        person.setEmail(p.getEmail());

        if (p instanceof Asd) {
            Asd asd = (Asd) person;
            asd.setYouthoffice(((Asd) p).getYouthoffice());
            asdRepository.save(asd);
            return asd;
        } else {
            personRepository.save(person);
            return person;
        }
    }

    private Institution createOrLoadInstitution(SpecifiedInstitutionRequest sir) {
        Institution i = null;

        if (sir instanceof DayCareRequest) {
            Optional<DayCare> optDayCare = dayCareRepository.findByName(sir.getName());
            if (optDayCare.isPresent()) {
                i = optDayCare.get();
            }
        } else if (sir instanceof HealthInsuranceRequest) {
            Optional<HealthInsurance> optHealthInsurance = healthInsuranceRepository.findByName(sir.getName());
            if (optHealthInsurance.isPresent()) {
                i = optHealthInsurance.get();
            }
        } else if (sir instanceof FoodSupplierRequest) {
            Optional<FoodSupplier> optFoodSupplier = foodSupplierRepository.findByName(sir.getName());
            if (optFoodSupplier.isPresent()) {
                i = optFoodSupplier.get();
            }
        }

        if (i == null) {
            i = new Institution();
            i.setName(sir.getName());
        }

        Address address = getAddressOrNull(sir.getAddress());
        if (address != null) {
            i.setAddress(address);
        }

        i.setPhone(sir.getPhone());
        i.setFax(sir.getFax());
        i.setEmail(sir.getEmail());

        if (sir instanceof DayCareRequest) {
            dayCareRepository.save((DayCare) i);
        }

        if (sir instanceof HealthInsuranceRequest) {
            healthInsuranceRepository.save((HealthInsurance) i);
        }

        if (sir instanceof FoodSupplierRequest) {
            foodSupplierRepository.save((FoodSupplier) i);
        }

        return i;
    }

    private Institution createOrLoadInstitution(Institution institution) {
        Institution i;

        Optional<Institution> optInstitution = institutionRepository.findByName(institution.getName());
        if (optInstitution.isPresent()) {
            i = optInstitution.get();
        } else {
            i = new Institution();
            i.setName(institution.getName());
        }

        Address address = getAddressOrNull(institution.getAddress());
        if (address != null) {
            i.setAddress(address);
        }

        i.setPhone(institution.getPhone());
        i.setFax(institution.getFax());
        i.setEmail(institution.getEmail());

        institutionRepository.save(i);
        return i;
    }

    private Teach createOrLoadTeach(DayCare dayCare, DayCareRequest request) {
        Teach teach = new Teach();
        teach.setDayCare(dayCare);
        teach.setDayCareGroup(request.getGroup());
        teach.setDayCareTeacher(request.getTeacher());
        if (teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(teach.getDayCare(), teach.getDayCareGroup(), teach.getDayCareTeacher()).isEmpty()) {
            teachRepository.save(teach);
        } else {
            teach = teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(teach.getDayCare(), teach.getDayCareGroup(), teach.getDayCareTeacher()).get();
        }
        return teach;
    }

    private Insured createOrLoadInsured(HealthInsurance healthInsurance, HealthInsuranceRequest request) {
        Insured insured = new Insured();
        insured.setHealthInsurance(healthInsurance);
        insured.setHolder(request.getHolder());
        insured.setCustomerNumber(request.getcNumber());
        if (insuredRepository.findByHealthInsuranceAndHolderAndCustomerNumber(insured.getHealthInsurance(), insured.getHolder(), insured.getCustomerNumber()).isEmpty()) {
            insuredRepository.save(insured);
        } else {
            insured = insuredRepository.findByHealthInsuranceAndHolderAndCustomerNumber(insured.getHealthInsurance(), insured.getHolder(), insured.getCustomerNumber()).get();
        }
        return insured;
    }

    private Supply createOrLoadSupply(FoodSupplier foodSupplier, FoodSupplierRequest request) {
        Supply supply = new Supply();
        supply.setFoodSupplier(foodSupplier);
        supply.setCustomerNumber(request.getcNumber());
        supply.setPin(request.getPin());
        if (supplyRepository.findByFoodSupplierAndCustomerNumberAndPin(supply.getFoodSupplier(), supply.getCustomerNumber(), supply.getPin()).isEmpty()) {
            supplyRepository.save(supply);
        } else {
            supply = supplyRepository.findByFoodSupplierAndCustomerNumberAndPin(supply.getFoodSupplier(), supply.getCustomerNumber(), supply.getPin()).get();
        }
        return supply;
    }

    private Role createOrLoadRoleByTypeAndSpecification(ERole eRole, String specification) {
        Optional<Role> optRole = roleRepository.findByTypeAndSpecification(eRole, specification);
        if (optRole.isPresent()) {
            return optRole.get();
        }
        Role role = new Role();
        role.setType(eRole);
        role.setSpecification(specification);
        roleRepository.save(role);

        return role;
    }

    //TODO: Übergangslösung, wenn Adressen-Instanz nicht gültig, dann Adresse nicht speichern
    private Address getAddressOrNull(Address address) {
        if (address == null) {
            return null;
        }
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        if (violations.isEmpty()) {
            if (!addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).isPresent()) {
                addressRepository.save(address);
            } else {
                address = addressRepository.findByStreetAndNumberAndZipCodeAndCity(address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity()).get();
            }
            return address;
        }
        return null;
    }

    /*
    @GetMapping("/get/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<LivingGroup> getLivingGroupByName(@PathVariable String name) {
        List<LivingGroup> all = new ArrayList<>();
        if (!livingGroupRepository.findAll().isEmpty()) {
            if (livingGroupRepository.findByName(name).isPresent()) {
                all.add(livingGroupRepository.findByName(name).get());
            }
        }
        return all;
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (livingGroupRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Wohngruppe mit dieser ID existiert nicht!"));
        }

        livingGroupRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Wohngruppe erfolgreich gelöscht!"));
    }
    */
}
