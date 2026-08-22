package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.entity.LaboratoryTest;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.exception.ResourceNotFoundException;
import com.smartcare.hospital.repository.DoctorRepository;
import com.smartcare.hospital.repository.LaboratoryTestRepository;
import com.smartcare.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LaboratoryTestService {

    private static final String COMPLETED = "COMPLETED";

    private final LaboratoryTestRepository laboratoryTestRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public LaboratoryTestService(LaboratoryTestRepository laboratoryTestRepository,
                                 PatientRepository patientRepository,
                                 DoctorRepository doctorRepository) {
        this.laboratoryTestRepository = laboratoryTestRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public LaboratoryTest createLaboratoryTest(LaboratoryTest laboratoryTest) {
        validateCompletedResult(laboratoryTest);
        laboratoryTest.setPatient(resolvePatient(laboratoryTest));
        laboratoryTest.setDoctor(resolveDoctor(laboratoryTest));
        return laboratoryTestRepository.save(laboratoryTest);
    }

    public List<LaboratoryTest> getAllLaboratoryTests() {
        return laboratoryTestRepository.findAll();
    }

    public LaboratoryTest getLaboratoryTestById(Long id) {
        return laboratoryTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratory test not found with id: " + id));
    }

    public LaboratoryTest updateLaboratoryTest(Long id, LaboratoryTest testDetails) {
        validateCompletedResult(testDetails);
        LaboratoryTest laboratoryTest = getLaboratoryTestById(id);
        laboratoryTest.setPatient(resolvePatient(testDetails));
        laboratoryTest.setDoctor(resolveDoctor(testDetails));
        laboratoryTest.setTestName(testDetails.getTestName());
        laboratoryTest.setTestDate(testDetails.getTestDate());
        laboratoryTest.setTestResult(testDetails.getTestResult());
        laboratoryTest.setTechnicianName(testDetails.getTechnicianName());
        laboratoryTest.setTestStatus(testDetails.getTestStatus());
        laboratoryTest.setTestCharge(testDetails.getTestCharge());
        return laboratoryTestRepository.save(laboratoryTest);
    }

    public void deleteLaboratoryTest(Long id) {
        LaboratoryTest laboratoryTest = getLaboratoryTestById(id);
        laboratoryTestRepository.delete(laboratoryTest);
    }

    public List<LaboratoryTest> getPatientLaboratoryHistory(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        return laboratoryTestRepository.findByPatientPatientIdOrderByTestDateDesc(patientId);
    }

    public List<LaboratoryTest> getTestsByStatusAndDateRange(String status,
                                                              LocalDate startDate,
                                                              LocalDate endDate) {
        validateTestStatus(status);
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return laboratoryTestRepository.findByTestStatusAndTestDateBetween(status, startDate, endDate);
    }

    private void validateTestStatus(String status) {
        if (!"REQUESTED".equals(status)
                && !"IN_PROGRESS".equals(status)
                && !COMPLETED.equals(status)) {
            throw new IllegalArgumentException(
                    "Test status must be REQUESTED, IN_PROGRESS or COMPLETED");
        }
    }

    private void validateCompletedResult(LaboratoryTest laboratoryTest) {
        if (COMPLETED.equals(laboratoryTest.getTestStatus())
                && (laboratoryTest.getTestResult() == null || laboratoryTest.getTestResult().isBlank())) {
            throw new IllegalArgumentException("Completed laboratory test must have a test result");
        }
    }

    private Patient resolvePatient(LaboratoryTest laboratoryTest) {
        if (laboratoryTest.getPatient() == null || laboratoryTest.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        return patientRepository.findById(laboratoryTest.getPatient().getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + laboratoryTest.getPatient().getPatientId()));
    }

    private Doctor resolveDoctor(LaboratoryTest laboratoryTest) {
        if (laboratoryTest.getDoctor() == null || laboratoryTest.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
        return doctorRepository.findById(laboratoryTest.getDoctor().getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + laboratoryTest.getDoctor().getDoctorId()));
    }
}
