package com.smartcare.hospital.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillResponse {
    private Long billId;
    private Long patientId;
    private LocalDate billDate;
    private BigDecimal consultationCharge;
    private BigDecimal roomCharge;
    private BigDecimal laboratoryCharge;
    private BigDecimal medicineCharge;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String paymentMethod;
    public BillResponse() { }
    public Long getBillId() { return billId; }
    public void setBillId(Long value) { billId = value; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long value) { patientId = value; }
    public LocalDate getBillDate() { return billDate; }
    public void setBillDate(LocalDate value) { billDate = value; }
    public BigDecimal getConsultationCharge() { return consultationCharge; }
    public void setConsultationCharge(BigDecimal value) { consultationCharge = value; }
    public BigDecimal getRoomCharge() { return roomCharge; }
    public void setRoomCharge(BigDecimal value) { roomCharge = value; }
    public BigDecimal getLaboratoryCharge() { return laboratoryCharge; }
    public void setLaboratoryCharge(BigDecimal value) { laboratoryCharge = value; }
    public BigDecimal getMedicineCharge() { return medicineCharge; }
    public void setMedicineCharge(BigDecimal value) { medicineCharge = value; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal value) { totalAmount = value; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String value) { paymentStatus = value; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String value) { paymentMethod = value; }
}
