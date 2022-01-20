package de.phoenix.wgtest.repository.management;

import de.phoenix.wgtest.model.management.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
