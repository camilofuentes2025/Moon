package Moon.ports;

import java.sql.Date;
import java.util.List;

import Moon.domain.models.Motel;

public interface MotelPort {
	
	boolean existMotel(long motelID);
	boolean existsMotelName(String motelName);
    void saveMotel(Motel motel);
    Motel findByMotelID(long motelID);
    List<Motel> findAllByMotelName(String motelName);
    Motel findByMotelName(String motelName);
}

