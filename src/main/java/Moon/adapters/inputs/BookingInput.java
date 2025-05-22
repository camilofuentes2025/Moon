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

    private final String MENU = """
        ===================================
               🛏️ Gestión de Reservas  
        ===================================
        1️⃣ 📅 Crear una nueva reserva  
        2️⃣ 🔍 Buscar reservas por ID  
        3️⃣ ❌ Cancelar una reserva  
        4️⃣ 🚪 Salir  
        -----------------------------------
        Por favor seleccione una opción:
        """;

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
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego! 👋");
                    return; // 📌 Evita que el menú se repita después de salir.
                }
                default -> System.out.println("⚠️ Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void createBooking() {
        try {
            System.out.println("📧 Ingrese el correo electrónico del usuario:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine());

            System.out.println("🏨 Ingrese el nombre del motel:");
            String motelName = Utils.getReader().nextLine();

            System.out.println("🛏️ Ingrese el tipo de habitación (Sencilla, Doble, Suite, Familiar):");
            String roomType = roomValidator.typeValidator(Utils.getReader().nextLine());

            System.out.println("📅 Ingrese la fecha de inicio (YYYY-MM-DD):");
            Date checkIn = Date.valueOf(Utils.getReader().nextLine());

            System.out.println("📅 Ingrese la fecha de fin (YYYY-MM-DD):");
            Date checkOut = Date.valueOf(Utils.getReader().nextLine());

            Booking booking = new Booking();
            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);

            bookingService.createBooking(booking, email, motelName, roomType);
            System.out.println("✅ Reserva creada exitosamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al crear la reserva: " + e.getMessage());
        }
    }

    private void searchBooking() {
        try {
            System.out.println("🔍 Ingrese el ID de la reserva:");
            Long bookingID = Long.parseLong(Utils.getReader().nextLine()); 

            bookingService.searchBookingsByBookingID(bookingID).forEach(booking ->
                System.out.println("✅ Reserva encontrada: ID " + booking.getBookingID() +
                                   ", 📧 Usuario: " + booking.getUser().getName() +
                                   ", 🏠 Habitación: " + booking.getRoom().getType() +
                                   ", 📅 Fecha inicio: " + booking.getCheckIn() +
                                   ", 📅 Fecha fin: " + booking.getCheckOut() +
                                   ", ⏳ Estado: " + (booking.isStatus() ? "Activa" : "Cancelada"))
            );
        } catch (Exception e) {
            System.out.println("❌ Error al buscar reservas: " + e.getMessage());
        }
    }

    private void cancelBooking() {
        try {
            System.out.println("❌ Ingrese el ID de la reserva a cancelar:");
            Long bookingID = Long.parseLong(Utils.getReader().nextLine()); 

            bookingService.cancelBooking(bookingID);
            System.out.println("✅ Reserva cancelada exitosamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al cancelar la reserva: " + e.getMessage());
        }
    }
}