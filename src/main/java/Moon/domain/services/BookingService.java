package Moon.domain.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Moon.adapters.inputs.utils.RoomValidator;
import Moon.domain.models.Booking;
import Moon.domain.models.Room;
import Moon.domain.models.User;
import Moon.ports.BookingPort;
import Moon.ports.RoomPort;
import Moon.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Service
public class BookingService {

    @Autowired
    private BookingPort bookingPort;

    @Autowired
    private RoomPort roomPort;

    @Autowired
    private UserPort userPort;
    

    public void createBooking(Booking booking, String email, String motelName, String roomType) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null) {
            throw new Exception("Usuario no encontrado.");
        }

        // 📌 Validar tipo de habitación
        roomType = new RoomValidator().typeValidator(roomType);

        Room room = roomPort.findAvailableRoomByTypeAndMotelName(roomType, motelName); 
        if (room == null) {
            throw new Exception("No hay habitaciones disponibles de tipo '" + roomType + "' en " + motelName);
        }

        if (booking.getCheckIn().after(booking.getCheckOut())) { // 📌 Cambio de startTime a checkIn
            throw new Exception("Fechas inválidas.");
        }

        booking.setRoom(room);
        booking.setUser(user);
        booking.setStatus(true);

        bookingPort.saveBooking(booking);
        System.out.println("✅ Reserva creada exitosamente en " + motelName + " con ID " + booking.getBookingID());
    }
    
    
    public List<Booking> searchBookingsByBookingID(Long bookingID) throws Exception {
        List<Booking> booking = bookingPort.findAllByBookingID(bookingID); 
        if (booking == null || booking.isEmpty()) {
            throw new Exception("No se encontraron reservas para el ID proporcionado.");
        }

        return booking; 
    }
    
    public void cancelBooking(Long bookingID) throws Exception {
        Booking booking = bookingPort.findByBookingID(bookingID);
        if (booking == null) {
            throw new Exception("Reserva no encontrada.");
        }

        booking.setStatus(false);
        bookingPort.saveBooking(booking);
        System.out.println("Reserva cancelada exitosamente: ID " + bookingID);
    }
}
