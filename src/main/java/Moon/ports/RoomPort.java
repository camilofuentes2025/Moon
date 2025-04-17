package Moon.ports;

import java.util.List;
import Moon.domain.models.Room;

public interface RoomPort {
	
	boolean existRoom(long roomID);
    void saveRoom(Room room);
    Room findByRoomID(long rommID);
    List<Room> findRoomsByMotelID(long motelID);
}
