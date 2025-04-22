package Moon.adapters.inputs;

import Moon.adapters.inputs.utils.MotelValidator;
import Moon.adapters.inputs.utils.PersonValidator;
import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.Utils;
import Moon.domain.models.Motel;
import Moon.domain.models.Room;
import Moon.domain.services.MotelService;
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
public class MotelInput implements InputPort {

    @Autowired
    private MotelService motelService;

    @Autowired
    private MotelValidator motelValidator; 

    @Autowired
    private UserValidator userValidator;

    private final String MENU = "Seleccione una opción:"
            + "\n 1. Registrar un nuevo motel."
            + "\n 2. Buscar moteles."
            + "\n 3. Actualizar detalles de un motel."
            + "\n 4. Salir.";

    @Override
    public void menu() {
        while (true) {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();

            switch (option) {
                case "1" -> registerMotel();
                case "2" -> searchMotels();
                case "3" -> updateMotel();
                case "4" -> {
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void registerMotel() {
        try {
            System.out.println("Ingrese el correo electrónico del administrador:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine()); // Validación del correo

            System.out.println("Ingrese el nombre del motel:");
            String motelName = motelValidator.motelNameValidator(Utils.getReader().nextLine()); // Validación del nombre

            System.out.println("Ingrese la ubicación del motel:");
            String location = motelValidator.locationValidator(Utils.getReader().nextLine()); // Validación de la ubicación

            System.out.println("Ingrese el teléfono del motel:");
            long phone = motelValidator.motelPhoneValidator(Utils.getReader().nextLine()); // Validación del teléfono

            Motel motel = new Motel();
            motel.setMotelName(motelName);
            motel.setLocation(location);
            motel.setMotelPhone(phone);

            motelService.createMotel(motel, email); // Se elimina la referencia a habitaciones
            System.out.println("Motel registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar el motel: " + e.getMessage());
        }
    }

    private void searchMotels() {
        try {
            System.out.println("Ingrese la ubicación para buscar moteles:");
            String location = motelValidator.locationValidator(Utils.getReader().nextLine()); // Validación

            System.out.println("Ingrese la fecha de check-in (YYYY-MM-DD):");
            Date checkIn = Date.valueOf(Utils.getReader().nextLine());

            System.out.println("Ingrese la fecha de check-out (YYYY-MM-DD):");
            Date checkOut = Date.valueOf(Utils.getReader().nextLine());

            motelService.searchMotels(location, checkIn, checkOut).forEach(motel ->
                    System.out.println("Motel encontrado: " + motel.getMotelName() + " en " + motel.getLocation())
            );
        } catch (Exception e) {
            System.out.println("Error al buscar moteles: " + e.getMessage());
        }
    }

    private void updateMotel() {
        try {
            System.out.println("Ingrese el ID del motel a actualizar:");
            Long motelID = Long.parseLong(Utils.getReader().nextLine());

            System.out.println("Ingrese el nuevo nombre del motel:");
            String motelName = motelValidator.motelNameValidator(Utils.getReader().nextLine()); // Validación

            System.out.println("Ingrese la nueva ubicación del motel:");
            String location = motelValidator.locationValidator(Utils.getReader().nextLine()); // Validación

            System.out.println("Ingrese el nuevo número de teléfono del motel:");
            long phone = motelValidator.motelPhoneValidator(Utils.getReader().nextLine()); // Validación

            System.out.println("Ingrese la disponibilidad del motel (true/false):");
            boolean availability = Boolean.parseBoolean(Utils.getReader().nextLine());

            System.out.println("Ingrese el correo del administrador:");
            String email = Utils.getReader().nextLine();

            Motel updatedMotel = new Motel();
            updatedMotel.setMotelName(motelName);
            updatedMotel.setLocation(location);
            updatedMotel.setMotelPhone(phone); 
            updatedMotel.setAvailability(availability);

            motelService.updateMotel(motelID, updatedMotel, email); 
            System.out.println("Motel actualizado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el motel: " + e.getMessage());
        }
    }
}