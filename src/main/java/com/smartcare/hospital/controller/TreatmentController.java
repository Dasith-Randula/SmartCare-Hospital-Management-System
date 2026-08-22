package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.TreatmentRequest;
import com.smartcare.hospital.dto.response.TreatmentResponse;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.entity.Treatment;
import com.smartcare.hospital.service.TreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping
    public ResponseEntity<TreatmentResponse> createTreatment(@Valid @RequestBody TreatmentRequest request) {
        return ResponseEntity.status(201).body(toResponse(treatmentService.createTreatment(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentResponse>> getAllTreatments() {
        return ResponseEntity.ok(treatmentService.getAllTreatments().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentResponse> getTreatmentById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(treatmentService.getTreatmentById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentResponse> updateTreatment(@PathVariable Long id,
                                                              @Valid @RequestBody TreatmentRequest request) {
        return ResponseEntity.ok(toResponse(treatmentService.updateTreatment(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<List<TreatmentResponse>> getPatientMedicalHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(treatmentService.getPatientMedicalHistory(patientId).stream()
                .map(this::toResponse).toList());
    }

    private Treatment toEntity(TreatmentRequest request) {
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctorId());
        Treatment treatment = new Treatment();
        treatment.setPatient(patient);
        treatment.setDoctor(doctor);
        treatment.setDiagnosis(request.getDiagnosis());
        treatment.setPrescription(request.getPrescription());
        treatment.setTreatmentNotes(request.getTreatmentNotes());
        treatment.setTreatmentDate(request.getTreatmentDate());
        return treatment;
    }

    private TreatmentResponse toResponse(Treatment treatment) {
        TreatmentResponse response = new TreatmentResponse();
        response.setTreatmentId(treatment.getTreatmentId());
        response.setPatientId(treatment.getPatient().getPatientId());
        response.setDoctorId(treatment.getDoctor().getDoctorId());
        response.setDiagnosis(treatment.getDiagnosis());
        response.setPrescription(treatment.getPrescription());
        response.setTreatmentNotes(treatment.getTreatmentNotes());
        response.setTreatmentDate(treatment.getTreatmentDate());
        return response;
    }
}
