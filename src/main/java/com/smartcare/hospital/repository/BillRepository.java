package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByPatientPatientIdOrderByBillDateDesc(Long patientId);

    List<Bill> findByPaymentStatus(String paymentStatus);
}
