package com.smartcare.hospital.dto.response;

public class DepartmentResponse {
    private Long departmentId;
    private String departmentName;
    private String location;
    private Long headDoctorId;
    public DepartmentResponse() { }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long value) { departmentId = value; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String value) { departmentName = value; }
    public String getLocation() { return location; }
    public void setLocation(String value) { location = value; }
    public Long getHeadDoctorId() { return headDoctorId; }
    public void setHeadDoctorId(Long value) { headDoctorId = value; }
}
