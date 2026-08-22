package com.smartcare.hospital.controller;

import com.smartcare.hospital.dto.request.RoomRequest;
import com.smartcare.hospital.dto.response.RoomResponse;
import com.smartcare.hospital.entity.Room;
import com.smartcare.hospital.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(201).body(toResponse(roomService.createRoom(toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(roomService.getRoomById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id,
                                                   @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(toResponse(roomService.updateRoom(id, toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms().stream().map(this::toResponse).toList());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RoomResponse>> getRoomsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(roomService.getRoomsByCategory(category).stream().map(this::toResponse).toList());
    }

    private Room toEntity(RoomRequest request) {
        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomCategory(request.getRoomCategory());
        room.setDailyCharge(request.getDailyCharge());
        return room;
    }

    private RoomResponse toResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setRoomId(room.getRoomId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomCategory(room.getRoomCategory());
        response.setAvailabilityStatus(room.getAvailabilityStatus());
        response.setDailyCharge(room.getDailyCharge());
        return response;
    }
}
