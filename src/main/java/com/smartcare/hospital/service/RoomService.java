package com.smartcare.hospital.service;

import com.smartcare.hospital.entity.Room;
import com.smartcare.hospital.exception.ResourceNotFoundException;
import com.smartcare.hospital.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room) {
        room.setAvailabilityStatus("AVAILABLE");
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = getRoomById(id);
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setRoomCategory(roomDetails.getRoomCategory());
        room.setDailyCharge(roomDetails.getDailyCharge());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailabilityStatus("AVAILABLE");
    }

    public List<Room> getRoomsByCategory(String category) {
        return roomRepository.findByRoomCategory(category);
    }
}
