package Moon.adapters.bookings.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import Moon.adapters.bookings.entity.BookingEntity;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.users.entity.UserEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>{
	
	boolean existsByBookingID(long bookingID);
    BookingEntity findByBookingID(long bookingID); 
    List<BookingEntity> findAllByBookingID(Long bookingID);

}
