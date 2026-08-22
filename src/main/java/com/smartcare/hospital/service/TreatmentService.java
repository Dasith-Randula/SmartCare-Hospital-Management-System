package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.entity.Treatment;
import com.smartcare.hospital.exception.ResourceNotFoundException;
import com.smartcare.hospital.repository.DoctorRepository;
import com.smartcare.hospital.repository.PatientRepository;
import com.smartcare.hospital.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public TreatmentService(TreatmentRepository treatmentRepository,
                            PatientRepository patientRepository,
                            DoctorRepository doctorRepository) {
        this.treatmentRepository = treatmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Treatment createTreatment(Treatment treatment) {
        treatment.setPatient(resolvePatient(treatment));
        treatment.setDoctor(resolveDoctor(treatment));
        return treatmentRepository.save(treatment);
    }

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatmentById(Long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with id: " + id));
    }

    public Treatment updateTreatment(Long id, Treatment treatmentDetails) {
        Treatment treatment = getTreatmentById(id);
        treatment.setPatient(resolvePatient(treatmentDetails));
        treatment.setDoctor(resolveDoctor(treatmentDetails));
        treatment.setDiagnosis(treatmentDetails.getDiagnosis());
        treatment.setPrescription(treatmentDetails.getPrescription());
        treatment.setTreatmentNotes(treatmentDetails.getTreatmentNotes());
        treatment.setTreatmentDate(treatmentDetails.getTreatmentDate());
        return treatmentRepository.save(treatment);
    }

    public void deleteTreatment(Long id) {
        Treatment treatment = getTreatmentById(id);
        treatmentRepository.delete(treatment);
    }

    public List<Treatment> getPatientMedicalHistory(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        return treatmentRepository.findByPatientPatientIdOrderByTreatmentDateDesc(patientId);
    }

    private Patient resolvePatient(Treatment treatment) {
        if (treatment.getPatient() == null || treatment.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        return patientRepository.findById(treatment.getPatient().getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + treatment.getPatient().getPatientId()));
    }

    private Doctor resolveDoctor(Treatment treatment) {
        if (treatment.getDoctor() == null || treatment.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
        return doctorRepository.findById(treatment.getDoctor().getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + treatment.getDoctor().getDoctorId()));
    }
}
