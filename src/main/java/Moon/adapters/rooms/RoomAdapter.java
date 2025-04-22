package Moon.adapters.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.rooms.entity.RoomEntity;
import Moon.adapters.motels.entity.MotelEntity;
import Moon.adapters.rooms.repository.RoomRepository;
import Moon.domain.models.Room;
import Moon.domain.models.Motel;
import Moon.ports.RoomPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Service
public class RoomAdapter implements RoomPort {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public boolean existRoom(long roomID) {
        return roomRepository.existByRoomID(roomID);
    }

    @Override
    public void saveRoom(Room room) {
        RoomEntity roomEntity = roomAdapter(room);
        roomRepository.save(roomEntity);
        room.setRoomID(roomEntity.getRoomID());
    }

    @Override
    public Room findByRoomID(long roomID) {
        RoomEntity roomEntity = roomRepository.findByRoomID(roomID);
        return roomAdapter(roomEntity);
    }

    @Override
    public List<Room> findRoomsByMotelName(String motelName) {
        List<RoomEntity> roomEntity = roomRepository.findRoomsByMotelName(motelName);
        return roomEntity.stream()
                .map(this::roomAdapter)
                .collect(Collectors.toList());
    }
    

    private Room roomAdapter(RoomEntity roomEntity) {
        if (roomEntity == null) {
            return null; 
        }

        Room room = new Room();
        room.setRoomID(roomEntity.getRoomID());
        room.setType(roomEntity.getType());
        room.setPrice(roomEntity.getPrice());
        room.setCharacteristics(roomEntity.getCharacteristics());
        room.setAvailability(roomEntity.getAvailability());
        room.setMotel(motelAdapter(roomEntity.getMotel())); // Relación con el Motel.
        return room;
    }

    private RoomEntity roomAdapter(Room room) {
        if (room == null) {
            return null; 
        }

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomID(room.getRoomID());
        roomEntity.setType(room.getType());
        roomEntity.setPrice(room.getPrice());
        roomEntity.setCharacteristics(room.getCharacteristics());
        roomEntity.setAvailability(room.isAvailability());

        MotelEntity motelEntity = new MotelEntity();
        if (room.getMotel() != null) { // Verifica si existe un objeto Motel.
            motelEntity.setMotelID(room.getMotel().getMotelID());
            roomEntity.setMotel(motelEntity); // Relación con el Motel.
        }

        return roomEntity;
    }

    private Motel motelAdapter(MotelEntity motelEntity) {
        if (motelEntity == null) {
            return null; 
        }
        Motel motel = new Motel();
        motel.setMotelID(motelEntity.getMotelID());
        motel.setMotelName(motelEntity.getMotelName());
        motel.setLocation(motelEntity.getLocation());
        motel.setMotelPhone(motelEntity.getMotelPhone());
        motel.setAvailability(motelEntity.isAvailability());
        return motel;
    }
}