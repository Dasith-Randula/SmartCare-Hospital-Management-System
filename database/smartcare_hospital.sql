-- SmartCare Hospital Management System database schema

-- 1. Create database
CREATE DATABASE IF NOT EXISTS smartcare_hospital
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. Use database
USE smartcare_hospital;

-- 3. Drop tables in reverse dependency order
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS laboratory_tests;
DROP TABLE IF EXISTS treatments;
DROP TABLE IF EXISTS admissions;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS departments;

SET FOREIGN_KEY_CHECKS = 1;

-- 4. Departments are created first because doctors reference them.
-- The head-doctor foreign key is added after doctors are created.
CREATE TABLE departments (
    department_id BIGINT AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    head_doctor_id BIGINT NULL,
    PRIMARY KEY (department_id),
    UNIQUE KEY uq_departments_department_name (department_name),
    KEY idx_departments_head_doctor_id (head_doctor_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 5. Doctors
CREATE TABLE doctors (
    doctor_id BIGINT AUTO_INCREMENT,
    doctor_name VARCHAR(150) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    qualification VARCHAR(150) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    consultation_fee DECIMAL(10, 2) NOT NULL,
    department_id BIGINT NOT NULL,
    PRIMARY KEY (doctor_id),
    KEY idx_doctors_department_id (department_id),
    CONSTRAINT chk_doctors_consultation_fee
        CHECK (consultation_fee > 0),
    CONSTRAINT fk_doctors_department
        FOREIGN KEY (department_id)
        REFERENCES departments (department_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 6. Complete the circular department/head-doctor relationship.
-- The Spring Boot Service layer will ensure a department head belongs to that department.
ALTER TABLE departments
    ADD CONSTRAINT fk_departments_head_doctor
        FOREIGN KEY (head_doctor_id)
        REFERENCES doctors (doctor_id)
        ON UPDATE RESTRICT
        ON DELETE SET NULL;

-- 7. Patients
CREATE TABLE patients (
    patient_id BIGINT AUTO_INCREMENT,
    full_name VARCHAR(150) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    contact_number VARCHAR(20) NOT NULL,
    blood_group VARCHAR(5),
    emergency_contact_name VARCHAR(150),
    emergency_contact_number VARCHAR(20),
    PRIMARY KEY (patient_id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 8. Appointments
-- Java business rules will reject past appointment dates and detect clashes for the
-- same doctor/date/time while ignoring CANCELLED appointments.
CREATE TABLE appointments (
    appointment_id BIGINT AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    consultation_room VARCHAR(50) NOT NULL,
    appointment_status VARCHAR(30) NOT NULL,
    PRIMARY KEY (appointment_id),
    KEY idx_appointments_doctor_datetime
        (doctor_id, appointment_date, appointment_time),
    KEY idx_appointments_patient_id (patient_id),
    CONSTRAINT chk_appointments_status
        CHECK (appointment_status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT fk_appointments_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients (patient_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES doctors (doctor_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 9. Rooms
-- The Spring Boot Service layer will validate room availability before admission,
-- then set rooms to OCCUPIED after admission and AVAILABLE after discharge.
CREATE TABLE rooms (
    room_id BIGINT AUTO_INCREMENT,
    room_number VARCHAR(20) NOT NULL,
    room_category VARCHAR(30) NOT NULL,
    availability_status VARCHAR(20) NOT NULL,
    daily_charge DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (room_id),
    UNIQUE KEY uq_rooms_room_number (room_number),
    KEY idx_rooms_availability_status (availability_status),
    CONSTRAINT chk_rooms_category
        CHECK (room_category IN ('GENERAL_WARD', 'PRIVATE_ROOM', 'ICU')),
    CONSTRAINT chk_rooms_availability_status
        CHECK (availability_status IN ('AVAILABLE', 'OCCUPIED')),
    CONSTRAINT chk_rooms_daily_charge
        CHECK (daily_charge >= 0)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 10. Admissions
CREATE TABLE admissions (
    admission_id BIGINT AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    admission_date DATE NOT NULL,
    discharge_date DATE NULL,
    bed_number VARCHAR(20) NOT NULL,
    admission_status VARCHAR(20) NOT NULL,
    PRIMARY KEY (admission_id),
    KEY idx_admissions_patient_id (patient_id),
    KEY idx_admissions_room_status (room_id, admission_status),
    CONSTRAINT chk_admissions_status
        CHECK (admission_status IN ('ADMITTED', 'DISCHARGED')),
    CONSTRAINT chk_admissions_status_dates
        CHECK (
            (
                admission_status = 'ADMITTED'
                AND discharge_date IS NULL
            )
            OR
            (
                admission_status = 'DISCHARGED'
                AND discharge_date IS NOT NULL
                AND discharge_date >= admission_date
            )
        ),
    CONSTRAINT fk_admissions_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients (patient_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,
    CONSTRAINT fk_admissions_room
        FOREIGN KEY (room_id)
        REFERENCES rooms (room_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 11. Treatments
CREATE TABLE treatments (
    treatment_id BIGINT AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    diagnosis VARCHAR(255) NOT NULL,
    prescription TEXT,
    treatment_notes TEXT,
    treatment_date DATE NOT NULL,
    PRIMARY KEY (treatment_id),
    KEY idx_treatments_patient_id (patient_id),
    KEY idx_treatments_doctor_id (doctor_id),
    CONSTRAINT fk_treatments_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients (patient_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,
    CONSTRAINT fk_treatments_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES doctors (doctor_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 12. Laboratory tests
CREATE TABLE laboratory_tests (
    lab_test_id BIGINT AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    test_name VARCHAR(150) NOT NULL,
    test_date DATE NOT NULL,
    test_result TEXT NULL,
    technician_name VARCHAR(150),
    test_status VARCHAR(30) NOT NULL,
    test_charge DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (lab_test_id),
    KEY idx_laboratory_tests_patient_id (patient_id),
    KEY idx_laboratory_tests_doctor_id (doctor_id),
    KEY idx_laboratory_tests_status_date (test_status, test_date),
    CONSTRAINT chk_laboratory_tests_status
        CHECK (test_status IN ('REQUESTED', 'IN_PROGRESS', 'COMPLETED')),
    CONSTRAINT chk_laboratory_tests_charge
        CHECK (test_charge >= 0),
    CONSTRAINT fk_laboratory_tests_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients (patient_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,
    CONSTRAINT fk_laboratory_tests_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES doctors (doctor_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 13. Bills
-- The Spring Boot Service layer will calculate and validate bill totals before saving.
CREATE TABLE bills (
    bill_id BIGINT AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    bill_date DATE NOT NULL,
    consultation_charge DECIMAL(10, 2) NOT NULL DEFAULT 0,
    room_charge DECIMAL(10, 2) NOT NULL DEFAULT 0,
    laboratory_charge DECIMAL(10, 2) NOT NULL DEFAULT 0,
    medicine_charge DECIMAL(10, 2) NOT NULL DEFAULT 0,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    payment_method VARCHAR(30) NULL,
    PRIMARY KEY (bill_id),
    KEY idx_bills_patient_id (patient_id),
    KEY idx_bills_payment_status (payment_status),
    CONSTRAINT chk_bills_charges_non_negative
        CHECK (
            consultation_charge >= 0
            AND room_charge >= 0
            AND laboratory_charge >= 0
            AND medicine_charge >= 0
            AND total_amount >= 0
        ),
    CONSTRAINT chk_bills_total_amount
        CHECK (
            total_amount = consultation_charge
                + room_charge
                + laboratory_charge
                + medicine_charge
        ),
    CONSTRAINT chk_bills_payment_consistency
        CHECK (
            (
                payment_status = 'UNPAID'
                AND payment_method IS NULL
            )
            OR
            (
                payment_status = 'PAID'
                AND payment_method IN ('CASH', 'CARD', 'ONLINE')
            )
        ),
    CONSTRAINT fk_bills_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients (patient_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- Verify that all SmartCare tables were created successfully
SHOW TABLES;
