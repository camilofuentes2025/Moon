package Moon.adapters.rooms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Moon.adapters.rooms.entity.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, Long>{
	
	boolean existByRoomID(long roomID);
	RoomEntity findByRoomID(long roomID);
	List<RoomEntity> findRoomsByMotelID(long motelID);

}
