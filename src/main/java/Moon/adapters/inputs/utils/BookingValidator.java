package Moon.adapters.inputs.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Moon.domain.models.Person;
import Moon.domain.models.Room;
import Moon.domain.services.MotelService;
import Moon.domain.services.RoomService;

import java.util.Date;

@Component
public class BookingValidator extends SimpleValidator {

    public Date startTimeValidator(Date checkIn) throws Exception {
        if (checkIn == null) {
            throw new Exception("La fecha de inicio de la reserva es requerida.");
        }
        Date currentDate = new Date();
        if (checkIn.before(currentDate)) {
            throw new Exception("La fecha de inicio de la reserva no puede ser anterior a la fecha actual.");
        }
        return checkIn;
    }
    

    public Date endTimeValidator(Date checkIn, Date checkOut) throws Exception {
        if (checkOut == null) {
            throw new Exception("La fecha de fin de la reserva es requerida.");
        }
        if (checkOut.before(checkIn)) {
            throw new Exception("La fecha de fin de la reserva no puede ser anterior a la fecha de inicio.");
        }
        return checkOut;
    }
    
    @Autowired
    private RoomService roomService; // Inyección del servicio de habitaciones

    public Room roomValidator(Room room) throws Exception {
        if (room == null) {
            throw new Exception("La reserva debe estar asociada a una habitación válida.");
        }

        if (!roomService.isRoomAvailable(room.getRoomID())) { // Uso de un método del servicio
            throw new Exception("La habitación seleccionada no está disponible.");
        }

        return room;
    }
    
    public Person userValidator(Person user) throws Exception {
        if (user == null) {
            throw new Exception("La reserva debe estar asociada a un usuario válido.");
        }
        if (user.getDocument() <= 0) { // Verifica que el documento sea un número positivo
            throw new Exception("El usuario debe tener un documento válido.");
        }
        return user;
    }
}

