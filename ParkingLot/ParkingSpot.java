
public class ParkingSpot {
	private VehicleSize size;
	private Vehicle currentVehicle;
	
	public ParkingSpot(VehicleSize size) {
		this.size = size;
	}
	
	public ParkingSpot park(Vehicle v) {
		if (!canPark(v)) {
			return null;
		}

		currentVehicle = v;		
		return this;
	}
	
	public boolean unpark() {
		if (currentVehicle == null) {
			return false;
		}
		
		currentVehicle = null;
		return true;
	}
	
	public boolean canPark(Vehicle v) {
		assert v != null;
		
		return currentVehicle == null && canFit(v);
	}
	
	private boolean canFit(Vehicle v) {
		if (size == VehicleSize.LARGE) {
			return true;
		}
		
		return v.getSize() == VehicleSize.COMPACT;
	}
}
