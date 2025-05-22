package Moon.adapters.inputs;

import Moon.adapters.inputs.utils.RoomValidator;
import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.MotelValidator;
import Moon.adapters.inputs.utils.Utils;
import Moon.domain.models.Room;
import Moon.domain.services.MotelService;
import Moon.domain.services.RoomService;
import Moon.ports.InputPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class RoomInput implements InputPort {

    @Autowired
    private RoomService roomService;
    
    @Autowired
    private MotelService motelService;

    @Autowired
    private RoomValidator roomValidator;
    
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MotelValidator motelValidator;

    private final String MENU = """
        ===================================
              🏨 Gestión de Habitaciones   
        ===================================
        1️⃣ 🏠 Registrar una nueva habitación  
        2️⃣ 🛠️ Actualizar detalles de una habitación  
        3️⃣ ❌ Salir  
        -----------------------------------
        Por favor seleccione una opción:
        """;

    @Override
    public void menu() {
        while (true) {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();

            switch (option) {
                case "1" -> registerRoom();
                case "2" -> updateRoom();
                case "3" -> { 
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego! 👋");
                    return; // 📌 Evita que el menú de administración vuelva a mostrarse.
                }
                default -> System.out.println("⚠️ Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void registerRoom() {
        try {
            System.out.println("📌 Ingrese el nombre del motel asociado:");
            String motelName = motelValidator.motelNameValidator(Utils.getReader().nextLine());

            System.out.println("🏠 Ingrese el tipo de habitación (Sencilla, Doble, Suite, Familiar):");
            String type = roomValidator.typeValidator(Utils.getReader().nextLine());

            System.out.println("💲 Ingrese el precio de la habitación:");
            long price = roomValidator.priceValidator(Utils.getReader().nextLine());

            System.out.println("✍️ Ingrese las características de la habitación:");
            String characteristics = Utils.getReader().nextLine();

            System.out.println("📧 Ingrese el correo del administrador:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine());

            Room room = new Room();
            room.setType(type);
            room.setPrice(price);
            room.setCharacteristics(characteristics);
            room.setAvailability(true); 

            roomService.createRoom(motelName, room, email);
            System.out.println("✅ Habitación registrada exitosamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al registrar la habitación: " + e.getMessage());
        }
    }

    /*private void searchRooms() {
        try {
            System.out.println("🏨 Ingrese el nombre del motel para buscar habitaciones:");
            String motelName = Utils.getReader().nextLine();

            if (!motelService.isValidMotelName(motelName)) {
                throw new Exception("❌ No se encontró un motel válido con el nombre proporcionado.");
            }

            roomService.searchRoomsByMotelName(motelName).forEach(room ->
                System.out.println("🔍 Habitación encontrada: " + room.getType() + 
                                   ", 💲 Precio: " + room.getPrice() + 
                                   ", 📌 Disponibilidad: " + (room.isAvailability() ? "Disponible" : "Ocupada"))
            );
        } catch (Exception e) {
            System.out.println("❌ Error al buscar habitaciones: " + e.getMessage());
        }
    }*/

    private void updateRoom() {
        try {
            System.out.println("✏️ Ingrese el ID de la habitación a actualizar:");
            Long roomID = Long.parseLong(Utils.getReader().nextLine());

            System.out.println("🏠 Ingrese el nuevo tipo de habitación:");
            String type = roomValidator.typeValidator(Utils.getReader().nextLine());

            System.out.println("💲 Ingrese el nuevo precio de la habitación:");
            long price = roomValidator.priceValidator(Utils.getReader().nextLine());

            System.out.println("✍️ Ingrese las nuevas características de la habitación:");
            String characteristics = Utils.getReader().nextLine();

            System.out.println("📌 Ingrese la disponibilidad de la habitación (true/false):");
            boolean availability = Boolean.parseBoolean(Utils.getReader().nextLine());

            System.out.println("📧 Ingrese el correo del administrador:");
            String email = Utils.getReader().nextLine();

            Room updatedRoom = new Room();
            updatedRoom.setType(type);
            updatedRoom.setPrice(price);
            updatedRoom.setCharacteristics(characteristics);
            updatedRoom.setAvailability(availability);

            roomService.updateRoom(roomID, updatedRoom, email);
            System.out.println("✅ Habitación actualizada exitosamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar la habitación: " + e.getMessage());
        }
    }
}