package model;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoomMapper {

    // Query to get all rooms
    @Select("SELECT * FROM rooms")
    List<Room> getAllRooms();

    // Query to get a room by its number
    @Select("SELECT * FROM rooms WHERE room_number = #{roomNumber}")
    Room getRoomByNumber(@Param("roomNumber") int roomNumber);

    // Query to add a new room
    @Insert("INSERT INTO rooms (room_number, type, price, is_available) " +
            "VALUES (#{roomNumber}, #{type}, #{price}, #{isAvailable})")
    @Options(useGeneratedKeys = true, keyProperty = "roomNumber")
    int addRoom(Room room);

    // Query to update a room
    @Update("UPDATE rooms SET type = #{type}, price = #{price}, is_available = #{isAvailable} " +
            "WHERE room_number = #{roomNumber}")
    int updateRoom(Room room);

    // Query to delete a room
    @Delete("DELETE FROM rooms WHERE room_number = #{roomNumber}")
    int deleteRoom(@Param("roomNumber") int roomNumber);
}
