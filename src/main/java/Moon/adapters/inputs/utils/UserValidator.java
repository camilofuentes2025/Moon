package Moon.adapters.inputs.utils;

import org.springframework.stereotype.Component;

@Component
public class UserValidator extends PersonValidator {

    public String emailValidator(String value) throws Exception {
        value = stringValidator(value, "Correo electrónico");
        if (!value.contains("@") || !value.contains(".")) {
            throw new Exception("El correo electrónico debe tener un formato válido (ejemplo: usuario@dominio.com).");
        }
        if (value.length() > 50) {
            throw new Exception("El correo electrónico no puede exceder los 50 caracteres.");
        }
        return value.trim();
    }

    public String passwordValidator(String value) throws Exception {
        value = stringValidator(value, "Contraseña");
        if (value.length() < 8) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!value.matches(".*[A-Z].*")) {
            throw new Exception("La contraseña debe incluir al menos una letra mayúscula.");
        }
        if (!value.matches(".*[a-z].*")) {
            throw new Exception("La contraseña debe incluir al menos una letra minúscula.");
        }
        if (!value.matches(".*\\d.*")) {
            throw new Exception("La contraseña debe incluir al menos un número.");
        }
        if (!value.matches(".*[@#$%^&+=].*")) {
            throw new Exception("La contraseña debe incluir al menos un carácter especial (@, #, $, %, ^, &, +, =).");
        }
        return value.trim();
    }

    public String rolValidator(String value) throws Exception {
        value = stringValidator(value, "Rol");
        if (!value.equalsIgnoreCase("ADMIN") &&
            !value.equalsIgnoreCase("CLIENTE") &&
            !value.equalsIgnoreCase("PROVEEDOR")) {
            throw new Exception("El rol debe ser uno de los siguientes: ADMIN, CLIENTE, PROVEEDOR.");
        }
        return value.trim().toUpperCase();
    }
}
