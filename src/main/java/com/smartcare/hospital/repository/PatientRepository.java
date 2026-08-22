package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFullNameContainingIgnoreCase(String name);
}
