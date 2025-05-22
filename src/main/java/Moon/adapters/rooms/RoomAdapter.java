package Moon.adapters.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.rooms.entity.RoomEntity;
import Moon.adapters.motels.entity.MotelEntity;
import Moon.adapters.motels.repository.MotelRepository;
import Moon.adapters.rooms.repository.RoomRepository;
import Moon.adapters.users.entity.UserEntity;
import Moon.domain.models.Room;
import Moon.domain.models.Motel;
import Moon.ports.RoomPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Service
public class RoomAdapter implements RoomPort {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MotelRepository motelRepository;

    @Override
    public boolean existRoom(long roomID) {
        return roomRepository.existsByRoomID(roomID);
    }

    @Override
    public void saveRoom(Room room) {
        // 📌 Recuperamos el `MotelEntity` antes de asignarlo a `RoomEntity`
        MotelEntity motelEntity = motelRepository.findByMotelName(room.getMotel().getMotelName());
        if (motelEntity == null) {
            throw new RuntimeException("❌ Error: No se encontró un motel con nombre '" + room.getMotel().getMotelName() + "'");
        }

        RoomEntity roomEntity = roomAdapter(room);
        roomEntity.setMotel(motelEntity); // 📌 Asignamos correctamente el motel

        roomRepository.save(roomEntity);
        room.setRoomID(roomEntity.getRoomID());
    }


    @Override
    public Room findByRoomID(long roomID) {
        RoomEntity roomEntity = roomRepository.findByRoomID(roomID);
        return roomAdapter(roomEntity);
    }

    @Override
    public List<Room> findByMotelName(String motelName) {
        // Buscar el MotelEntity por su nombre en la BD
        MotelEntity motelEntity = motelRepository.findByMotelName(motelName);

        if (motelEntity == null) {
            throw new RuntimeException("No se encontró un motel con el nombre: " + motelName);
        }

        // Obtener las habitaciones asociadas al motel encontrado
        List<RoomEntity> roomEntity = roomRepository.findByMotel(motelEntity);

        // Convertir las entidades a modelos
        return roomEntity.stream()
                .map(this::roomAdapter)
                .collect(Collectors.toList());
    }
    
    @Override
    public Room findAvailableRoomByTypeAndMotelName(String type, String motelName) {
        // 📌 Buscar el motel por su nombre
        MotelEntity motelEntity = motelRepository.findByMotelName(motelName);

        if (motelEntity == null) {
            throw new RuntimeException("No se encontró un motel con el nombre: " + motelName);
        }

        // 📌 Obtener habitaciones del motel, filtrar por tipo y disponibilidad
        List<RoomEntity> availableRooms = roomRepository.findByMotel(motelEntity)
            .stream()
            .filter(room -> room.isAvailability() && room.getType().equalsIgnoreCase(type)) // Filtra por tipo
            .collect(Collectors.toList());

        if (availableRooms.isEmpty()) {
            throw new RuntimeException("No hay habitaciones disponibles de tipo '" + type + "' en " + motelName);
        }

        // 📌 Retorna la primera habitación disponible del tipo solicitado
        return roomAdapter(availableRooms.get(0));
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
        room.setAvailability(roomEntity.isAvailability());
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

      
        if (room.getMotel() != null) { 
            MotelEntity motelEntity = new MotelEntity();
            motelEntity.setMotelID(room.getMotel().getMotelID());
            roomEntity.setMotel(motelEntity); 
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
        
        return motel;
    }

    private MotelEntity motelAdapter(Motel motel) {
        if (motel == null) {
            return null; 
        }
        MotelEntity motelEntity = new MotelEntity();
        motelEntity.setMotelID(motel.getMotelID());
        motelEntity.setMotelName(motel.getMotelName());
        motelEntity.setLocation(motel.getLocation());
        motelEntity.setMotelPhone(motel.getMotelPhone());
        
        return motelEntity;
    }
}