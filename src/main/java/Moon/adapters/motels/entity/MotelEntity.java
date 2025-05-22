package Moon.adapters.motels.entity;

import java.util.List;

import Moon.adapters.rooms.entity.RoomEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "motel")

public class MotelEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motelID")
    private long motelID;
    
    @Column(name = "motelName")
    private String motelName;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "motelPhone")
    private long motelPhone;
    
    @OneToMany(mappedBy = "motel")
    private List<RoomEntity> rooms;

	public long getMotelID() {
		return motelID;
	}

	public void setMotelID(long motelID) {
		this.motelID = motelID;
	}

	public String getMotelName() {
		return motelName;
	}

	public void setMotelName(String motelName) {
		this.motelName = motelName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getMotelPhone() {
		return motelPhone;
	}

	public void setMotelPhone(long motelPhone) {
		this.motelPhone = motelPhone;
	}

	public List<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomEntity> rooms) {
		this.rooms = rooms;
	}


	
	
 
}
