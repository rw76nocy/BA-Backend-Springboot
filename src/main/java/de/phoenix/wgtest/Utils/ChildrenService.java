package de.phoenix.wgtest.Utils;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.*;
import de.phoenix.wgtest.repository.management.*;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Service
public class ChildrenService {

    @Autowired
    LivingGroupRepository livingGroupRepository;

    @Autowired
    ChildRepository childRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PersonRoleRepository personRoleRepository;

    @Autowired
    InstitutionRoleRepository institutionRoleRepository;

    @Autowired
    AsdRepository asdRepository;

    @Autowired
    DayCareRepository dayCareRepository;

    @Autowired
    HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    FoodSupplierRepository foodSupplierRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    TeachRepository teachRepository;

    @Autowired
    InsuredRepository insuredRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    Validator validator;

    @Autowired
    AddressRepository addressRepository;

    public List<Child> getChildrenByLivingGroup(String livingGroup) {
        List<Child> all = new ArrayList<>();
        Optional<LivingGroup> optLg = livingGroupRepository.findByName(livingGroup);
        if (optLg.isPresent()) {
            all = childRepository.findByLivingGroup(optLg.get());
        }
        return all;
    }

    @Transactional
    public Child insertChild(CreateChildRequest request) {
        Child child = new Child(
                EGender.findByName(request.getGender()), request.getFirstName(), request.getLastName(),
                request.getBirthday(), request.getEntranceDate(), request.getReleaseDate(),
                request.getReason(), request.getCare(), request.getVisit(), request.getDiseases(),
                request.getLivingGroup()
        );
        childRepository.save(child);

        child.setPersonRoles(getPersonRoles(child, request));
        child.setInstitutionRoles(getInstitutionRoles(child, request));
        childRepository.save(child);
        return child;
    }

