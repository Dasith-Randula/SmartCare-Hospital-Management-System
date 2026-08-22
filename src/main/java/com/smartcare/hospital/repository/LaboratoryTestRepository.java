package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.LaboratoryTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LaboratoryTestRepository extends JpaRepository<LaboratoryTest, Long> {

    List<LaboratoryTest> findByPatientPatientIdOrderByTestDateDesc(Long patientId);

    List<LaboratoryTest> findByTestStatusAndTestDateBetween(
            String testStatus, LocalDate startDate, LocalDate endDate);
}
