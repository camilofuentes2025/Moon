package Moon.domain.services;

import Moon.domain.models.User;
import Moon.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class LoginService {

    @Autowired
    private UserPort userPort;

    public User login(User user) throws Exception {
        User userValidate = userPort.findByEmail(user.getEmail());
        if (userValidate == null) {
            throw new Exception("correo  incorrecto.");
        }
        if(!user.getPassword().equals(userValidate.getPassword())){
            throw new Exception("contraseña invalido");
        }
        return userValidate;
    }   
}