    private List<PersonRole> getPersonRoles(Child child, CreateChildRequest request) {
        return EntryStream.of(getPersonReferenceObjects(request))
                .mapKeyValue((k, v) -> persistPersonReferenceObject(child, k, v))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<InstitutionRole> getInstitutionRoles(Child child, CreateChildRequest request) {
        return EntryStream.of(getInstitutionReferenceObject(request))
                .mapKeyValue((k, v) -> persistInstitutionReferenceObject(child, k, v))
                .filter(Objects::nonNull)
                .toList();
    }

    private ReferenceObjectMap getPersonReferenceObjects(CreateChildRequest request) {
        ReferenceObjectMap map = new ReferenceObjectMap();
        map.putIfNamePresent(personRepository.getByName(request.getSuperVisor1()), new RoleObject(ERole.SUPERVISOR1));
        map.putIfNamePresent(personRepository.getByName(request.getSuperVisor2()), new RoleObject(ERole.SUPERVISOR2));
        map.putIfNamePresent(request.getGuardian(), new RoleObject(ERole.GUARDIAN));
        map.putIfNamePresent(request.getAsd(), new RoleObject(ERole.ASD));
        map.putIfNamePresent(request.getMother(), new RoleObject(ERole.MOTHER));
        map.putIfNamePresent(request.getFather(), new RoleObject(ERole.FATHER));
        map.putIfNamePresent(request.getChilddoctor(), new RoleObject(ERole.CHILDDOCTOR));
        map.putReferenceObjectList(request.getReferencePersons(), ERole.REFERENCE_PERSON);
        map.putReferenceObjectList(request.getDoctors(), ERole.DOCTOR);
        map.putReferenceObjectList(request.getTherapists(), ERole.THERAPIST);
        map.putReferenceObjectList(request.getPartners(), ERole.PARTNER);
        return map;
    }

    private ReferenceObjectMap getInstitutionReferenceObject(CreateChildRequest request) {
        ReferenceObjectMap map = new ReferenceObjectMap();
        map.putIfNamePresent(request.getDayCareRequest(), new RoleObject(ERole.DAYCARE));
        map.putIfNamePresent(request.getHealthInsuranceRequest(), new RoleObject(ERole.HEALTHINSURANCE));
        map.putIfNamePresent(request.getFoodSupplierRequest(), new RoleObject(ERole.FOODSUPPLIER));
        map.putIfNamePresent(request.getDriver(), new RoleObject(ERole.DRIVER));
        return map;
    }

    private PersonRole persistPersonReferenceObject(Child child, ReferenceObject obj, RoleObject role) {
        if (obj instanceof Person) {
            Person p = (Person) obj;
            if (p instanceof Asd) {
                Asd a = (Asd) obj;
                return persistPersonRole(child, createOrLoadAsd(a), role.getERole(), role.getSpecification());
            }
            return persistPersonRole(child, createOrLoadPerson(p), role.getERole(), role.getSpecification());
        }
        return null;
    }

    private InstitutionRole persistInstitutionReferenceObject(Child child, ReferenceObject obj, RoleObject role) {
        if (obj instanceof Institution) {
            Institution i = (Institution) obj;
            return persistInstitutionRole(child, createOrLoadInstitution(i), role.getERole(), role.getSpecification());
        }
        if (obj instanceof DayCareRequest) {
            DayCareRequest request = (DayCareRequest) obj;
            DayCare d = (DayCare) createOrLoadInstitution(request);
            Teach t = createOrLoadTeach(d, request.getGroup(), request.getTeacher());
            if (t != null) {
                child.setTeach(t);
            }
            return persistInstitutionRole(child, d, role.getERole(), role.getSpecification());
        }
        if (obj instanceof HealthInsuranceRequest) {
            HealthInsuranceRequest request = (HealthInsuranceRequest) obj;
            HealthInsurance h = (HealthInsurance) createOrLoadInstitution(request);
            Insured i = createOrLoadInsured(h, request.getcNumber(), request.getHolder());
            if (i != null) {
                child.setInsured(i);
            }
            return persistInstitutionRole(child, h, role.getERole(), role.getSpecification());
        }
        if (obj instanceof FoodSupplierRequest) {
            FoodSupplierRequest request = (FoodSupplierRequest) obj;
            FoodSupplier f = (FoodSupplier) createOrLoadInstitution(request);
            Supply s = createOrLoadSupply(f, request.getcNumber(), request.getPin());
            if (s != null) {
                child.setSupply(s);
            }
            return persistInstitutionRole(child, f, role.getERole(), role.getSpecification());
        }
        return null;
    }

    private PersonRole persistPersonRole(Child child, Person person, ERole eRole, String specification) {
        Role role = createOrLoadRoleByTypeAndSpecification(eRole, specification);
        PersonRole pRole = new PersonRole(child, person, role);
        personRoleRepository.save(pRole);
        return pRole;
    }

    private InstitutionRole persistInstitutionRole(Child child, Institution institution, ERole eRole, String specification) {
        Role role = createOrLoadRoleByTypeAndSpecification(eRole, specification);
        InstitutionRole iRole = new InstitutionRole(child, institution, role);
        institutionRoleRepository.save(iRole);
        return iRole;
    }

    private Role createOrLoadRoleByTypeAndSpecification(ERole eRole, String specification) {
        Optional<Role> optRole = roleRepository.findByTypeAndSpecification(eRole, specification);
        if (optRole.isPresent()) {
            return optRole.get();
        }
        Role role = new Role(eRole, specification);
        roleRepository.save(role);
        return role;
    }

    private Person createOrLoadPerson(Person p) {
        Person person = null;
        Optional<Person> optPerson = personRepository.findByName(p.getName());
        if (optPerson.isPresent()) {
            person = optPerson.get();
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

        personRepository.save(person);
        return person;
    }

    private Asd createOrLoadAsd(Asd a) {
        Asd asd = null;
        Optional<Asd> optAsd = asdRepository.findByName(a.getName());
        if (optAsd.isPresent()) {
            asd = optAsd.get();
        }

        if (asd == null) {
            asd = new Asd();
            asd.setName(a.getName());
        }

        Address address = getAddressOrNull(a.getAddress());
        if (address != null) {
            asd.setAddress(address);
        }

        asd.setBirthday(a.getBirthday());
        asd.setPhone(a.getPhone());
        asd.setFax(a.getFax());
        asd.setEmail(a.getEmail());
        asd.setYouthoffice(a.getYouthoffice());
        asdRepository.save(asd);
        return asd;
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

    private Teach createOrLoadTeach(DayCare dayCare, String group, String teacher) {
        if (group == null && teacher == null) {
            return null;
        }
        Teach teach = new Teach(dayCare, group, teacher);
        if (teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(teach.getDayCare(), teach.getDayCareGroup(), teach.getDayCareTeacher()).isEmpty()) {
            teachRepository.save(teach);
        } else {
            teach = teachRepository.findByDayCareAndDayCareGroupAndDayCareTeacher(teach.getDayCare(), teach.getDayCareGroup(), teach.getDayCareTeacher()).get();
        }
        return teach;
    }

    private Insured createOrLoadInsured(HealthInsurance healthInsurance, String customerNumber, String holder) {
        if (customerNumber == null && holder == null) {
            return null;
        }
        Insured insured = new Insured(healthInsurance, customerNumber, holder);
        if (insuredRepository.findByHealthInsuranceAndHolderAndCustomerNumber(insured.getHealthInsurance(), insured.getHolder(), insured.getCustomerNumber()).isEmpty()) {
            insuredRepository.save(insured);
        } else {
            insured = insuredRepository.findByHealthInsuranceAndHolderAndCustomerNumber(insured.getHealthInsurance(), insured.getHolder(), insured.getCustomerNumber()).get();
        }
        return insured;
    }

    private Supply createOrLoadSupply(FoodSupplier foodSupplier, String customerNumber, String pin) {
        if (customerNumber == null && pin == null) {
            return null;
        }
        Supply supply = new Supply(foodSupplier, customerNumber, pin);
        if (supplyRepository.findByFoodSupplierAndCustomerNumberAndPin(supply.getFoodSupplier(), supply.getCustomerNumber(), supply.getPin()).isEmpty()) {
            supplyRepository.save(supply);
        } else {
            supply = supplyRepository.findByFoodSupplierAndCustomerNumberAndPin(supply.getFoodSupplier(), supply.getCustomerNumber(), supply.getPin()).get();
        }
        return supply;
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
}
