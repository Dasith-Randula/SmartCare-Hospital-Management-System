package com.smartcare.hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TreatmentRequest {

    @NotNull(message = "Patient is required")
    private Long patientId;
    @NotNull(message = "Doctor is required")
    private Long doctorId;
    @NotBlank(message = "Diagnosis is required")
    @Size(max = 255)
    private String diagnosis;
    private String prescription;
    private String treatmentNotes;
    @NotNull(message = "Treatment date is required")
    private LocalDate treatmentDate;

    public TreatmentRequest() { }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }
    public String getTreatmentNotes() { return treatmentNotes; }
    public void setTreatmentNotes(String treatmentNotes) { this.treatmentNotes = treatmentNotes; }
    public LocalDate getTreatmentDate() { return treatmentDate; }
    public void setTreatmentDate(LocalDate treatmentDate) { this.treatmentDate = treatmentDate; }
}
