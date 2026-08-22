package com.smartcare.hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AdmissionRequest {

    @NotNull(message = "Patient is required")
    private Long patientId;
    @NotNull(message = "Room is required")
    private Long roomId;
    @NotNull(message = "Admission date is required")
    private LocalDate admissionDate;
    @NotBlank(message = "Bed number is required")
    @Size(max = 20)
    private String bedNumber;

    public AdmissionRequest() { }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
}
