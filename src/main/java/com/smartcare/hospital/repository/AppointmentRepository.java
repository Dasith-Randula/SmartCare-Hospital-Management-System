package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(
            Long doctorId, LocalDate appointmentDate);

    boolean existsByDoctorDoctorIdAndAppointmentDateAndAppointmentTimeAndAppointmentStatusNot(
            Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime, String appointmentStatus);
}
