package Moon.adapters.bookings.entity;

import java.sql.Date;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.rooms.entity.RoomEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking")

public class BookingEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingID")
    private long bookingID; 
    
    @Column(name = "checkIn") 
    private Date checkIn;
    
    @Column(name = "checkOut") 
    private Date checkOut;

    @Column(name = "status")
    private boolean status;

    @Column(name = "payment")
    private boolean payment;

    @OneToOne
    @JoinColumn(name = "room")
    private RoomEntity room;
    
    @ManyToOne
    @JoinColumn(name = "user")
    private PersonEntity user;

	public long getBookingID() {
		return bookingID;
	}

	public void setBookingID(long bookingID) {
		this.bookingID = bookingID;
	}

	

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public RoomEntity getRoom() {
		return room;
	}

	public void setRoom(RoomEntity room) {
		this.room = room;
	}

	public PersonEntity getUser() {
		return user;
	}

	public void setUser(PersonEntity user) {
		this.user = user;
	}
    
}
