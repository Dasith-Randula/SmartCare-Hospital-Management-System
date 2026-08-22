package com.smartcare.hospital.dto.response;

import java.time.LocalDate;

public class PatientResponse {
    private Long patientId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String contactNumber;
    private String bloodGroup;
    private String emergencyContactName;
    private String emergencyContactNumber;
    public PatientResponse() { }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public String getFullName() { return fullName; }
    public void setFullName(String value) { fullName = value; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate value) { dateOfBirth = value; }
    public String getGender() { return gender; }
    public void setGender(String value) { gender = value; }
    public String getAddress() { return address; }
    public void setAddress(String value) { address = value; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String value) { contactNumber = value; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String value) { bloodGroup = value; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String value) { emergencyContactName = value; }
    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String value) { emergencyContactNumber = value; }
}
