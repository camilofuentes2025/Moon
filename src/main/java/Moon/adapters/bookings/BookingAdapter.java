package Moon.adapters.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.bookings.entity.BookingEntity;
import Moon.adapters.bookings.repository.BookingRepository;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.rooms.entity.RoomEntity;
import Moon.domain.models.Booking;
import Moon.domain.models.Person;
import Moon.domain.models.Room;
import Moon.ports.BookingPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Service
public class BookingAdapter implements BookingPort {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean existBooking(long bookingID) {
        return bookingRepository.existByBookingID(bookingID);
    }

    @Override
    public void saveBooking(Booking booking) {
        BookingEntity bookingEntity = bookingAdapter(booking);
        bookingRepository.save(bookingEntity);
        booking.setBookingID(bookingEntity.getBookingID());
    }

    @Override
    public Booking findByBookingID(long bookingID) {
        BookingEntity bookingEntity = bookingRepository.findByBookingID(bookingID);
        return bookingAdapter(bookingEntity);
    }

    @Override
    public List<Booking> findBookingsByBookingID(Long userID) {
        List<BookingEntity> bookingEntities = bookingRepository.findBookingsByBookingID(userID);
        return bookingEntities.stream()
                .map(this::bookingAdapter)
                .collect(Collectors.toList());
    }

    private Booking bookingAdapter(BookingEntity bookingEntity) {
        if (bookingEntity == null) {
            return null; 
        }

        Booking booking = new Booking();
        booking.setBookingID(bookingEntity.getBookingID());
        booking.setStartTime(bookingEntity.getStartTime());
        booking.setEndTime(bookingEntity.getEndTime());
        booking.setStatus(bookingEntity.isStatus());
        booking.setPayment(bookingEntity.isPayment());
        booking.setRoom(roomAdapter(bookingEntity.getRoom())); // Relación con Room.
        booking.setUser(personAdapter(bookingEntity.getUser())); // Relación con Person.
        return booking;
    }

    private BookingEntity bookingAdapter(Booking booking) {
        if (booking == null) {
            return null; 
        }

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingID(booking.getBookingID());
        bookingEntity.setStartTime(booking.getStartTime());
        bookingEntity.setEndTime(booking.getEndTime());
        bookingEntity.setStatus(booking.isStatus());
        bookingEntity.setPayment(booking.isPayment());
        bookingEntity.setRoom(roomAdapter(booking.getRoom())); // Relación con Room.
        bookingEntity.setUser(personAdapter(booking.getUser())); // Relación con Person.
        return bookingEntity;
    }

    private Room roomAdapter(RoomEntity roomEntity) {
        if (roomEntity == null) {
            return null; 
        }

        Room room = new Room();
        room.setRoomID(roomEntity.getRoomID());
        room.setType(roomEntity.getType());
        room.setPrice(roomEntity.getPrice());
        room.setCharacteristics(roomEntity.getCharacteristics());
        room.setAvailability(roomEntity.isAvailability());
        room.setMotel(null); // Opcional: si necesitas adaptar Motel, agrega lógica similar a Room.
        return room;
    }

    private RoomEntity roomAdapter(Room room) {
        if (room == null) {
            return null; 
        }

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomID(room.getRoomID());
        roomEntity.setType(room.getType());
        roomEntity.setPrice(room.getPrice());
        roomEntity.setCharacteristics(room.getCharacteristics());
        roomEntity.setAvailability(room.isAvailability());
        roomEntity.setMotel(null); // Opcional: si necesitas adaptar Motel, agrega lógica similar.
        return roomEntity;
    }

    private Person personAdapter(PersonEntity personEntity) {
        if (personEntity == null) {
            return null; 
        }

        Person person = new Person();
        person.setDocument(personEntity.getDocument());
        person.setName(personEntity.getName());
        person.setPhone(personEntity.getPhone());
        person.setAge(personEntity.getAge());
        return person;
    }

    private PersonEntity personAdapter(Person person) {
        if (person == null) {
            return null; 
        }

        PersonEntity personEntity = new PersonEntity();
        personEntity.setDocument(person.getDocument());
        personEntity.setName(person.getName());
        personEntity.setPhone(person.getPhone());
        personEntity.setAge(person.getAge());
        return personEntity;
    }
}