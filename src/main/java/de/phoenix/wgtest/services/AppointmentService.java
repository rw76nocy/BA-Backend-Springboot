package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.payload.request.CreateAppointmentRequest;
import de.phoenix.wgtest.payload.request.CreateAppointmentTypeRequest;
import de.phoenix.wgtest.payload.response.MessageResponse;
import de.phoenix.wgtest.repository.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public ResponseEntity<?> insertAppointment(CreateAppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setTitle(request.getTitle());
        LocalDateTime start = LocalDateTime.parse(request.getStartDate() + "T" + request.getStartTime());
        appointment.setStartDate(Timestamp.valueOf(start));
        LocalDateTime end = LocalDateTime.parse(request.getEndDate() + "T" + request.getEndTime());
        appointment.setEndDate(Timestamp.valueOf(end));
        appointment.setLocation(request.getLocation());
        appointment.setAppointmentType(request.getAppointmentType());
        appointment.setHasInterval(request.hasInterval());

        if (request.hasInterval().equals("true")) {
            appointment.setIntervall(EInterval.findByName(request.getInterval()));
        }

        if (request.getIntervalEnd() != null && !request.getIntervalEnd().isEmpty()) {
            LocalDateTime iEnd = LocalDate.parse(request.getIntervalEnd()).atStartOfDay();
            appointment.setIntervalEnd(Timestamp.valueOf(iEnd));
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

        // employees
        for (Person p : request.getEmployees()) {
            Person person = personRepository.findById(p.getId()).orElse(null);
            if (person != null) {
                AppointmentPersonParticipant app = new AppointmentPersonParticipant(person, appointment);
                appointmentPersonParticipantRepository.save(app);
            }
        }

        // children
        for (Child c : request.getChildren()) {
            Child child = childRepository.findById(c.getId()).orElse(null);
            if (child != null) {
                AppointmentChildParticipant acp = new AppointmentChildParticipant(child, appointment);
                appointmentChildParticipantRepository.save(acp);
            }
        }

        return ResponseEntity.ok(new MessageResponse("Termin erfolgreich angelegt!"));
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
}
