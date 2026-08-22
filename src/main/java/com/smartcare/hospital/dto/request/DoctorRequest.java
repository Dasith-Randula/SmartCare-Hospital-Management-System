package com.smartcare.hospital.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class DoctorRequest {

    @NotBlank(message = "Doctor name is required")
    @Size(max = 150)
    private String doctorName;
    @NotBlank(message = "Specialization is required")
    @Size(max = 100)
    private String specialization;
    @NotBlank(message = "Qualification is required")
    @Size(max = 150)
    private String qualification;
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^07\\d{8}$", message = "Contact number must be a valid Sri Lankan mobile number")
    private String contactNumber;
    @NotNull(message = "Consultation fee is required")
    @DecimalMin(value = "0.01", message = "Consultation fee must be greater than zero")
    private BigDecimal consultationFee;
    @NotNull(message = "Department is required")
    private Long departmentId;

    public DoctorRequest() { }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}
