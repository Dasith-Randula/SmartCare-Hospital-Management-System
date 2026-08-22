package com.smartcare.hospital.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class RoomRequest {

    @NotBlank(message = "Room number is required")
    @Size(max = 20)
    private String roomNumber;
    @NotBlank(message = "Room category is required")
    @Pattern(regexp = "GENERAL_WARD|PRIVATE_ROOM|ICU", message = "Room category must be valid")
    private String roomCategory;
    @NotNull(message = "Daily charge is required")
    @DecimalMin(value = "0.00", message = "Daily charge cannot be negative")
    private BigDecimal dailyCharge;

    public RoomRequest() { }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getRoomCategory() { return roomCategory; }
    public void setRoomCategory(String roomCategory) { this.roomCategory = roomCategory; }
    public BigDecimal getDailyCharge() { return dailyCharge; }
    public void setDailyCharge(BigDecimal dailyCharge) { this.dailyCharge = dailyCharge; }
}
