package de.phoenix.wgtest.services;

import de.phoenix.wgtest.Utils.ReferenceObjectMap;
import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.*;
import de.phoenix.wgtest.payload.response.ChildResponse;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.services.FileStorageService;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentChildParticipantRepository appointmentChildParticipantRepository;

    @Autowired
    AppointmentPersonParticipantRepository appointmentPersonParticipantRepository;

    public List<Child> getChildrenByLivingGroup(String livingGroup) {
        LivingGroup lg = livingGroupRepository.findByName(livingGroup).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return childRepository.findByLivingGroup(lg);
    }

    @Transactional
    public ResponseEntity<?> insertChild(CreateChildRequest request) {
        String fullName = request.getFirstName() + " " + request.getLastName();
        Child child = new Child(
                EGender.findByName(request.getGender()), request.getFirstName(), request.getLastName(),
                fullName, request.getBirthday(), request.getEntranceDate(), request.getReleaseDate(),
                request.getReason(), request.getCare(), request.getVisit(), request.getDiseases(),
                request.getLivingGroup()
        );
        childRepository.save(child);

        child.setPersonRoles(getPersonRoles(child, request));
        child.setInstitutionRoles(getInstitutionRoles(child, request));

        childRepository.save(child);
        return ResponseEntity.ok(new ChildResponse("Kind erfolgreich angelegt!", child.getId()));
    }

    @Transactional
    public ResponseEntity<?> updateChild(CreateChildRequest request) {
        Child child = childRepository.findById(request.getId()).orElse(null);
        if (child == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Kind mit dieser ID existiert nicht!"));
        }

        child.setGender(EGender.findByName(request.getGender()));
        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setFullName(request.getFirstName() + " " + request.getLastName());
        child.setBirthday(request.getBirthday());
        child.setEntranceDate(request.getEntranceDate());
        child.setReleaseDate(request.getReleaseDate());
        child.setReason(request.getReason());
        child.setCare(request.getCare());
        child.setVisit(request.getVisit());
        child.setDiseases(request.getDiseases());
        child.setLivingGroup(request.getLivingGroup());

        childRepository.save(child);

        List<PersonRole> oldPRoles = personRoleRepository.findAllByChild(child);
        List<InstitutionRole> oldIRoles = institutionRoleRepository.findAllByChild(child);

        List<PersonRole> pRoles = getPersonRoles(child, request);
        List<InstitutionRole> iRoles = getInstitutionRoles(child, request);

        child.setPersonRoles(pRoles);
        child.setInstitutionRoles(iRoles);

        childRepository.save(child);

        removeOldPersonEntries(oldPRoles, pRoles);
        removeOldInstitutionEntries(oldIRoles, iRoles);

        return ResponseEntity.ok(new ChildResponse("Kind erfolgreich geändert!", child.getId()));
    }

    @Transactional
    public ResponseEntity<?> deleteChild(Long id) {
        if (childRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Ein Kind mit dieser ID existiert nicht!"));
        }

        fileStorageService.deleteFile(id);
        personRoleRepository.deleteByChildId(id);
        institutionRoleRepository.deleteByChildId(id);
        removeChildFromAppointments(id);
        childRepository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("Kind erfolgreich gelöscht!"));
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
        map.putIfNamePresent(request.getDayCare(), new RoleObject(ERole.DAYCARE));
        map.putIfNamePresent(request.getHealthInsurance(), new RoleObject(ERole.HEALTHINSURANCE));
        map.putIfNamePresent(request.getFoodSupplier(), new RoleObject(ERole.FOODSUPPLIER));
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
            DayCare d = createOrLoadDayCare(request);
            Teach t = createOrLoadTeach(d, request.getGroup(), request.getTeacher());
            if (t != null) {
                child.setTeach(t);
            }
            return persistInstitutionRole(child, d, role.getERole(), role.getSpecification());
        }
        if (obj instanceof HealthInsuranceRequest) {
            HealthInsuranceRequest request = (HealthInsuranceRequest) obj;
            HealthInsurance h = createOrLoadHealthInsurance(request);
            Insured i = createOrLoadInsured(h, request.getcNumber(), request.getHolder());
            if (i != null) {
                child.setInsured(i);
            }
            return persistInstitutionRole(child, h, role.getERole(), role.getSpecification());
        }
        if (obj instanceof FoodSupplierRequest) {
            FoodSupplierRequest request = (FoodSupplierRequest) obj;
            FoodSupplier f = createOrLoadFoodSupplier(request);
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
        return createOrLoadPersonRole(pRole);
    }

    private InstitutionRole persistInstitutionRole(Child child, Institution institution, ERole eRole, String specification) {
        Role role = createOrLoadRoleByTypeAndSpecification(eRole, specification);
        InstitutionRole iRole = new InstitutionRole(child, institution, role);
        return createOrLoadInstitutionRole(iRole);
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

    private PersonRole createOrLoadPersonRole(PersonRole pRole) {
        Optional<PersonRole> opt = personRoleRepository.findByChildAndPersonAndRole(pRole.getChild(), pRole.getPerson(), pRole.getRole());
        if (opt.isPresent()) {
            return opt.get();
        }
        personRoleRepository.save(pRole);
        return pRole;
    }

    private InstitutionRole createOrLoadInstitutionRole(InstitutionRole iRole) {
        Optional<InstitutionRole> opt = institutionRoleRepository.findByChildAndInstitutionAndRole(iRole.getChild(), iRole.getInstitution(), iRole.getRole());
        if (opt.isPresent()) {
            return opt.get();
        }
        institutionRoleRepository.save(iRole);
        return iRole;
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

    private DayCare createOrLoadDayCare(DayCareRequest request) {
        DayCare dc = dayCareRepository.findByName(request.getName()).orElse(null);

        if (dc == null) {
            dc = new DayCare();
            dc.setName(request.getName());
        }

        Address address = getAddressOrNull(request.getDayCare().getAddress());
        if (address != null) {
            dc.setAddress(address);
        }

        dc.setPhone(request.getDayCare().getPhone());
        dc.setFax(request.getDayCare().getFax());
        dc.setEmail(request.getDayCare().getEmail());

        dayCareRepository.save(dc);
        return dc;
    }

    private HealthInsurance createOrLoadHealthInsurance(HealthInsuranceRequest request) {
        HealthInsurance hi = healthInsuranceRepository.findByName(request.getName()).orElse(null);

        if (hi == null) {
            hi = new HealthInsurance();
            hi.setName(request.getName());
        }

        Address address = getAddressOrNull(request.getHealthInsurance().getAddress());
        if (address != null) {
            hi.setAddress(address);
        }

        hi.setPhone(request.getHealthInsurance().getPhone());
        hi.setFax(request.getHealthInsurance().getFax());
        hi.setEmail(request.getHealthInsurance().getEmail());

        healthInsuranceRepository.save(hi);
        return hi;
    }

    private FoodSupplier createOrLoadFoodSupplier(FoodSupplierRequest request) {
        FoodSupplier fs = foodSupplierRepository.findByName(request.getName()).orElse(null);

        if (fs == null) {
            fs = new FoodSupplier();
            fs.setName(request.getName());
        }

        Address address = getAddressOrNull(request.getFoodSupplier().getAddress());
        if (address != null) {
            fs.setAddress(address);
        }

        fs.setPhone(request.getFoodSupplier().getPhone());
        fs.setFax(request.getFoodSupplier().getFax());
        fs.setEmail(request.getFoodSupplier().getEmail());

        foodSupplierRepository.save(fs);
        return fs;
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

    private void removeOldPersonEntries(List<PersonRole> oldRoles, List<PersonRole> newRoles) {
        oldRoles.removeIf(newRoles::contains);
        for (PersonRole pRole : oldRoles) {
            personRoleRepository.deleteByPersonRole(pRole.getChild().getId(), pRole.getPerson().getId(), pRole.getRole().getId());
        }
    }

    private void removeOldInstitutionEntries(List<InstitutionRole> oldRoles, List<InstitutionRole> newRoles) {
        oldRoles.removeIf(newRoles::contains);
        for (InstitutionRole iRole : oldRoles) {
            institutionRoleRepository.deleteByInstitutionRole(iRole.getChild().getId(), iRole.getInstitution().getId(), iRole.getRole().getId());
        }
    }

    private void removeChildFromAppointments(Long childId) {
        Child child = childRepository.getById(childId);
        List<AppointmentChildParticipant> all = appointmentChildParticipantRepository.findAllByChild(child);
        for (AppointmentChildParticipant acp : all) {
            Appointment a = acp.getAppointment();
            appointmentChildParticipantRepository.deleteByAppointmentChildParticipant(acp.getChild().getId(), acp.getAppointment().getId());
            a.removeChildParticipant(acp);
            if (a.getAppointmentChildParticipants().isEmpty()) {
                for (AppointmentPersonParticipant app : a.getAppointmentPersonParticipants()) {
                    app.getPerson().removePersonParticipant(app);
                    appointmentPersonParticipantRepository.deleteByAppointmentPersonParticipant(app.getPerson().getId(), app.getAppointment().getId());
                }
                a.setAppointmentPersonParticipants(new ArrayList<>());
                appointmentRepository.deleteById(a.getId());
            } else {
                appointmentRepository.save(a);
            }
        }
    }
}
