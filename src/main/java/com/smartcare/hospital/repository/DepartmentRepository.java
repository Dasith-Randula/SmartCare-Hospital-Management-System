package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByDepartmentNameContainingIgnoreCase(String name);
}
