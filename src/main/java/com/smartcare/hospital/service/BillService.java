package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Bill;
import com.smartcare.hospital.entity.Patient;
import com.smartcare.hospital.exception.ResourceNotFoundException;
import com.smartcare.hospital.repository.BillRepository;
import com.smartcare.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    private static final String PAID = "PAID";
    private static final String UNPAID = "UNPAID";

    private final BillRepository billRepository;
    private final PatientRepository patientRepository;

    public BillService(BillRepository billRepository, PatientRepository patientRepository) {
        this.billRepository = billRepository;
        this.patientRepository = patientRepository;
    }

    public Bill createBill(Bill bill) {
        bill.setPatient(resolvePatient(bill));
        prepareBill(bill);
        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
    }

    public Bill updateBill(Long id, Bill billDetails) {
        Bill bill = getBillById(id);
        bill.setPatient(resolvePatient(billDetails));
        bill.setBillDate(billDetails.getBillDate());
        bill.setConsultationCharge(billDetails.getConsultationCharge());
        bill.setRoomCharge(billDetails.getRoomCharge());
        bill.setLaboratoryCharge(billDetails.getLaboratoryCharge());
        bill.setMedicineCharge(billDetails.getMedicineCharge());
        bill.setPaymentStatus(billDetails.getPaymentStatus());
        bill.setPaymentMethod(billDetails.getPaymentMethod());
        prepareBill(bill);
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        Bill bill = getBillById(id);
        billRepository.delete(bill);
    }

    public List<Bill> getPatientBills(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        return billRepository.findByPatientPatientIdOrderByBillDateDesc(patientId);
    }

    public List<Bill> getBillsByPaymentStatus(String paymentStatus) {
        validatePaymentStatus(paymentStatus);
        return billRepository.findByPaymentStatus(paymentStatus);
    }

    public Bill markBillAsPaid(Long billId, String paymentMethod) {
        validatePaymentMethod(paymentMethod);
        Bill bill = getBillById(billId);
        bill.setPaymentStatus(PAID);
        bill.setPaymentMethod(paymentMethod);
        return billRepository.save(bill);
    }

    private void prepareBill(Bill bill) {
        requireCharges(bill);
        validatePaymentStatus(bill.getPaymentStatus());
        if (UNPAID.equals(bill.getPaymentStatus())) {
            bill.setPaymentMethod(null);
        } else {
            validatePaymentMethod(bill.getPaymentMethod());
        }
        bill.setTotalAmount(bill.getConsultationCharge()
                .add(bill.getRoomCharge())
                .add(bill.getLaboratoryCharge())
                .add(bill.getMedicineCharge()));
    }

    private void requireCharges(Bill bill) {
        if (bill.getConsultationCharge() == null
                || bill.getRoomCharge() == null
                || bill.getLaboratoryCharge() == null
                || bill.getMedicineCharge() == null) {
            throw new IllegalArgumentException("All bill charges are required");
        }
        if (bill.getConsultationCharge().compareTo(BigDecimal.ZERO) < 0
                || bill.getRoomCharge().compareTo(BigDecimal.ZERO) < 0
                || bill.getLaboratoryCharge().compareTo(BigDecimal.ZERO) < 0
                || bill.getMedicineCharge().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Bill charges cannot be negative");
        }
    }

    private Patient resolvePatient(Bill bill) {
        if (bill.getPatient() == null || bill.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        return patientRepository.findById(bill.getPatient().getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + bill.getPatient().getPatientId()));
    }

    private void validatePaymentStatus(String paymentStatus) {
        if (!PAID.equals(paymentStatus) && !UNPAID.equals(paymentStatus)) {
            throw new IllegalArgumentException("Payment status must be PAID or UNPAID");
        }
    }

    private void validatePaymentMethod(String paymentMethod) {
        if (!"CASH".equals(paymentMethod) && !"CARD".equals(paymentMethod)
                && !"ONLINE".equals(paymentMethod)) {
            throw new IllegalArgumentException("Payment method must be CASH, CARD or ONLINE");
        }
    }
}
