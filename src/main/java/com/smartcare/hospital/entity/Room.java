package com.smartcare.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotBlank(message = "Room number is required")
    @Size(max = 20)
    @Column(name = "room_number", nullable = false, unique = true, length = 20)
    private String roomNumber;

    @NotBlank(message = "Room category is required")
    @Pattern(regexp = "GENERAL_WARD|PRIVATE_ROOM|ICU", message = "Room category must be valid")
    @Column(name = "room_category", nullable = false, length = 30)
    private String roomCategory;

    @NotBlank(message = "Availability status is required")
    @Pattern(regexp = "AVAILABLE|OCCUPIED", message = "Availability status must be valid")
    @Column(name = "availability_status", nullable = false, length = 20)
    private String availabilityStatus;

    @NotNull(message = "Daily charge is required")
    @DecimalMin(value = "0.00", message = "Daily charge cannot be negative")
    @Column(name = "daily_charge", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyCharge;

    public Room() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }
}
