package Moon.domain.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MotelService {
	@Autowired
    private MotelPort motelPort;

    @Autowired
    private UserPort userPort;
    @Autowired
    private RoomPort roomPort;

    public void createMotel(Motel motel, String email, Room room) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null || !user.getRol().equalsIgnoreCase("ADMIN")) {
            throw new Exception("Solo los administradores pueden registrar moteles.");
        }

        if (motel == null || motel.getMotelName() == null || motel.getLocation() == null) {
            throw new Exception("El motel debe tener un nombre y ubicación válidos");
        }

        room.setAvailability(true);
        roomPort.saveRoom(room); 
        motelPort.saveMotel(motel); 
        System.out.println("Motel registrado exitosamente: " + motel.getMotelName());
    }
    

    public List<Motel> searchMotels(String location, Date checkIn, Date checkOut) throws Exception {
        if (location == null || checkIn == null || checkOut == null) {
            throw new Exception("Filtros incompletos");
        }

        if (checkIn.after(checkOut)) {
            throw new Exception("Fechas inválidas");
        }

        return motelPort.searchMotels(location, checkIn, checkOut);
    }
    
 
    public void updateMotel(Long motelID, Motel updatedMotel, String email) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null || !user.getRol().equalsIgnoreCase("ADMIN")) {
            throw new Exception("Solo los administradores pueden actualizar moteles.");
        }

        Motel existing = motelPort.findByMotelID(motelID);
        if (existing == null) {
            throw new Exception("Motel no encontrado.");
        }

        existing.setMotelName(updatedMotel.getMotelName());
        existing.setLocation(updatedMotel.getLocation());
        existing.setMotelPhone(updatedMotel.getMotelPhone());
        existing.setAvailability(updatedMotel.isAvailability());

        motelPort.saveMotel(existing);
        System.out.println("Motel actualizado: " + existing.getMotelName());
    }

}


