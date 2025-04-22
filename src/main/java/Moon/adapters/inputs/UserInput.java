package Moon.adapters.inputs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Moon.adapters.inputs.utils.PersonValidator;
import Moon.adapters.inputs.utils.UserValidator;
import Moon.adapters.inputs.utils.Utils;
import Moon.domain.models.User;
import Moon.domain.services.UserService;
import Moon.ports.InputPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Component
public class UserInput implements InputPort {

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService; 

    private final String MENU = "Seleccione una opción:"
            + "\n 1. Registrar usuario."
            + "\n 2. Recuperar contraseña."
            + "\n 3. Cambiar rol de un usuario."
            + "\n 4. Salir.";

    @Override
    public void menu() {
        while (true) {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();

            switch (option) {
                case "1" -> registerUser();
                case "2" -> recoverPassword();
                case "3" -> changeUserRole();
                case "4" -> {
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void registerUser() {
        try {
            System.out.println("Ingrese el nombre del usuario:");
            String name = personValidator.nameValidator(Utils.getReader().nextLine());

            System.out.println("Ingrese el correo electrónico:");
            String email = userValidator.emailValidator(Utils.getReader().nextLine());

            System.out.println("Ingrese la edad:");
            long age = personValidator.ageValidator(Utils.getReader().nextLine());

            System.out.println("Ingrese el rol del usuario (ADMIN, PROVEEDOR, CLIENTE):");
            String role = Utils.getReader().nextLine();

            System.out.println("Ingrese la contraseña:");
            String password = userValidator.passwordValidator(Utils.getReader().nextLine());

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setRol(role);
            user.setPassword(password);

            userService.registerUser(user); 
            System.out.println("Usuario registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    private void recoverPassword() {
        try {
            System.out.println("Ingrese el correo electrónico del usuario:");
            String email = Utils.getReader().nextLine();

            userService.recoverPassword(email); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void changeUserRole() {
        try {
            System.out.println("Ingrese el correo electrónico del usuario:");
            String email = Utils.getReader().nextLine();

            System.out.println("Ingrese el nuevo rol (ADMIN, PROVEEDOR, CLIENTE):");
            String role = Utils.getReader().nextLine();

            userService.changeRole(email, role); 
            System.out.println("Rol del usuario actualizado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}