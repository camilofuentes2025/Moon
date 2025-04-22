package Moon.domain.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Moon.adapters.motels.repository.MotelRepository;
import Moon.adapters.rooms.entity.RoomEntity;
import Moon.adapters.rooms.repository.RoomRepository;
import Moon.domain.models.Motel;
import Moon.domain.models.Room;
import Moon.domain.models.User;
import Moon.ports.MotelPort;
import Moon.ports.RoomPort;
import Moon.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Service

public class RoomService {
	
	@Autowired
    private RoomPort roomPort;

    @Autowired
    private UserPort userPort;

    @Autowired
    private MotelPort motelPort;
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private MotelRepository motelRepository;

    
    public void createRoom(String motelName, Room room, String email) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null || !user.getRol().equalsIgnoreCase("ADMIN")) {
            throw new Exception("Solo los administradores pueden registrar habitaciones.");
        }
        
        Motel motel = motelPort.findByMotelName(motelName);
        if (motel == null) {
            throw new Exception("Motel no encontrado.");
        }

        if (room == null || room.getType() == null || room.getPrice() <= 0) {
            throw new Exception("La habitación debe tener un tipo y un precio válidos.");
        }
        
        motel.setMotelName(motelName);
        room.setAvailability(true); 
        roomPort.saveRoom(room);
        System.out.println("Habitación creada exitosamente en el motel: " + motel.getMotelName());
    }
    

    public List<Room> searchRoomsByMotelName(String motelName) throws Exception {
        Motel motel = motelPort.findByMotelName(motelName);
        if (motel == null) {
            throw new Exception("Motel no encontrado.");
        }

        return roomPort.findRoomsByMotelName(motelName);
    }
    
    public void updateRoom(Long roomID, Room updatedRoom, String email) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null || !user.getRol().equalsIgnoreCase("ADMIN")) {
            throw new Exception("Solo los administradores pueden actualizar habitaciones.");
        }

        Room existingRoom = roomPort.findByRoomID(roomID);
        if (existingRoom == null) {
            throw new Exception("No se encontró la habitación con el ID proporcionado.");
        }

        existingRoom.setType(updatedRoom.getType());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setCharacteristics(updatedRoom.getCharacteristics());
        existingRoom.setAvailability(updatedRoom.isAvailability());

        roomPort.saveRoom(existingRoom);
        System.out.println("Habitación actualizada exitosamente: ID " + roomID);
    }
    
    public boolean isRoomAvailable(long roomID) {
        RoomEntity room = roomRepository.findByRoomID(roomID); 
        if (room == null || !room.getAvailability()) { 
            return false;
        }

        return true; 
    }
    
    public boolean isValidMotelName(String motelName) {
        if (motelName == null || motelName.trim().isEmpty()) return false;
        String normalizedName = motelName.trim().toLowerCase();
        return motelRepository.existsByMotelName(normalizedName);
    }



}
