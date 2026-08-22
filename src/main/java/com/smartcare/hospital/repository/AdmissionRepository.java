package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    List<Admission> findByPatientPatientIdOrderByAdmissionDateDesc(Long patientId);

    List<Admission> findByRoomRoomIdAndAdmissionStatus(Long roomId, String admissionStatus);

    boolean existsByRoomRoomIdAndAdmissionStatus(Long roomId, String admissionStatus);
}
