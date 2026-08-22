package com.smartcare.hospital.repository;

import com.smartcare.hospital.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByAvailabilityStatus(String availabilityStatus);

    List<Room> findByRoomCategory(String roomCategory);
}
