package Moon.ports;

import java.util.List;

import Moon.domain.models.Booking;

public interface BookingPort {
	
	boolean existBooking(long bookingID);
    void saveBooking(Booking booking);
    Booking findByBookingID(long bookingID); 
    List<Booking> findBookingsByBookingID(Long userID); 



}
