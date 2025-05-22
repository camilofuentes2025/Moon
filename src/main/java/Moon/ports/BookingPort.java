package Moon.ports;

import java.util.List;

import Moon.domain.models.Booking;
import Moon.domain.models.Motel;
import Moon.domain.models.User;

public interface BookingPort {
	
	boolean existBooking(long bookingID);
    void saveBooking(Booking booking);
    Booking findByBookingID(long bookingID); 
    List<Booking> findAllByBookingID(Long bookingID); 



}
