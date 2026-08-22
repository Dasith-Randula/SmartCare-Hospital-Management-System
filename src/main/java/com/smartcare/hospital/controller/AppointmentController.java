package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.AppointmentCreateRequest;
import com.smartcare.hospital.dto.request.AppointmentUpdateRequest;
import com.smartcare.hospital.dto.response.AppointmentResponse;
import com.smartcare.hospital.entity.Appointment;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody AppointmentCreateRequest request) {
        return ResponseEntity.status(201).body(toResponse(appointmentService.bookAppointment(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(appointmentService.getAppointmentById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id,
                                                                 @Valid @RequestBody AppointmentUpdateRequest request) {
        return ResponseEntity.ok(toResponse(appointmentService.updateAppointment(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(appointmentService.cancelAppointment(id)));
    }

    @GetMapping("/doctor/{doctorId}/schedule")
    public ResponseEntity<List<AppointmentResponse>> getDoctorSchedule(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.getDoctorSchedule(doctorId, date).stream()
                .map(this::toResponse).toList());
    }

    private Appointment toEntity(AppointmentCreateRequest request) {
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctorId());
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setConsultationRoom(request.getConsultationRoom());
        return appointment;
    }

    private Appointment toEntity(AppointmentUpdateRequest request) {
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctorId());
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setConsultationRoom(request.getConsultationRoom());
        appointment.setAppointmentStatus(request.getAppointmentStatus());
        return appointment;
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(appointment.getAppointmentId());
        response.setPatientId(appointment.getPatient().getPatientId());
        response.setDoctorId(appointment.getDoctor().getDoctorId());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setConsultationRoom(appointment.getConsultationRoom());
        response.setAppointmentStatus(appointment.getAppointmentStatus());
        return response;
    }
}
