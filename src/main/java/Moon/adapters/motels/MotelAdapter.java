package Moon.adapters.motels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Moon.adapters.bookings.entity.BookingEntity;
import Moon.adapters.motels.entity.MotelEntity;
import Moon.adapters.motels.repository.MotelRepository;
import Moon.domain.models.Booking;
import Moon.domain.models.Motel;
import Moon.ports.MotelPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Service
public class MotelAdapter implements MotelPort {

    @Autowired
    private MotelRepository motelRepository;

    @Override
    public boolean existMotel(long motelID) {
        return motelRepository.existsByMotelID(motelID);
    }
    @Override
    public boolean existsMotelName(String motelName) {
        return motelRepository.existsByMotelName(motelName); 
    }

    @Override
    public void saveMotel(Motel motel) {
        MotelEntity motelEntity = motelAdapter(motel);
        motelRepository.save(motelEntity);
        motel.setMotelID(motelEntity.getMotelID());
    }

    @Override
    public Motel findByMotelID(long motelID) {
        MotelEntity motelEntity = motelRepository.findByMotelID(motelID);
        return motelAdapter(motelEntity);
    }
    
    @Override
    public Motel findByMotelName(String motelName) {
        MotelEntity motelEntity = motelRepository.findByMotelName(motelName);

        if (motelEntity == null) {
            throw new RuntimeException("No se encontró ningún motel con el nombre: " + motelName);
        }

        return motelAdapter(motelEntity);
    }

    @Override
    public List<Motel> findAllByMotelName(String motelName) {
        List<MotelEntity> motelEntities = motelRepository.findAllByMotelName(motelName);

        if (motelEntities == null || motelEntities.isEmpty()) {
            throw new RuntimeException("No se encontraron moteles con el nombre: " + motelName);
        }

        return motelEntities.stream()
                .map(this::motelAdapter)
                .collect(Collectors.toList());
    }
    
   

    private Motel motelAdapter(MotelEntity motelEntity) {
        if (motelEntity == null) {
            return null; 
        }
        Motel motel = new Motel();
        motel.setMotelID(motelEntity.getMotelID());
        motel.setMotelName(motelEntity.getMotelName());
        motel.setLocation(motelEntity.getLocation());
        motel.setMotelPhone(motelEntity.getMotelPhone());
       
        return motel;
    }

    private MotelEntity motelAdapter(Motel motel) {
        if (motel == null) {
            return null; 
        }
        MotelEntity motelEntity = new MotelEntity();
        motelEntity.setMotelID(motel.getMotelID());
        motelEntity.setMotelName(motel.getMotelName());
        motelEntity.setLocation(motel.getLocation());
        motelEntity.setMotelPhone(motel.getMotelPhone());
      
        return motelEntity;
    }
}