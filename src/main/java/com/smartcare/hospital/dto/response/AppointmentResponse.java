package com.smartcare.hospital.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String consultationRoom;
    private String appointmentStatus;
    public AppointmentResponse() { }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long value) { appointmentId = value; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long value) { doctorId = value; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate value) { appointmentDate = value; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime value) { appointmentTime = value; }
    public String getConsultationRoom() { return consultationRoom; }
    public void setConsultationRoom(String value) { consultationRoom = value; }
    public String getAppointmentStatus() { return appointmentStatus; }
    public void setAppointmentStatus(String value) { appointmentStatus = value; }
}
