package com.smartcare.hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentUpdateRequest {

    @NotNull(message = "Patient is required")
    private Long patientId;
    @NotNull(message = "Doctor is required")
    private Long doctorId;
    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;
    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;
    @NotBlank(message = "Consultation room is required")
    @Size(max = 50)
    private String consultationRoom;
    @NotBlank(message = "Appointment status is required")
    @Pattern(regexp = "SCHEDULED|COMPLETED|CANCELLED", message = "Appointment status must be valid")
    private String appointmentStatus;

    public AppointmentUpdateRequest() { }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getConsultationRoom() { return consultationRoom; }
    public void setConsultationRoom(String consultationRoom) { this.consultationRoom = consultationRoom; }
    public String getAppointmentStatus() { return appointmentStatus; }
    public void setAppointmentStatus(String appointmentStatus) { this.appointmentStatus = appointmentStatus; }
}
