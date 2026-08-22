package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Department;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.repository.DepartmentRepository;
import com.smartcare.hospital.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public DepartmentService(DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public Department createDepartment(Department department) {
        department.setHeadDoctor(null);
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = getDepartmentById(id);
        department.setDepartmentName(departmentDetails.getDepartmentName());
        department.setLocation(departmentDetails.getLocation());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        if (!doctorRepository.findByDepartmentDepartmentId(id).isEmpty()) {
            throw new IllegalStateException("Cannot delete a department with assigned doctors");
        }
        departmentRepository.delete(department);
    }

    public List<Department> searchDepartments(String name) {
        return departmentRepository.findByDepartmentNameContainingIgnoreCase(name);
    }

    public Department assignHeadDoctor(Long departmentId, Long doctorId) {
        Department department = getDepartmentById(departmentId);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        if (doctor.getDepartment() == null
                || !departmentId.equals(doctor.getDepartment().getDepartmentId())) {
            throw new IllegalArgumentException("Head doctor must belong to the same department");
        }

        department.setHeadDoctor(doctor);
        return departmentRepository.save(department);
    }
}
