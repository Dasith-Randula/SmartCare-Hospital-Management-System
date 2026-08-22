package com.smartcare.hospital.dto.response;

import java.time.LocalDate;

public class AdmissionResponse {
    private Long admissionId;
    private Long patientId;
    private Long roomId;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String bedNumber;
    private String admissionStatus;
    public AdmissionResponse() { }
    public Long getAdmissionId() { return admissionId; }
    public void setAdmissionId(Long value) { admissionId = value; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long value) { roomId = value; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate value) { admissionDate = value; }
    public LocalDate getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(LocalDate value) { dischargeDate = value; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String value) { bedNumber = value; }
    public String getAdmissionStatus() { return admissionStatus; }
    public void setAdmissionStatus(String value) { admissionStatus = value; }
}
