package com.smartcare.hospital.dto.response;

import java.math.BigDecimal;

public class DoctorResponse {
    private Long doctorId;
    private String doctorName;
    private String specialization;
    private String qualification;
    private String contactNumber;
    private BigDecimal consultationFee;
    private Long departmentId;
    public DoctorResponse() { }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long value) { doctorId = value; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String value) { doctorName = value; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String value) { specialization = value; }
    public String getQualification() { return qualification; }
    public void setQualification(String value) { qualification = value; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String value) { contactNumber = value; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal value) { consultationFee = value; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long value) { departmentId = value; }
}
