package Moon.adapters.bookings.entity;

import java.sql.Date;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.rooms.entity.RoomEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Column(name = "bookingID")
    private long bookingID; 
    
    @Column(name = "startTime") 
    private Date startTime;
    
    @Column(name = "endTime") 
    private Date endTime;

    @Column(name = "status")
    private boolean status;

    @Column(name = "payment")
    private boolean payment;

    @OneToOne
    @JoinColumn(name = "room")
    private RoomEntity room;
    
    @OneToOne
    @JoinColumn(name = "user")
    private PersonEntity user;

	public long getBookingID() {
		return bookingID;
	}

	public void setBookingID(long bookingID) {
		this.bookingID = bookingID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
