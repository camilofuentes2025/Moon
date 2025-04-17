package Moon.domain.models;

public class Motel {
	
	private long motelID;
    private String motelName;
    private String location;
    private long motelPhone;
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
