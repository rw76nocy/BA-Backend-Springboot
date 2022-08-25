package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.CreateAppointmentRequest;
import de.phoenix.wgtest.payload.request.CreateAppointmentTypeRequest;
import de.phoenix.wgtest.payload.response.AddAppointmentResponse;
import de.phoenix.wgtest.payload.response.AppointmentOverlapResponse;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    LivingGroupRepository livingGroupRepository;

    @Autowired
    AppointmentPersonParticipantRepository appointmentPersonParticipantRepository;

    @Autowired
    AppointmentChildParticipantRepository appointmentChildParticipantRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ChildRepository childRepository;

    public List<Appointment> getAppointmentsByLivingGroup(String livingGroup) {
        LivingGroup lg = livingGroupRepository.findByName(livingGroup).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return appointmentRepository.findAllByLivingGroup(lg);
    }

    public Set<AppointmentOverlapResponse> checkOverlaps(CreateAppointmentRequest request) {
        LivingGroup lg = livingGroupRepository.findByName(request.getLivingGroup().getName()).orElse(null);
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();

        //all appointments with overlapping times
        HashSet<Appointment> concerned =
                appointmentRepository.findAllByLivingGroupAndStartDateAfterAndStartDateBeforeOrEndDateAfterAndEndDateBeforeOrStartDateLessThanEqualAndEndDateGreaterThanEqual(
                lg, startDate, endDate, startDate, endDate, startDate, endDate
        );

        //for updating, remove the appointment with the same id
        if (request.getId() != null && request.getId() != 0L) {
            concerned.removeIf(a -> a.getId().equals(request.getId()));
        }

        //if there are no time overlaps, return empty set
        if (concerned.isEmpty()) {
            return new HashSet<>();
        }

        //load persons und children, only if the are time overlaps
        HashSet<Person> membersSet = personRepository.findAllByNameIn(request.getMembers());
        HashSet<Child> childSet = childRepository.findAllByFullNameIn(request.getChildren());

        Set<AppointmentOverlapResponse> responses = new HashSet<>();

        for (Appointment a : concerned) {
            Set<String> overlapedPersons = getMemberOverlaps(a, membersSet);
            Set<String> overlapedChildren = getChildrenOverlaps(a, childSet);

            //if there are person or children overlaps, add appointment to response
            if (!overlapedPersons.isEmpty() || !overlapedChildren.isEmpty()) {
                responses.add(new AppointmentOverlapResponse(a.getTitle(), a.getStartDate(), a.getEndDate(),
                        overlapedPersons, overlapedChildren));
            }
        }

        return responses;
    }

    private Set<String> getMemberOverlaps(Appointment a, HashSet<Person> members) {
        Set<Person> persons = StreamEx.of(a.getAppointmentPersonParticipants())
                .map(AppointmentPersonParticipant::getPerson)
                .toSet();

        Set<Person> copy = new HashSet<>(members);
        copy.removeIf(p -> !persons.contains(p));
        return StreamEx.of(copy).map(Person::getName).toSet();
    }

    private Set<String> getChildrenOverlaps(Appointment a, HashSet<Child> children) {
        Set<Child> childs = StreamEx.of(a.getAppointmentChildParticipants())
                .map(AppointmentChildParticipant::getChild)
                .toSet();

        Set<Child> copy = new HashSet<>(children);
        copy.removeIf(c -> !childs.contains(c));
        return StreamEx.of(copy).map(Child::getFullName).toSet();
    }

    public List<CreateAppointmentRequest> getEarlierAlternatives(CreateAppointmentRequest request, long minutes) {
        List<CreateAppointmentRequest> alternatives = new ArrayList<>();

        Instant start = request.getStartDate().toInstant();
        ZonedDateTime lower = ZonedDateTime.ofInstant(start, ZoneId.systemDefault());
        ZonedDateTime lowerLdt = lower.withHour(7).withMinute(0);
        Instant lowerLimit = lowerLdt.toInstant();

        Instant newStart = request.getStartDate().toInstant().minus(minutes, ChronoUnit.MINUTES);
        Instant newEnd = request.getEndDate().toInstant().minus(minutes, ChronoUnit.MINUTES);

        //wenn unteres Limit erreicht ist oder 5 Alternativen gefunden wurden
        while (request.getStartDate().toInstant().isAfter(lowerLimit) && alternatives.size() < 5) {
            request.setStartDate(Date.from(newStart));
            request.setEndDate(Date.from(newEnd));

            CreateAppointmentRequest newReq = new CreateAppointmentRequest();
            newReq.setId(request.getId());
            newReq.setTitle(request.getTitle());
            newReq.setStartDate(request.getStartDate());
            newReq.setEndDate(request.getEndDate());
            newReq.setLocation(request.getLocation());
            newReq.setAppointmentType(request.getAppointmentType());
            newReq.setrRule(request.getrRule());
            newReq.setLivingGroup(request.getLivingGroup());
            newReq.setMembers(request.getMembers());
            newReq.setChildren(request.getChildren());

            Set<AppointmentOverlapResponse> overlaps = checkOverlaps(request);
            if (overlaps.isEmpty()) {
                alternatives.add(newReq);
            }

            newStart = request.getStartDate().toInstant().minus(minutes, ChronoUnit.MINUTES);
            newEnd = request.getEndDate().toInstant().minus(minutes, ChronoUnit.MINUTES);
        }

        return alternatives;
    }

    public List<CreateAppointmentRequest> getLaterAlternatives(CreateAppointmentRequest request, long minutes) {
        List<CreateAppointmentRequest> alternatives = new ArrayList<>();

        Instant end = request.getEndDate().toInstant();
        ZonedDateTime upper = ZonedDateTime.ofInstant(end, ZoneId.systemDefault());
        ZonedDateTime upperLdt = upper.withHour(20).withMinute(0);
        Instant upperLimit = upperLdt.toInstant();

        Instant newStart = request.getStartDate().toInstant().plus(minutes, ChronoUnit.MINUTES);
        Instant newEnd = request.getEndDate().toInstant().plus(minutes, ChronoUnit.MINUTES);

        while ((request.getEndDate().toInstant().isBefore(upperLimit) || request.getEndDate().toInstant().equals(upperLimit)) && alternatives.size() < 5) {
            request.setStartDate(Date.from(newStart));
            request.setEndDate(Date.from(newEnd));

            CreateAppointmentRequest newReq = new CreateAppointmentRequest();
            newReq.setId(request.getId());
            newReq.setTitle(request.getTitle());
            newReq.setStartDate(request.getStartDate());
            newReq.setEndDate(request.getEndDate());
            newReq.setLocation(request.getLocation());
            newReq.setAppointmentType(request.getAppointmentType());
            newReq.setrRule(request.getrRule());
            newReq.setLivingGroup(request.getLivingGroup());
            newReq.setMembers(request.getMembers());
            newReq.setChildren(request.getChildren());

            Set<AppointmentOverlapResponse> overlaps = checkOverlaps(request);
            if (overlaps.isEmpty()) {
                alternatives.add(newReq);
            }

            newStart = request.getStartDate().toInstant().plus(minutes, ChronoUnit.MINUTES);
            newEnd = request.getEndDate().toInstant().plus(minutes, ChronoUnit.MINUTES);
        }

        return alternatives;
    }

    @Transactional
    public ResponseEntity<?> insertAppointment(CreateAppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setTitle(request.getTitle());
        appointment.setStartDate(request.getStartDate());
        appointment.setEndDate(request.getEndDate());
        String location = request.getLocation();
        if (location != null) {
            appointment.setLocation(location);
        }
        appointment.setAppointmentType(request.getAppointmentType());
        String rRule = request.getrRule();
        if (rRule != null) {
            appointment.setrRule(rRule);
        }

        LivingGroup lg = request.getLivingGroup();
        if (livingGroupRepository.findByName(lg.getName()).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Die angegebene Wohngruppe existiert nicht!"));
        } else {
            lg = livingGroupRepository.findByName(lg.getName()).get();
            appointment.setLivingGroup(lg);
        }

        appointmentRepository.save(appointment);


        // employees/members
        for (String name : request.getMembers()) {
            Person person = personRepository.findByName(name).orElse(null);
            if (person != null) {
                AppointmentPersonParticipant app = new AppointmentPersonParticipant(person, appointment);
                appointmentPersonParticipantRepository.save(app);
            }
        }

        // children
        for (String name : request.getChildren()) {
            Child child = childRepository.findByFullName(name).orElse(null);
            if (child != null) {
                AppointmentChildParticipant acp = new AppointmentChildParticipant(child, appointment);
                appointmentChildParticipantRepository.save(acp);
            }
        }

        return ResponseEntity.ok(new AddAppointmentResponse("Termin erfolgreich angelegt!", appointment.getId()));
    }

    @Transactional
    public ResponseEntity<?> updateAppointment(CreateAppointmentRequest request) {
        Appointment a = appointmentRepository.findById(request.getId()).orElse(null);
        if (a == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Termin mit dieser ID existiert nicht!"));
        }

        a.setTitle(request.getTitle());
        a.setStartDate(request.getStartDate());
        a.setEndDate(request.getEndDate());
        a.setLocation(request.getLocation());
        a.setAppointmentType(request.getAppointmentType());
        a.setrRule(request.getrRule());

        handleMemberChange(request, a);
        handleChildrenChange(request, a);

        appointmentRepository.save(a);

        return ResponseEntity.ok(new MessageResponse("Termin erfolgreich geändert!"));
    }

    @Transactional
    public ResponseEntity<?> deleteAppointment(Long id) {
        Appointment a = appointmentRepository.findById(id).orElse(null);
        if (a == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Termin mit dieser ID existiert nicht!"));
        }

        appointmentPersonParticipantRepository.deleteByAppointmentId(a.getId());
        appointmentChildParticipantRepository.deleteByAppointmentId(a.getId());
        appointmentRepository.deleteById(a.getId());

        return ResponseEntity.ok(new MessageResponse("Termin erfolgreich gelöscht!"));
    }

    public List<AppointmentType> getAppointmentTypesByLivingGroup(String livingGroup) {
        LivingGroup lg = livingGroupRepository.findByName(livingGroup).orElse(null);
        if (lg == null) {
            return List.of();
        }
        return appointmentTypeRepository.findAllByLivingGroup(lg);
    }

    @Transactional
    public ResponseEntity<?> insertAppointmentType(CreateAppointmentTypeRequest createAppointmentTypeRequest) {
        LivingGroup lg = livingGroupRepository.findByName(createAppointmentTypeRequest.getLivingGroup()).orElse(null);
        if (lg == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Die angegebene Wohngruppe existiert nicht!"));
        }

        AppointmentType at = appointmentTypeRepository.findByLivingGroupAndName(lg, createAppointmentTypeRequest.getName()).orElse(null);
        if (at != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Der angegebene Name existiert bereits!"));
        }

        at = new AppointmentType();
        at.setName(createAppointmentTypeRequest.getName());
        at.setColor(createAppointmentTypeRequest.getColor());
        at.setLivingGroup(lg);

        appointmentTypeRepository.save(at);

        return ResponseEntity.ok(new MessageResponse("Terminart erfolgreich angelegt!"));
    }

    @Transactional
    public ResponseEntity<?> updateAppointmentType(AppointmentType type) {
        AppointmentType at = appointmentTypeRepository.findById(type.getId()).orElse(null);
        if (at != null) {
            at.setName(type.getName());
            at.setColor(type.getColor());
            appointmentTypeRepository.save(at);
        }
        return ResponseEntity.ok(new MessageResponse("Terminart erfolgreich geändert!"));
    }

    @Transactional
    public ResponseEntity<?> deleteAppointmentType(Long id) {
        AppointmentType at = appointmentTypeRepository.findById(id).orElse(null);
        if (at == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Eine Terminart mit dieser ID existiert nicht!"));
        }

        AppointmentType defaultType = appointmentTypeRepository.findById(at.getLivingGroup().getDefaultType().getId()).orElse(null);
        if (defaultType == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Keine Standart-Terminart Terminart gefunden!"));
        }

        List<Appointment> all = at.getAppointments();
        for (Appointment a : all) {
            a.setAppointmentType(defaultType);
            appointmentRepository.save(a);
        }

        appointmentTypeRepository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("Terminart erfolgreich gelöscht!"));
    }

    private void handleMemberChange(CreateAppointmentRequest request, Appointment appointment) {
        List<String> members = request.getMembers();

        List<String> existingNames = StreamEx.of(appointment.getAppointmentPersonParticipants())
                .map(app -> app.getPerson().getName())
                .toList();

        List<String> newMembers = StreamEx.of(members).filter(m -> !existingNames.contains(m)).toList();

        List<String> removed = StreamEx.of(appointment.getAppointmentPersonParticipants())
                .map(app -> app.getPerson().getName())
                .filter(name -> !members.contains(name))
                .toList();

        List<AppointmentPersonParticipant> appsToDelete = StreamEx.of(appointment.getAppointmentPersonParticipants())
                .filter(app -> removed.contains(app.getPerson().getName()))
                .toList();

        appointment.removePersonParticipants(appsToDelete);

        for (AppointmentPersonParticipant app : appsToDelete) {
            appointmentPersonParticipantRepository.deleteByAppointmentPersonParticipant(app.getPerson().getId(), app.getAppointment().getId());
        }

        for (String name : newMembers) {
            Person person = personRepository.findByName(name).orElse(null);
            if (person != null) {
                AppointmentPersonParticipant app = new AppointmentPersonParticipant(person, appointment);
                appointmentPersonParticipantRepository.save(app);
            }
        }
    }

    private void handleChildrenChange(CreateAppointmentRequest request, Appointment appointment) {
        List<String> children = request.getChildren();

        List<String> existingNames = StreamEx.of(appointment.getAppointmentChildParticipants())
                .map(acp -> acp.getChild().getFullName())
                .toList();

        List<String> newChildren = StreamEx.of(children).filter(c -> !existingNames.contains(c)).toList();

        List<String> removed = StreamEx.of(appointment.getAppointmentChildParticipants())
                .map(acp -> acp.getChild().getFullName())
                .filter(name -> !children.contains(name))
                .toList();

        List<AppointmentChildParticipant> acpsToDelete = StreamEx.of(appointment.getAppointmentChildParticipants())
                .filter(acp -> removed.contains(acp.getChild().getFullName()))
                .toList();

        appointment.removeChildParticipants(acpsToDelete);

        for (AppointmentChildParticipant acp : acpsToDelete) {
            appointmentChildParticipantRepository.deleteByAppointmentChildParticipant(acp.getChild().getId(), acp.getAppointment().getId());
        }

        for (String name : newChildren) {
            Child child = childRepository.findByFullName(name).orElse(null);
            if (child != null) {
                AppointmentChildParticipant acp = new AppointmentChildParticipant(child, appointment);
                appointmentChildParticipantRepository.save(acp);
            }
        }
    }
}
