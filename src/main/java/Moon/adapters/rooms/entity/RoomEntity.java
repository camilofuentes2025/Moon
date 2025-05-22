package Moon.adapters.rooms.entity;

import Moon.adapters.motels.entity.MotelEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomID")
    private long roomID;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private long price;

    @Column(name = "characteristics")
    private String characteristics;

    @Column(name = "availability")
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "motel_id")
    private MotelEntity motel;

	public long getRoomID() {
		return roomID;
	}

	public void setRoomID(long roomID) {
		this.roomID = roomID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}


	public MotelEntity getMotel() {
		return motel;
	}

	public void setMotel(MotelEntity motel) {
		this.motel = motel;
	}

    



}