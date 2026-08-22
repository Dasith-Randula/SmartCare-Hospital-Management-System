package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.DepartmentRequest;
import com.smartcare.hospital.dto.response.DepartmentResponse;
import com.smartcare.hospital.entity.Department;
import com.smartcare.hospital.service.DepartmentService;
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
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.status(201).body(toResponse(departmentService.createDepartment(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(departmentService.getDepartmentById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id,
                                                                @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(toResponse(departmentService.updateDepartment(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<DepartmentResponse>> searchDepartments(@RequestParam String name) {
        return ResponseEntity.ok(departmentService.searchDepartments(name).stream()
                .map(this::toResponse).toList());
    }

    @PutMapping("/{departmentId}/head-doctor/{doctorId}")
    public ResponseEntity<DepartmentResponse> assignHeadDoctor(@PathVariable Long departmentId,
                                                                @PathVariable Long doctorId) {
        return ResponseEntity.ok(toResponse(departmentService.assignHeadDoctor(departmentId, doctorId)));
    }

    private Department toEntity(DepartmentRequest request) {
        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());
        department.setLocation(request.getLocation());
        return department;
    }

    private DepartmentResponse toResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(department.getDepartmentId());
        response.setDepartmentName(department.getDepartmentName());
        response.setLocation(department.getLocation());
        response.setHeadDoctorId(department.getHeadDoctor() == null
                ? null : department.getHeadDoctor().getDoctorId());
        return response;
    }
}
