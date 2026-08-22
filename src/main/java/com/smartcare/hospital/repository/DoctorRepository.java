package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByDoctorNameContainingIgnoreCase(String name);

    List<Doctor> findByDepartmentDepartmentId(Long departmentId);
}
