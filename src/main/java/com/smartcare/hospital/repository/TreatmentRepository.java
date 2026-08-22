package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findByPatientPatientIdOrderByTreatmentDateDesc(Long patientId);
}
