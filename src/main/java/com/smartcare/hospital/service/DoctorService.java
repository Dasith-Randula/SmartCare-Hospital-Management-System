package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Department;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.repository.DepartmentRepository;
import com.smartcare.hospital.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
    }

    public Doctor createDoctor(Doctor doctor) {
        doctor.setDepartment(resolveDepartment(doctor));
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        doctor.setDoctorName(doctorDetails.getDoctorName());
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setQualification(doctorDetails.getQualification());
        doctor.setContactNumber(doctorDetails.getContactNumber());
        doctor.setConsultationFee(doctorDetails.getConsultationFee());
        doctor.setDepartment(resolveDepartment(doctorDetails));
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }

    public List<Doctor> searchDoctors(String name) {
        return doctorRepository.findByDoctorNameContainingIgnoreCase(name);
    }

    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        ensureDepartmentExists(departmentId);
        return doctorRepository.findByDepartmentDepartmentId(departmentId);
    }

    private Department resolveDepartment(Doctor doctor) {
        if (doctor.getDepartment() == null || doctor.getDepartment().getDepartmentId() == null) {
            throw new IllegalArgumentException("Department is required");
        }
        return departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                .orElseThrow(() -> new RuntimeException(
                        "Department not found with id: " + doctor.getDepartment().getDepartmentId()));
    }

    private void ensureDepartmentExists(Long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
    }
}
