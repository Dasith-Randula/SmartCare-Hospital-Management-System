package com.smartcare.hospital.dto.response;

import java.math.BigDecimal;

public class RoomResponse {
    private Long roomId;
    private String roomNumber;
    private String roomCategory;
    private String availabilityStatus;
    private BigDecimal dailyCharge;
    public RoomResponse() { }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long value) { roomId = value; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String value) { roomNumber = value; }
    public String getRoomCategory() { return roomCategory; }
    public void setRoomCategory(String value) { roomCategory = value; }
    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String value) { availabilityStatus = value; }
    public BigDecimal getDailyCharge() { return dailyCharge; }
    public void setDailyCharge(BigDecimal value) { dailyCharge = value; }
}
