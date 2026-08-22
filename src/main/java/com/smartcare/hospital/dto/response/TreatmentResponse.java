package com.smartcare.hospital.dto.response;

import java.time.LocalDate;

public class TreatmentResponse {
    private Long treatmentId;
    private Long patientId;
    private Long doctorId;
    private String diagnosis;
    private String prescription;
    private String treatmentNotes;
    private LocalDate treatmentDate;
    public TreatmentResponse() { }
    public Long getTreatmentId() { return treatmentId; }
    public void setTreatmentId(Long value) { treatmentId = value; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long value) { doctorId = value; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String value) { diagnosis = value; }
    public String getPrescription() { return prescription; }
    public void setPrescription(String value) { prescription = value; }
    public String getTreatmentNotes() { return treatmentNotes; }
    public void setTreatmentNotes(String value) { treatmentNotes = value; }
    public LocalDate getTreatmentDate() { return treatmentDate; }
    public void setTreatmentDate(LocalDate value) { treatmentDate = value; }
}
