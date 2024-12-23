package controller;

import model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private final List<Room> rooms;

    public RoomController() {
        this.rooms = new ArrayList<>();
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public Room getRoomByNumber(int roomNumber) {
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    public boolean addRoom(Room room) {
        if (getRoomByNumber(room.getRoomNumber()) == null) {
            rooms.add(room);
            return true;
        }
        return false;
    }

    public boolean updateRoom(Room updatedRoom) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber() == updatedRoom.getRoomNumber()) {
                rooms.set(i, updatedRoom);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(int roomNumber) {
        return rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }
}
