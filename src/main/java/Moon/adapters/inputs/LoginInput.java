package Moon.adapters.inputs;

import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.Utils;
import Moon.domain.services.LoginService;
import Moon.ports.InputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginInput implements InputPort {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserValidator userValidator;

    private final String MENU = "Seleccione una opción:"
            + "\n 1. Iniciar sesión."
            + "\n 2. Cerrar sesión."
            + "\n 3. Salir.";

    @Override
    public void menu() {
        while (true) {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();

            switch (option) {
                case "1" -> login();
                case "2" -> logout();
                case "3" -> {
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void login() {
        try {
            System.out.println("Ingrese su correo electrónico:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine()); // Valida el correo

            System.out.println("Ingrese su contraseña:");
            String password = Utils.getReader().nextLine(); // Aquí puedes agregar validación adicional para contraseñas

            boolean success = loginService.login(email, password); // Llama al servicio para autenticar
            if (success) {
                System.out.println("Inicio de sesión exitoso. ¡Bienvenido!");
            } else {
                System.out.println("Error: Credenciales inválidas. Intente nuevamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void logout() {
        try {
            boolean success = loginService.logout(); // Llama al servicio para cerrar sesión
            if (success) {
                System.out.println("Sesión cerrada exitosamente.");
            } else {
                System.out.println("No se encontró ninguna sesión activa.");
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar sesión: " + e.getMessage());
        }
    }
}