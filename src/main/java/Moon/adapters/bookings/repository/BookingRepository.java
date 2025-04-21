package Moon.adapters.bookings.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Moon.adapters.bookings.entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>{
	
	boolean existByBookingID(long bookingID);
    BookingEntity findByBookingID(long bookingID); 
    List<BookingEntity> findBookingsByBookingID(Long userID);

}
