package Moon.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Moon.domain.models.Booking;
import Moon.domain.models.Motel;
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
    
 // Crear una reserva
    public void createBooking(Booking booking, String email, Long roomID) throws Exception {
        User user = userPort.findByEmail(email);
        if (user == null) {
            throw new Exception("Usuario no encontrado.");
        }

        Room room = roomPort.findByRoomID(roomID);
        if (room == null) {
            throw new Exception("Habitación no encontrada.");
        }

        if (!room.isAvailability()) {
            throw new Exception("La habitación no está disponible.");
        }

        if (booking.getStartTime().after(booking.getEndTime())) {
            throw new Exception("Fechas inválidas.");
        }

        booking.setRoom(room);
        booking.setUser(user);
        booking.setStatus(true); 
        //booking.setPayment(false); // opcional, pago pendiente

        room.setAvailability(false);
        roomPort.saveRoom(room);

        bookingPort.saveBooking(booking);
        System.out.println("Reserva creada exitosamente: ID " + booking.getBookingID());
    }
    
    public List<Booking> searchBookingsByBookingID(Long bookingID) throws Exception {
        List<Booking> bookings = bookingPort.findBookingsByBookingID(bookingID); // Buscar en el puerto.
        if (bookings == null || bookings.isEmpty()) {
            throw new Exception("No se encontraron reservas para el ID proporcionado.");
        }

        return bookings; // Devuelve la lista de reservas.
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
