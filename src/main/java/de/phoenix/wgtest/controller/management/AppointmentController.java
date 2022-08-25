package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.annotations.MeasureTime;
import de.phoenix.wgtest.model.management.Appointment;
import de.phoenix.wgtest.model.management.AppointmentType;
import de.phoenix.wgtest.payload.request.CreateAppointmentRequest;
import de.phoenix.wgtest.payload.request.CreateAppointmentTypeRequest;
import de.phoenix.wgtest.payload.response.AppointmentOverlapResponse;
import de.phoenix.wgtest.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @MeasureTime
    @GetMapping( value = "/get/all/{livingGroup}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Appointment> getAppointmentsByLivingGroup(@PathVariable String livingGroup) {
        return appointmentService.getAppointmentsByLivingGroup(livingGroup);
    }

    @MeasureTime
    @PostMapping("/check")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Set<AppointmentOverlapResponse> checkOverlaps(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.checkOverlaps(request);
    }

    @PostMapping("/alternative/earlier")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<CreateAppointmentRequest> getEarlierAlternatives(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.getEarlierAlternatives(request, 30);
    }

    @PostMapping("/alternative/later")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<CreateAppointmentRequest> getLaterAlternatives(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.getLaterAlternatives(request, 30);
    }

    @MeasureTime
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addAppointment(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.insertAppointment(request);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointment(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.updateAppointment(request);
    }

    @MeasureTime
    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        return appointmentService.deleteAppointment(id);
    }

    @GetMapping( value = "/get/types/all/{livingGroup}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public List<AppointmentType> getAppointmentTypesByLivingGroup(@PathVariable String livingGroup) {
        return appointmentService.getAppointmentTypesByLivingGroup(livingGroup);
    }

    @PostMapping("/add/type")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> addAppointmentType(@RequestBody CreateAppointmentTypeRequest createAppointmentTypeRequest) {
        return appointmentService.insertAppointmentType(createAppointmentTypeRequest);
    }

    @PostMapping("/update/type")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateAppointmentType(@RequestBody AppointmentType type) {
        return appointmentService.updateAppointmentType(type);
    }

    @DeleteMapping(value = "/delete/type/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteAppointmentType(@PathVariable Long id) {
        return appointmentService.deleteAppointmentType(id);
    }

}
