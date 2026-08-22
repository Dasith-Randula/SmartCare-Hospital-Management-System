package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.DoctorRequest;
import com.smartcare.hospital.dto.response.DoctorResponse;
import com.smartcare.hospital.entity.Department;
import com.smartcare.hospital.entity.Doctor;
import com.smartcare.hospital.service.DoctorService;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.status(201).body(toResponse(doctorService.createDoctor(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(doctorService.getDoctorById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable Long id,
                                                        @Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(toResponse(doctorService.updateDoctor(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponse>> searchDoctors(@RequestParam String name) {
        return ResponseEntity.ok(doctorService.searchDoctors(name).stream().map(this::toResponse).toList());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorResponse>> getDoctorsByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(doctorService.getDoctorsByDepartment(departmentId).stream()
                .map(this::toResponse).toList());
    }

    private Doctor toEntity(DoctorRequest request) {
        Department department = new Department();
        department.setDepartmentId(request.getDepartmentId());
        Doctor doctor = new Doctor();
        doctor.setDoctorName(request.getDoctorName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setContactNumber(request.getContactNumber());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setDepartment(department);
        return doctor;
    }

    private DoctorResponse toResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setDoctorId(doctor.getDoctorId());
        response.setDoctorName(doctor.getDoctorName());
        response.setSpecialization(doctor.getSpecialization());
        response.setQualification(doctor.getQualification());
        response.setContactNumber(doctor.getContactNumber());
        response.setConsultationFee(doctor.getConsultationFee());
        response.setDepartmentId(doctor.getDepartment() == null ? null : doctor.getDepartment().getDepartmentId());
        return response;
    }
}
