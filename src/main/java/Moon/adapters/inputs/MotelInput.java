package Moon.adapters.inputs;

import Moon.adapters.inputs.utils.MotelValidator;
import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.Utils;
import Moon.domain.models.Motel;
import Moon.domain.models.User;
import Moon.domain.services.MotelService;
import Moon.domain.services.UserService;
import Moon.ports.InputPort;
import Moon.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class MotelInput implements InputPort {

    @Autowired
    private MotelService motelService;

    @Autowired
    private UserService userService; // 📌 Integrando la gestión de usuarios

    @Autowired
    private MotelValidator motelValidator; 

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoomInput roomInput;
    
    @Autowired
    private UserPort userPort;

    private final String MENU = """
        ===================================
               🏨 Panel de ADMIN  
        ===================================
        1️⃣ 🏠 Gestionar Moteles  
        2️⃣ 🛏️ Administrar Habitaciones  
        3️⃣ 🔑 Recuperar Contraseña    
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
                case "1" -> manageMotels();
                case "2" -> roomInput.menu();
                case "3" -> recoverPassword();           
                case "4" -> {
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego! 👋");
                    return;
                }
                default -> System.out.println("⚠️ Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void manageMotels() {
        while (true) { // 📌 Mantiene el flujo interno sin recargar `menu()`
            System.out.println("""
                ===================================
                       🏨 Gestión de Moteles  
                ===================================
                1️⃣ 🏠 Registrar un nuevo motel  
                2️⃣ 🔍 Buscar moteles  
                3️⃣ 🛠️ Actualizar detalles de un motel  
                4️⃣ ↩️ Volver  
                -----------------------------------
                Por favor seleccione una opción:
                """);
            String option = Utils.getReader().nextLine();

            switch (option) {
                case "1" -> registerMotel();
                case "2" -> searchMotels();
                case "3" -> updateMotel();
                case "4" -> { 
                    return; // 📌 Evita que `menu()` se recargue y regrese al admin después de salir.
                }
                default -> System.out.println("⚠️ Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void registerMotel() {
        try {
            System.out.println("🏠 Ingrese el nombre del motel:");
            String motelName = motelValidator.motelNameValidator(Utils.getReader().nextLine());

            System.out.println("📍 Ingrese la ubicación del motel:");
            String location = Utils.getReader().nextLine();

            System.out.println("📞 Ingrese el teléfono del motel:");
            long motelPhone = Long.parseLong(Utils.getReader().nextLine());

            System.out.println("📧 Ingrese el correo del administrador:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine());

            Motel motel = new Motel();
            motel.setMotelName(motelName);
            motel.setLocation(location);
            motel.setMotelPhone(motelPhone);

            motelService.createMotel(motel, email);
            System.out.println("✅ Motel registrado exitosamente.");

            // 📌 Aquí preguntamos si quiere agregar habitaciones
            System.out.println("🛏️ ¿Desea asignarle habitaciones ahora? (Sí/No)");
            String option = Utils.getReader().nextLine().trim().toLowerCase();

            if (option.equals("sí") || option.equals("si")) {
                System.out.println("🔄 Redirigiendo al menú de habitaciones...");
                roomInput.menu();
            }

        } catch (Exception e) {
            System.out.println("❌ Error al registrar el motel: " + e.getMessage());
        }
    }

    private void searchMotels() {
        try {
            System.out.println("🔍 Ingrese el nombre del motel para buscar:");
            String motelName = Utils.getReader().nextLine(); 

            Motel motel = motelService.findMotelByName(motelName);
            if (motel != null) {
                System.out.println("✅ Motel encontrado: " + motel.getMotelName() + 
                                   " 📍 Ubicación: " + motel.getLocation() + 
                                   " 📞 Teléfono: " + motel.getMotelPhone());
            } else {
                System.out.println("❌ No se encontró ningún motel con ese nombre.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al buscar motel: " + e.getMessage());
        }
    }

    private void updateMotel() {
        try {
            System.out.println("✏️ Ingrese el ID del motel a actualizar:");
            Long motelID = Long.parseLong(Utils.getReader().nextLine());

            System.out.println("🏠 Ingrese el nuevo nombre del motel:");
            String motelName = motelValidator.motelNameValidator(Utils.getReader().nextLine());

            System.out.println("📍 Ingrese la nueva ubicación del motel:");
            String location = motelValidator.locationValidator(Utils.getReader().nextLine());

            System.out.println("📞 Ingrese el nuevo número de teléfono del motel:");
            long phone = motelValidator.motelPhoneValidator(Utils.getReader().nextLine());

            System.out.println("📧 Ingrese el correo del administrador:");
            String email = Utils.getReader().nextLine();

            Motel updatedMotel = new Motel();
            updatedMotel.setMotelName(motelName);
            updatedMotel.setLocation(location);
            updatedMotel.setMotelPhone(phone); 

            motelService.updateMotel(motelID, updatedMotel, email);
            System.out.println("✅ Motel actualizado exitosamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar el motel: " + e.getMessage());
        }
    }

    private void recoverPassword() {
        try {
            System.out.println("🔑 Ingrese el correo electrónico del usuario:");
            String email = Utils.getReader().nextLine();

            userService.recoverPassword(email);
            System.out.println("✅ Si el correo es válido, se envió un enlace de recuperación.");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /*private void changeUserRole() {
        try {
            System.out.println("🔄 Ingrese el correo electrónico del usuario:");
            String email = Utils.getReader().nextLine();

            System.out.println("🔄 Ingrese el nuevo rol (ADMIN / CLIENTE):");
            String role = Utils.getReader().nextLine().trim().toUpperCase();

            if (!role.equals("ADMIN") && !role.equals("CLIENTE")) {
                System.out.println("❌ Rol inválido. Solo se permiten ADMIN o CLIENTE.");
                return;
            }

            // 📌 Cambiar userService.findByEmail(email) por userPort.findByEmail(email)
            System.out.println("🔍 Buscando usuario con email: " + email);
            User user = userPort.findByEmail(email);
            if (user == null) {
                System.out.println("❌ Error: No se encontró un usuario con ese correo.");
                return;
            }

            userService.changeRole(email, role);
            System.out.println("✅ Rol del usuario actualizado exitosamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }*/
}