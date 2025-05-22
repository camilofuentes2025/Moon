package Moon.adapters.inputs.utils;

import org.springframework.stereotype.Component;

@Component
public class MotelValidator extends SimpleValidator {

    public String motelNameValidator(String value) throws Exception {
        value = stringValidator(value, "Nombre del motel");
        if (value.length() < 3 || value.length() > 20) {
            throw new Exception("El nombre del motel debe tener entre 3 y 20 caracteres.");
        }
        return value.trim();
    }

    public String locationValidator(String value) throws Exception {
        value = stringValidator(value, "Ubicación del motel");
        if (value.length() < 5 || value.length() > 100) {
            throw new Exception("La ubicación debe tener entre 5 y 100 caracteres.");
        }
        return value.trim();
    }

    public long motelPhoneValidator(String value) throws Exception {
        long phone = longValidator(value, "Teléfono del motel");
        if (String.valueOf(phone).length() != 10) {
            throw new Exception("El teléfono del motel debe tener exactamente 10 dígitos.");
        }
        return phone;
    }

    /*public boolean availabilityValidator(String value) throws Exception {
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new Exception("La disponibilidad del motel debe ser 'true' o 'false'.");
        }
        return Boolean.parseBoolean(value);
    }*/
}


/*probablemente toque escribir un nuevo metodo en el motel service y el room service el cual sea que el sitema
 * calcule automaticamente si un motel esta o no esta disponible igual que su habitacion, puede funcionar en un futuro
 * el siguiente codigo:
 * 
 * @Service
public class MotelService {

    @Autowired
    private RoomService roomService; // Llama a los métodos de RoomService para manejar habitaciones.

    public boolean calculateMotelAvailability(long motelID) {
        List<Room> rooms = roomService.findRoomsByMotelID(motelID);
        for (Room room : rooms) {
            if (room.isAvailable()) {
                return true;
            }
        }
        return false; // Todas las habitaciones están ocupadas.
    }
    
    para room:
    
    public boolean calculateRoomAvailability(long roomID) {
    Booking booking = bookingService.findActiveBookingByRoomID(roomID);
    return booking == null; // Si no hay reservas activas, la habitación está disponible.
}

}

 * 
 * 
 * 
 */
