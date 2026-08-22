package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Admission;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.entity.Room;
import com.smartcare.hospital.exception.InvalidOperationException;
import com.smartcare.hospital.exception.ResourceNotFoundException;
import com.smartcare.hospital.exception.RoomUnavailableException;
import com.smartcare.hospital.repository.AdmissionRepository;
import com.smartcare.hospital.repository.PatientRepository;
import com.smartcare.hospital.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdmissionService {

    private static final String ADMITTED = "ADMITTED";
    private static final String DISCHARGED = "DISCHARGED";

    private final AdmissionRepository admissionRepository;
    private final PatientRepository patientRepository;
    private final RoomRepository roomRepository;

    public AdmissionService(AdmissionRepository admissionRepository,
                            PatientRepository patientRepository,
                            RoomRepository roomRepository) {
        this.admissionRepository = admissionRepository;
        this.patientRepository = patientRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Admission admitPatient(Admission admission) {
        Patient patient = resolvePatient(admission);
        Room room = resolveRoom(admission);
        if (!"AVAILABLE".equals(room.getAvailabilityStatus())
                || admissionRepository.existsByRoomRoomIdAndAdmissionStatus(room.getRoomId(), ADMITTED)) {
            throw new RoomUnavailableException("Room is not available");
        }

        admission.setPatient(patient);
        admission.setRoom(room);
        admission.setAdmissionStatus(ADMITTED);
        admission.setDischargeDate(null);
        Admission savedAdmission = admissionRepository.save(admission);
        room.setAvailabilityStatus("OCCUPIED");
        roomRepository.save(room);
        return savedAdmission;
    }

    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    public Admission getAdmissionById(Long id) {
        return admissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found with id: " + id));
    }

    public List<Admission> getPatientAdmissions(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        return admissionRepository.findByPatientPatientIdOrderByAdmissionDateDesc(patientId);
    }

    @Transactional
    public Admission dischargePatient(Long id) {
        Admission admission = getAdmissionById(id);
        if (!ADMITTED.equals(admission.getAdmissionStatus())) {
            throw new InvalidOperationException("Admission is already discharged");
        }

        admission.setAdmissionStatus(DISCHARGED);
        admission.setDischargeDate(LocalDate.now());
        Admission savedAdmission = admissionRepository.save(admission);
        Room room = admission.getRoom();
        room.setAvailabilityStatus("AVAILABLE");
        roomRepository.save(room);
        return savedAdmission;
    }

    public void deleteAdmission(Long id) {
        Admission admission = getAdmissionById(id);
        if (ADMITTED.equals(admission.getAdmissionStatus())) {
            throw new InvalidOperationException(
                    "Cannot delete an active admission. Discharge the patient first");
        }
        admissionRepository.delete(admission);
    }

    private Patient resolvePatient(Admission admission) {
        if (admission.getPatient() == null || admission.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        return patientRepository.findById(admission.getPatient().getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + admission.getPatient().getPatientId()));
    }

    private Room resolveRoom(Admission admission) {
        if (admission.getRoom() == null || admission.getRoom().getRoomId() == null) {
            throw new IllegalArgumentException("Room is required");
        }
        return roomRepository.findById(admission.getRoom().getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room not found with id: " + admission.getRoom().getRoomId()));
    }
}
