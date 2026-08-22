package com.smartcare.hospital.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LaboratoryTestResponse {
    private Long labTestId;
    private Long patientId;
    private Long doctorId;
    private String testName;
    private LocalDate testDate;
    private String testResult;
    private String technicianName;
    private String testStatus;
    private BigDecimal testCharge;
    public LaboratoryTestResponse() { }
    public Long getLabTestId() { return labTestId; }
    public void setLabTestId(Long value) { labTestId = value; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long value) { doctorId = value; }
    public String getTestName() { return testName; }
    public void setTestName(String value) { testName = value; }
    public LocalDate getTestDate() { return testDate; }
    public void setTestDate(LocalDate value) { testDate = value; }
    public String getTestResult() { return testResult; }
    public void setTestResult(String value) { testResult = value; }
    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String value) { technicianName = value; }
    public String getTestStatus() { return testStatus; }
    public void setTestStatus(String value) { testStatus = value; }
    public BigDecimal getTestCharge() { return testCharge; }
    public void setTestCharge(BigDecimal value) { testCharge = value; }
}
