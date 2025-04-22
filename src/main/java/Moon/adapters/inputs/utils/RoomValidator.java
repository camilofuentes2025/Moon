package Moon.adapters.inputs.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import Moon.domain.models.Motel;
import Moon.domain.services.MotelService;

@Component
public class RoomValidator extends SimpleValidator {

    public String typeValidator(String value) throws Exception {
        value = stringValidator(value, "Tipo de habitación");
        if (value.length() < 3 || value.length() > 30) {
            throw new Exception("El tipo de habitación debe tener entre 3 y 30 caracteres.");
        }
        if (!value.matches("(?i)(Sencilla|Doble|Suite|Familiar)")) { 
            throw new Exception("El tipo de habitación debe ser: Sencilla, Doble, Suite o Familiar.");
        }
        return value.trim();
    }

    public long priceValidator(String value) throws Exception {
        long price = longValidator(value, "Precio de la habitación");
        if (price <= 0) {
            throw new Exception("El precio debe ser un valor mayor a 0.");
        }
        return price;
    }
    
    @Autowired
    private MotelService motelService; 

    public Motel motelValidator(Motel motel) throws Exception {
        if (motel == null || motel.getMotelName() == null || motel.getMotelName().trim().isEmpty()) {
            throw new Exception("La habitación debe estar asociada a un motel con un nombre válido.");
        }

        String normalizedName = motel.getMotelName().trim().toLowerCase(); 
        if (!motelService.isValidMotelName(normalizedName)) { 
            throw new Exception("No existe un motel asociado con el nombre: " + motel.getMotelName());
        }

        return motel;
    }
}