package Moon.domain.models;

import java.sql.Date;

public class Room {
	
    private long roomID;
    private String type;
    private long price;
    private String characteristics;
    private boolean availability;
    private Motel motel;
 
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
	public Motel getMotel() {
		return motel;
	}
	public void setMotel(Motel motel) {
		this.motel = motel;
	}

    
    

}
