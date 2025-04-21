package Moon.domain.services;

import Moon.domain.models.User;
import Moon.ports.PersonPort;
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
    private PersonPort personPort;
    @Autowired
    private UserPort userPort;

        public void registerUser(User user) throws Exception {
            if (userPort.existEmail(user.getEmail())) {
                throw new Exception("correo yo en uso");
            }
            if (user.getAge() < 18) {
                throw new Exception("debes ser mayor de edad");
            }
            userPort.saveUser(user);
        }
        

        public void recoverPassword(String email) {
            User user = userPort.findByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("correo inexistente");
            }
            
            String newPassword = generateTemporaryPassword();

            user.setPassword(newPassword);
            userPort.saveUser(user);

            // Opcional: podrías enviar esta nueva contraseña por correo al usuario.
            System.out.println("nueva contraseña: " + newPassword); // Solo como referencia.
        }
        

        private String generateTemporaryPassword() {
            
            return "nueva1234"; 
        }
        
    
        public void changeRole(String email, String role) {
            
            if (!isValidRole(role)) {
                throw new IllegalArgumentException("Rol inválido. Los roles permitidos son: ADMIN, PROVEEDOR, CLIENTE.");
            }

            User user = userPort.findByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("Correo inexistente");
            }

            user.setRol(role);
            userPort.saveUser(user);

            System.out.println("Rol actualizado: " + role);
        }
        
        
        private boolean isValidRole(String role) {
            return List.of("ADMIN", "PROVEEDOR", "CLIENTE").contains(role.toUpperCase());
        
        }
 }

    
    
    
