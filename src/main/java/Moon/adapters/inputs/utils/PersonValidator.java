package Moon.adapters.inputs.utils;

import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends SimpleValidator {

    public long documentValidator(String value) throws Exception {
        long document = longValidator(value, "Número de documento");
        if (document <= 0) {
            throw new Exception("El número de documento debe ser un valor positivo.");
        }
        if (String.valueOf(document).length() < 5 || String.valueOf(document).length() > 12) {
            throw new Exception("El número de documento debe contener entre 5 y 12 dígitos.");
        }
        return document;
    }

    public String nameValidator(String value) throws Exception {
        value = stringValidator(value, "Nombre");
        if (value.length() < 2 || value.length() > 20) {
            throw new Exception("El nombre debe tener entre 2 y 20 caracteres.");
        }
        return value.trim();
    }

    public long ageValidator(String value) throws Exception {
        long age = longValidator(value, "Edad");
        if (age < 18) {
            throw new Exception("La persona debe ser mayor de 18 años.");
        }
        return age;
    }
}