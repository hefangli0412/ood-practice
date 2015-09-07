import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
	private final Level[] levels;
	private final Map<Vehicle, ParkingSpot> map;

	public ParkingLot(int numOfLevels, 
			int numOfSpotsForEachLevel, 
			int numOfCompactSpotsForEachLevel) {
		levels = new Level[numOfLevels];
		
		if (numOfCompactSpotsForEachLevel > numOfSpotsForEachLevel) {
			numOfCompactSpotsForEachLevel = numOfSpotsForEachLevel;
		}
		
		for (int i = 0; i < levels.length; i++) {
			levels[i] = new Level(numOfSpotsForEachLevel, numOfCompactSpotsForEachLevel); // 可以这样用fast for-loop吗？
		}
		
		map = new HashMap<Vehicle, ParkingSpot>();
	}
	
	// park a vehicle at a particular location
	public boolean canPark(Vehicle v, ParkingLocation loc) {
		assert v != null;
		assert loc != null;

		return levels[loc.level].canPark(v, loc.spot);
	}
	
	// given a vehicle, tell me if I can pork it at a particular level
	public ParkingLocation canPark(Vehicle v, int levelIndex) {
		assert levelIndex >= 0 && levelIndex < levels.length;
		
		int spotIndex = levels[levelIndex].canPark(v);
		if (spotIndex == -1) {
			return null;
		} else {
			return new ParkingLocation(levelIndex, spotIndex);
		}
	}
	
	// given a vehicle, tell me whether I can pork it or not
	public ParkingLocation canPark(Vehicle v) {
		for (int i = 0; i < levels.length; i++) {
			int j = levels[i].canPark(v);
			if (j != -1) {
				return new ParkingLocation(i, j);
			}
		}
		
		return null;
	}
	
	// park a vehicle at a particular location
	public boolean park(Vehicle v, ParkingLocation loc) {
		if (!canPark(v, loc)) return false;
		
		ParkingSpot spot = levels[loc.level].park(v, loc.spot);
		ParkingSpot oldSpot = map.put(v, spot);
		assert oldSpot == null;
//		if (oldSpot != null) {
//			oldSpot.unpark();
//		}
		return true;
	}
		
	// park a vehicle at a particular level (from smaller to bigger spot index)
	public ParkingLocation park(Vehicle v, int levelIndex) {
		ParkingLocation loc = canPark(v, levelIndex);
		
		if (loc == null) return null;
		
		park(v, loc);
		return loc;
	}
	
	// park a vehicle at any level and location (from smaller to bigger level index)
	public ParkingLocation park(Vehicle v) {
		ParkingLocation loc = canPark(v);
		
		if (loc == null) return null;
		
		park(v, loc);
		return loc;
	}
	
	public boolean unpark(Vehicle v) {
		if (!map.containsKey(v)) {
			return false;
		}
		
		ParkingSpot spot = map.remove(v);
		return spot.unpark();
	}
	
	// how many spots left for a particular level
	public int getNumOfAvailableSpots(Vehicle v, int levelIndex) {
		if (levelIndex >= levels.length || levelIndex < 0) {
			return -1;
		}
		
		return levels[levelIndex].getNumOfAvailableSpots(v);
	}
	
	// how many spots left for the whole parking Lot
	public int getNumOfAvailableSpots(Vehicle v) {
		int count = 0;
		for (Level level : levels) {
			count += level.getNumOfAvailableSpots(v);
		}
		return count;
	}
	
}
