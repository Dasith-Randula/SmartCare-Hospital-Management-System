package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Appointment;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.exception.AppointmentConflictException;
import com.smartcare.hospital.exception.ResourceNotFoundException;
import com.smartcare.hospital.repository.AppointmentRepository;
import com.smartcare.hospital.repository.DoctorRepository;
import com.smartcare.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private static final String CANCELLED = "CANCELLED";
    private static final String SCHEDULED = "SCHEDULED";

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Appointment bookAppointment(Appointment appointment) {
        validateAppointmentDate(appointment.getAppointmentDate());
        Patient patient = resolvePatient(appointment);
        Doctor doctor = resolveDoctor(appointment);
        ensureNoConflict(doctor.getDoctorId(), appointment);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentStatus(SCHEDULED);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = getAppointmentById(id);
        Doctor doctor = resolveDoctor(appointmentDetails);
        boolean scheduleChanged = !sameId(appointment.getDoctor(), doctor)
                || !java.util.Objects.equals(appointment.getAppointmentDate(), appointmentDetails.getAppointmentDate())
                || !java.util.Objects.equals(appointment.getAppointmentTime(), appointmentDetails.getAppointmentTime());

        if (scheduleChanged) {
            validateAppointmentDate(appointmentDetails.getAppointmentDate());
            ensureNoConflict(doctor.getDoctorId(), appointmentDetails);
        }

        appointment.setPatient(resolvePatient(appointmentDetails));
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        appointment.setConsultationRoom(appointmentDetails.getConsultationRoom());
        appointment.setAppointmentStatus(appointmentDetails.getAppointmentStatus());
        return appointmentRepository.save(appointment);
    }

    public Appointment cancelAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setAppointmentStatus(CANCELLED);
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getDoctorSchedule(Long doctorId, LocalDate date) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));
        return appointmentRepository.findByDoctorDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(doctorId, date);
    }

    private void validateAppointmentDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }
    }

    private void ensureNoConflict(Long doctorId, Appointment appointment) {
        if (appointmentRepository.existsByDoctorDoctorIdAndAppointmentDateAndAppointmentTimeAndAppointmentStatusNot(
                doctorId, appointment.getAppointmentDate(), appointment.getAppointmentTime(), CANCELLED)) {
            throw new AppointmentConflictException("Doctor already has an appointment at this date and time");
        }
    }

    private Patient resolvePatient(Appointment appointment) {
        if (appointment.getPatient() == null || appointment.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        return patientRepository.findById(appointment.getPatient().getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + appointment.getPatient().getPatientId()));
    }

    private Doctor resolveDoctor(Appointment appointment) {
        if (appointment.getDoctor() == null || appointment.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
        return doctorRepository.findById(appointment.getDoctor().getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + appointment.getDoctor().getDoctorId()));
    }

    private boolean sameId(Doctor first, Doctor second) {
        return first != null && first.getDoctorId() != null
                && first.getDoctorId().equals(second.getDoctorId());
    }
}
