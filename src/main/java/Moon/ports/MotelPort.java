package Moon.ports;

import java.sql.Date;
import java.util.List;

import Moon.domain.models.Motel;

public interface MotelPort {
	
	boolean existMotel(long motelID);
    void saveMotel(Motel motel);
    Motel findByMotelID(long motelID);
    List<Motel> searchMotels(String location, Date checkIn, Date checkOut);

}

