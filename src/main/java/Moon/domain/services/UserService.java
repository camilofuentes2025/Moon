package Moon.domain.services;

import Moon.domain.models.User;
import Moon.ports.UserPort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserPort userPort;

    public void registerUser(User user) throws Exception {
        if (userPort.findByEmail(user.getEmail()) != null) {
            throw new Exception("❌ Error: Este correo ya está registrado.");
        }

        if (user.getAge() < 18) {
            throw new Exception("❌ Error: Debes ser mayor de edad para registrarte.");
        }

        userPort.saveUser(user);
        System.out.println("✅ Usuario registrado exitosamente.");
    }

    public void recoverPassword(String email) {
        User user = userPort.findByEmail(email);
        
        if (user == null) {
            System.out.println("❌ No se encontró ningún usuario con ese correo.");
            return;
        }
        
        String newPassword = generateTemporaryPassword();
        
        // 🔥 CAMBIO: Usar método específico para actualizar contraseña
        userPort.updatePassword(email, newPassword);
        
        System.out.println("✅ Tu nueva contraseña temporal es: " + newPassword);
    }

    private String generateTemporaryPassword() {
        return "temp" + (int) (Math.random() * 10000);
    }

   /* public void changeRole(String email, String role) {
        if (!isValidRole(role)) {
            throw new IllegalArgumentException("❌ Error: Rol inválido. Solo se permiten ADMIN o CLIENTE.");
        }

        User user = userPort.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("❌ Error: No se encontró ningún usuario con ese correo.");
        }

        user.setRol(role);
        userPort.saveUser(user);

        System.out.println("✅ Rol actualizado exitosamente a: " + role);
    }

    private boolean isValidRole(String role) {
        return List.of("ADMIN", "CLIENTE").contains(role.toUpperCase());
    }*/
}