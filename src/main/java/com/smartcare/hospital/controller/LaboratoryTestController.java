package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.LaboratoryTestRequest;
import com.smartcare.hospital.dto.response.LaboratoryTestResponse;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.entity.LaboratoryTest;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.service.LaboratoryTestService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/laboratory-tests")
public class LaboratoryTestController {

    private final LaboratoryTestService laboratoryTestService;

    public LaboratoryTestController(LaboratoryTestService laboratoryTestService) {
        this.laboratoryTestService = laboratoryTestService;
    }

    @PostMapping
    public ResponseEntity<LaboratoryTestResponse> createLaboratoryTest(
            @Valid @RequestBody LaboratoryTestRequest request) {
        return ResponseEntity.status(201).body(toResponse(
                laboratoryTestService.createLaboratoryTest(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<LaboratoryTestResponse>> getAllLaboratoryTests() {
        return ResponseEntity.ok(laboratoryTestService.getAllLaboratoryTests().stream()
                .map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryTestResponse> getLaboratoryTestById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(laboratoryTestService.getLaboratoryTestById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratoryTestResponse> updateLaboratoryTest(
            @PathVariable Long id, @Valid @RequestBody LaboratoryTestRequest request) {
        return ResponseEntity.ok(toResponse(
                laboratoryTestService.updateLaboratoryTest(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratoryTest(@PathVariable Long id) {
        laboratoryTestService.deleteLaboratoryTest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<List<LaboratoryTestResponse>> getPatientLaboratoryHistory(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(laboratoryTestService.getPatientLaboratoryHistory(patientId).stream()
                .map(this::toResponse).toList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<LaboratoryTestResponse>> getTestsByStatusAndDateRange(
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(laboratoryTestService
                .getTestsByStatusAndDateRange(status, startDate, endDate).stream()
                .map(this::toResponse).toList());
    }

    private LaboratoryTest toEntity(LaboratoryTestRequest request) {
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctorId());
        LaboratoryTest test = new LaboratoryTest();
        test.setPatient(patient);
        test.setDoctor(doctor);
        test.setTestName(request.getTestName());
        test.setTestDate(request.getTestDate());
        test.setTestResult(request.getTestResult());
        test.setTechnicianName(request.getTechnicianName());
        test.setTestStatus(request.getTestStatus());
        test.setTestCharge(request.getTestCharge());
        return test;
    }

    private LaboratoryTestResponse toResponse(LaboratoryTest test) {
        LaboratoryTestResponse response = new LaboratoryTestResponse();
        response.setLabTestId(test.getLabTestId());
        response.setPatientId(test.getPatient().getPatientId());
        response.setDoctorId(test.getDoctor().getDoctorId());
        response.setTestName(test.getTestName());
        response.setTestDate(test.getTestDate());
        response.setTestResult(test.getTestResult());
        response.setTechnicianName(test.getTechnicianName());
        response.setTestStatus(test.getTestStatus());
        response.setTestCharge(test.getTestCharge());
        return response;
    }
}
