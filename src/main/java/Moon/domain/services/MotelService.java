package Moon.domain.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Moon.adapters.motels.repository.MotelRepository;
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
    @Autowired
    private MotelRepository motelRepository;

    public void createMotel(Motel motel, String email) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null || !user.getRol().equalsIgnoreCase("ADMIN")) {
            throw new Exception("Solo los administradores pueden registrar moteles.");
        }

        if (motel == null || motel.getMotelName() == null || motel.getLocation() == null) {
            throw new Exception("El motel debe tener un nombre y ubicación válidos.");
        }

        motelPort.saveMotel(motel); 
        System.out.println("Motel registrado exitosamente: " + motel.getMotelName());
    }
    

    public Motel findMotelByName(String motelName) throws Exception {
        if (motelName == null || motelName.trim().isEmpty()) {
            throw new Exception("El nombre del motel no puede estar vacío.");
        }
        return motelPort.findByMotelName(motelName);
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
        

        motelPort.saveMotel(existing);
        System.out.println("Motel actualizado: " + existing.getMotelName());
    }
    
    public boolean isValidMotelName(String motelName) {
        if (motelName == null || motelName.trim().isEmpty()) return false;
        String normalizedName = motelName.trim().toLowerCase();
        return motelRepository.existsByMotelName(normalizedName);
    }

}


