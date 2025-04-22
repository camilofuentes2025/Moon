package Moon.adapters.inputs;

import Moon.adapters.inputs.utils.Utils;
import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.RoomValidator;
import Moon.domain.models.Booking;
import Moon.domain.services.BookingService;
import Moon.ports.InputPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@Component
public class BookingInput implements InputPort {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoomValidator roomValidator;

    private final String MENU = "Seleccione una opción:"
            + "\n 1. Crear una nueva reserva."
            + "\n 2. Buscar reservas por ID."
            + "\n 3. Cancelar una reserva."
            + "\n 4. Salir.";

    @Override
    public void menu() {
        while (true) {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();

            switch (option) {
                case "1" -> createBooking();
                case "2" -> searchBooking();
                case "3" -> cancelBooking();
                case "4" -> {
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void createBooking() {
        try {
            System.out.println("Ingrese el correo electrónico del usuario:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine()); // Validación del correo

            System.out.println("Ingrese el ID de la habitación:");
            Long roomID = Long.parseLong(Utils.getReader().nextLine()); // Validación del ID opcional

            System.out.println("Ingrese la fecha y hora de inicio (YYYY-MM-DD HH:MM:SS):");
            Timestamp startTime = Timestamp.valueOf(Utils.getReader().nextLine()); // Convierte a timestamp

            System.out.println("Ingrese la fecha y hora de fin (YYYY-MM-DD HH:MM:SS):");
            Timestamp endTime = Timestamp.valueOf(Utils.getReader().nextLine()); // Convierte a timestamp

            Booking booking = new Booking();
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);

            bookingService.createBooking(booking, email, roomID); // Llama al servicio para crear la reserva
            System.out.println("Reserva creada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear la reserva: " + e.getMessage());
        }
    }

    private void searchBooking() {
        try {
            System.out.println("Ingrese el ID de la reserva:");
            Long bookingID = Long.parseLong(Utils.getReader().nextLine()); // Validación opcional

            bookingService.searchBookingsByBookingID(bookingID).forEach(booking ->
                    System.out.println("Reserva encontrada: ID: " + booking.getBookingID() + 
                                       ", Usuario: " + booking.getUser().getName() + 
                                       ", Habitación: " + booking.getRoom().getType() + 
                                       ", Fecha inicio: " + booking.getStartTime() + 
                                       ", Fecha fin: " + booking.getEndTime() +
                                       ", Estado: " + (booking.isStatus() ? "Activa" : "Cancelada"))
            );
        } catch (Exception e) {
            System.out.println("Error al buscar reservas: " + e.getMessage());
        }
    }

    private void cancelBooking() {
        try {
            System.out.println("Ingrese el ID de la reserva a cancelar:");
            Long bookingID = Long.parseLong(Utils.getReader().nextLine()); // Validación opcional

            bookingService.cancelBooking(bookingID); // Llama al servicio para cancelar la reserva
            System.out.println("Reserva cancelada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al cancelar la reserva: " + e.getMessage());
        }
    }
}