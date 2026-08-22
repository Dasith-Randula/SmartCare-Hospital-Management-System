package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.BillRequest;
import com.smartcare.hospital.dto.response.BillResponse;
import com.smartcare.hospital.entity.Bill;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.service.BillService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody BillRequest request) {
        return ResponseEntity.status(201).body(toResponse(billService.createBill(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(billService.getBillById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillResponse> updateBill(@PathVariable Long id,
                                                    @Valid @RequestBody BillRequest request) {
        return ResponseEntity.ok(toResponse(billService.updateBill(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillResponse>> getPatientBills(@PathVariable Long patientId) {
        return ResponseEntity.ok(billService.getPatientBills(patientId).stream()
                .map(this::toResponse).toList());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillResponse>> getBillsByPaymentStatus(@PathVariable String status) {
        return ResponseEntity.ok(billService.getBillsByPaymentStatus(status).stream()
                .map(this::toResponse).toList());
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<BillResponse> markBillAsPaid(@PathVariable Long id,
                                                       @RequestParam String paymentMethod) {
        return ResponseEntity.ok(toResponse(billService.markBillAsPaid(id, paymentMethod)));
    }

    private Bill toEntity(BillRequest request) {
        Patient patient = new Patient();
        patient.setPatientId(request.getPatientId());
        Bill bill = new Bill();
        bill.setPatient(patient);
        bill.setBillDate(request.getBillDate());
        bill.setConsultationCharge(request.getConsultationCharge());
        bill.setRoomCharge(request.getRoomCharge());
        bill.setLaboratoryCharge(request.getLaboratoryCharge());
        bill.setMedicineCharge(request.getMedicineCharge());
        bill.setPaymentStatus(request.getPaymentStatus());
        bill.setPaymentMethod(request.getPaymentMethod());
        return bill;
    }

    private BillResponse toResponse(Bill bill) {
        BillResponse response = new BillResponse();
        response.setBillId(bill.getBillId());
        response.setPatientId(bill.getPatient().getPatientId());
        response.setBillDate(bill.getBillDate());
        response.setConsultationCharge(bill.getConsultationCharge());
        response.setRoomCharge(bill.getRoomCharge());
        response.setLaboratoryCharge(bill.getLaboratoryCharge());
        response.setMedicineCharge(bill.getMedicineCharge());
        response.setTotalAmount(bill.getTotalAmount());
        response.setPaymentStatus(bill.getPaymentStatus());
        response.setPaymentMethod(bill.getPaymentMethod());
        return response;
    }
}
