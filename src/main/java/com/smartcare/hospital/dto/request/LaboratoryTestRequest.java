package com.smartcare.hospital.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LaboratoryTestRequest {

    @NotNull(message = "Patient is required")
    private Long patientId;
    @NotNull(message = "Doctor is required")
    private Long doctorId;
    @NotBlank(message = "Test name is required")
    @Size(max = 150)
    private String testName;
    @NotNull(message = "Test date is required")
    private LocalDate testDate;
    private String testResult;
    @Size(max = 150)
    private String technicianName;
    @NotBlank(message = "Test status is required")
    @Pattern(regexp = "REQUESTED|IN_PROGRESS|COMPLETED", message = "Test status must be valid")
    private String testStatus;
    @NotNull(message = "Test charge is required")
    @DecimalMin(value = "0.00", message = "Test charge cannot be negative")
    private BigDecimal testCharge;

    public LaboratoryTestRequest() { }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    public LocalDate getTestDate() { return testDate; }
    public void setTestDate(LocalDate testDate) { this.testDate = testDate; }
    public String getTestResult() { return testResult; }
    public void setTestResult(String testResult) { this.testResult = testResult; }
    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }
    public String getTestStatus() { return testStatus; }
    public void setTestStatus(String testStatus) { this.testStatus = testStatus; }
    public BigDecimal getTestCharge() { return testCharge; }
    public void setTestCharge(BigDecimal testCharge) { this.testCharge = testCharge; }
}
