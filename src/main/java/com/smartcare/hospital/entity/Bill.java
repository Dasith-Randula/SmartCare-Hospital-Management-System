package com.smartcare.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    @NotNull(message = "Patient is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull(message = "Bill date is required")
    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;

    @NotNull(message = "Consultation charge is required")
    @DecimalMin(value = "0.00", message = "Consultation charge cannot be negative")
    @Column(name = "consultation_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationCharge;

    @NotNull(message = "Room charge is required")
    @DecimalMin(value = "0.00", message = "Room charge cannot be negative")
    @Column(name = "room_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal roomCharge;

    @NotNull(message = "Laboratory charge is required")
    @DecimalMin(value = "0.00", message = "Laboratory charge cannot be negative")
    @Column(name = "laboratory_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal laboratoryCharge;

    @NotNull(message = "Medicine charge is required")
    @DecimalMin(value = "0.00", message = "Medicine charge cannot be negative")
    @Column(name = "medicine_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal medicineCharge;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.00", message = "Total amount cannot be negative")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "Payment status is required")
    @Pattern(regexp = "PAID|UNPAID", message = "Payment status must be valid")
    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    @Pattern(regexp = "CASH|CARD|ONLINE", message = "Payment method must be CASH, CARD or ONLINE")
    @Column(name = "payment_method", length = 30)
    private String paymentMethod;

    public Bill() {
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getConsultationCharge() {
        return consultationCharge;
    }

    public void setConsultationCharge(BigDecimal consultationCharge) {
        this.consultationCharge = consultationCharge;
    }

    public BigDecimal getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(BigDecimal roomCharge) {
        this.roomCharge = roomCharge;
    }

    public BigDecimal getLaboratoryCharge() {
        return laboratoryCharge;
    }

    public void setLaboratoryCharge(BigDecimal laboratoryCharge) {
        this.laboratoryCharge = laboratoryCharge;
    }

    public BigDecimal getMedicineCharge() {
        return medicineCharge;
    }

    public void setMedicineCharge(BigDecimal medicineCharge) {
        this.medicineCharge = medicineCharge;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
