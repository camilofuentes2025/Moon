package Moon.adapters.motels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.motels.entity.MotelEntity;
import Moon.adapters.motels.repository.MotelRepository;
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
    public List<Motel> searchMotels(String location, Date checkIn, Date checkOut) {
        List<MotelEntity> motelEntity = motelRepository.findByLocationAndAvailability(location, checkIn, checkOut);
        return motelEntity.stream()
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
        motel.setAvailability(motelEntity.isAvailability());
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
        motelEntity.setAvailability(motel.isAvailability());
        return motelEntity;
    }
}