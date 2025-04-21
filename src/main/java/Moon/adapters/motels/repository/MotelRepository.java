package Moon.adapters.motels.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Moon.adapters.motels.entity.MotelEntity;

public interface MotelRepository extends JpaRepository<MotelEntity, Long> {
 
    boolean existsByMotelID(long motelID);
    MotelEntity findByMotelID(long motelID);
    List<MotelEntity> findByLocationAndAvailability(String location, Date checkIn, Date checkOut);
    
}

