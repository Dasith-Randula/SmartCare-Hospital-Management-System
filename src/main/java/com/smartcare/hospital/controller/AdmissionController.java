package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.AdmissionRequest;
import com.smartcare.hospital.dto.response.AdmissionResponse;
import com.smartcare.hospital.entity.Admission;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.entity.Room;
import com.smartcare.hospital.service.AdmissionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public ResponseEntity<AdmissionResponse> admitPatient(@Valid @RequestBody AdmissionRequest request) {
        return ResponseEntity.status(201).body(toResponse(admissionService.admitPatient(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<AdmissionResponse>> getAllAdmissions() {
        return ResponseEntity.ok(admissionService.getAllAdmissions().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionResponse> getAdmissionById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(admissionService.getAdmissionById(id)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AdmissionResponse>> getPatientAdmissions(@PathVariable Long patientId) {
        return ResponseEntity.ok(admissionService.getPatientAdmissions(patientId).stream()
                .map(this::toResponse).toList());
    }

    @PatchMapping("/{id}/discharge")
    public ResponseEntity<AdmissionResponse> dischargePatient(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(admissionService.dischargePatient(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Long id) {
        admissionService.deleteAdmission(id);
        return ResponseEntity.noContent().build();
    }

    private Admission toEntity(AdmissionRequest request) {
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        Room room = new Room();
        room.setRoomId(request.getRoomId());
        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setRoom(room);
        admission.setAdmissionDate(request.getAdmissionDate());
        admission.setBedNumber(request.getBedNumber());
        return admission;
    }

    private AdmissionResponse toResponse(Admission admission) {
        AdmissionResponse response = new AdmissionResponse();
        response.setAdmissionId(admission.getAdmissionId());
        response.setPatientId(admission.getPatient().getPatientId());
        response.setRoomId(admission.getRoom().getRoomId());
        response.setAdmissionDate(admission.getAdmissionDate());
        response.setDischargeDate(admission.getDischargeDate());
        response.setBedNumber(admission.getBedNumber());
        response.setAdmissionStatus(admission.getAdmissionStatus());
        return response;
    }
}
