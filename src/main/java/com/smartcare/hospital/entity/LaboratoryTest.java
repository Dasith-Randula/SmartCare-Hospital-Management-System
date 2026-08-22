package com.smartcare.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "laboratory_tests")
public class LaboratoryTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_test_id")
    private Long labTestId;

    @NotNull(message = "Patient is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull(message = "Doctor is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotBlank(message = "Test name is required")
    @Size(max = 150)
    @Column(name = "test_name", nullable = false, length = 150)
    private String testName;

    @NotNull(message = "Test date is required")
    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "test_result", columnDefinition = "TEXT")
    private String testResult;

    @Size(max = 150)
    @Column(name = "technician_name", length = 150)
    private String technicianName;

    @NotBlank(message = "Test status is required")
    @Pattern(regexp = "REQUESTED|IN_PROGRESS|COMPLETED", message = "Test status must be valid")
    @Column(name = "test_status", nullable = false, length = 30)
    private String testStatus;

    @NotNull(message = "Test charge is required")
    @DecimalMin(value = "0.00", message = "Test charge cannot be negative")
    @Column(name = "test_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal testCharge;

    public LaboratoryTest() {
    }

    public Long getLabTestId() {
        return labTestId;
    }

    public void setLabTestId(Long labTestId) {
        this.labTestId = labTestId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public BigDecimal getTestCharge() {
        return testCharge;
    }

    public void setTestCharge(BigDecimal testCharge) {
        this.testCharge = testCharge;
    }
}
