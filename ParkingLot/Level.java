
public class Level {
	private final ParkingSpot[] spots;
	
	public Level(int numOfSpots, int numOfCompactSpots) {
		spots = new ParkingSpot[numOfSpots];
		for (int i = 0; i < numOfCompactSpots; i++) {
			spots[i] = new ParkingSpot(VehicleSize.COMPACT);
		}
		for (int i = numOfCompactSpots; i < spots.length; i++) {
			spots[i] = new ParkingSpot(VehicleSize.LARGE);
		}
	}
	
	public boolean canPark(Vehicle v, int spotIndex) {
		assert spotIndex >= 0 && spotIndex < spots.length;
		
		return spots[spotIndex].canPark(v);
	}
	
	public int canPark(Vehicle v) {
		for (int i = 0; i < spots.length; i++) {
			if (spots[i].canPark(v)) {
				return i;
			}
		}
		return -1;
	}
	
	public ParkingSpot park(Vehicle v, int spotIndex) {
		if (!canPark(v, spotIndex)) {
			return null;
		}
		
		return spots[spotIndex].park(v);
	}
	
	public ParkingSpot park(Vehicle v) {
		int spotIndex = canPark(v);
		
		if (spotIndex == -1) return null;
		
		return park(v, spotIndex);
	}
	
	public boolean unpark(int spotIndex) {
		return spots[spotIndex].unpark();
	}
	
	public int getNumOfAvailableSpots(Vehicle v) {
		int count = 0;
		for (ParkingSpot spot : spots) {
			if (spot.canPark(v)) {
				count++;
			}
		}
		return count;
	}
}
