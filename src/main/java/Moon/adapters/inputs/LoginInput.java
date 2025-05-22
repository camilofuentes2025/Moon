package Moon.adapters.inputs;

import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.PersonValidator;
import Moon.domain.models.User;
import Moon.domain.services.LoginService;
import Moon.domain.services.UserService; // 📌 Se agregó UserService
import Moon.ports.InputPort;

import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginInput implements InputPort {

    private final LoginService loginService;
    private final UserService userService; // 📌 Se agregó UserService
    private final Map<String, InputPort> inputs;
    private final UserValidator userValidator;
    private final PersonValidator personValidator;

    @Autowired
    public LoginInput(LoginService loginService, UserService userService, UserValidator userValidator, PersonValidator personValidator, MotelInput motelInput, RoomInput roomInput, BookingInput bookingInput) {
        this.loginService = loginService;
        this.userService = userService; // 📌 Se inicializó UserService
        this.userValidator = userValidator;
        this.personValidator = personValidator;
        this.inputs = Map.of(
            "CLIENTE", bookingInput,
            "ADMIN", motelInput
        );
    }

    private final String MENU = """
        ===================================
               🌙 Moon - Inicio de Sesión   
        ===================================
        1️⃣ ✏️ Registrarse  
        2️⃣ 🔑 Iniciar sesión  
        3️⃣ 🚪 Cerrar sesión  
        4️⃣ ❌ Salir  
        -----------------------------------
        Por favor seleccione una opción:
        """;

    @Override
    public void menu() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println(MENU);
                String option = reader.readLine().trim();

                switch (option) {
                    case "1" -> register(reader);
                    case "2" -> login(reader);
                    case "3" -> logout();
                    case "4" -> {
                        System.out.println("✨ Gracias por usar Moon. ¡Hasta la próxima aventura! 🚀");
                        return;
                    }
                    default -> {
                        System.out.println("⚠️ Opción no válida. Intente nuevamente.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al procesar el menú: " + e.getMessage());
        }
    }

    private void register(BufferedReader reader) {
        try {
            System.out.println("=== 📝 Registro de Usuario ===");
            System.out.println("Ingrese su nombre:");
            String name = personValidator.nameValidator(reader.readLine());

            System.out.println("Ingrese su documento:");
            long document = personValidator.documentValidator(reader.readLine());

            System.out.println("Ingrese su teléfono:");
            long phone = personValidator.longValidator(reader.readLine(), "Teléfono");

            System.out.println("Ingrese su edad:");
            long age = personValidator.ageValidator(reader.readLine());

            System.out.println("Ingrese su correo electrónico:");
            String email = userValidator.emailValidator(reader.readLine());

            System.out.println("Ingrese su contraseña:");
            String password = userValidator.passwordValidator(reader.readLine());

            System.out.println("Seleccione su rol (CLIENTE):");
            String rol = userValidator.rolValidator(reader.readLine());

            User user = new User();
            user.setDocument(document);
            user.setName(name);
            user.setPhone(phone);
            user.setAge(age);
            user.setEmail(email);
            user.setPassword(password);
            user.setRol(rol);

            userService.registerUser(user); // 📌 Ahora usa UserService en lugar de LoginService
            System.out.println("🎉 ¡Registro exitoso! Ahora puede iniciar sesión.");

        } catch (Exception e) {
            System.out.println("❌ Error en el registro: " + e.getMessage());
        }
    }

    private void login(BufferedReader reader) {
        try {
            System.out.println("=== 🔐 Iniciar Sesión ===");
            System.out.println("Ingrese su correo electrónico:");
            String email = userValidator.emailValidator(reader.readLine());

            System.out.println("Ingrese su contraseña:");
            String password = reader.readLine().trim();

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            System.out.println("🔍 Validando credenciales...");
            User authenticatedUser = loginService.login(user);

            if (authenticatedUser == null || authenticatedUser.getRol() == null) {
                System.out.println("❌ Error: Credenciales inválidas o usuario sin rol asignado.");
                return;
            }

            InputPort inputPort = inputs.get(authenticatedUser.getRol());

            if (inputPort != null) {
                System.out.println("✅ Inicio de sesión exitoso. ¡Bienvenido, " + authenticatedUser.getName() + "!");
                inputPort.menu();

                // 📌 **Solución: Detenemos el flujo después del menú para evitar que regrese al principal**
                return;
            } else {
                System.out.println("❌ Error: No se encontró un menú para el rol '" + authenticatedUser.getRol() + "'.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void logout() {
        try {
            System.out.println("=== 🚪 Cerrar Sesión ===");
            System.out.println("Procesando su solicitud...");

            boolean success = loginService.logout();
            System.out.println(success ? "✅ Sesión cerrada exitosamente." : "❌ No se encontró ninguna sesión activa.");

        } catch (Exception e) {
            System.out.println("❌ Error al cerrar sesión: " + e.getMessage());
        }
    }
}