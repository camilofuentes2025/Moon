package Moon.adapters.motels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "motelID")
    private long motelID;
    
    @Column(name = "motelName")
    private String motelName;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "motelPhone")
    private long motelPhone;
    
    @Column(name = "availability")
    private boolean availability;

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

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	
 
}
