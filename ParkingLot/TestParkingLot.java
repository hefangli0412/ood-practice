
public class TestParkingLot {

	public static void main(String[] args) {
		ParkingLot pl = new ParkingLot(3, 3, 2);
		Car c1 = new Car();
		Car c2 = new Car();
		Car c3 = new Car();
		Truck t1 = new Truck();
		
		System.out.println(pl.canPark(t1, 0) + " expected true");
		System.out.println(pl.canPark(c1, 0) + " expected true");
		ParkingLocation loc1 = pl.park(c1, 0);
		System.out.println("level: " + loc1.level + " spot:" + loc1.spot);
		System.out.println(pl.canPark(c2, 0) + " expected true");
		ParkingLocation loc2 = pl.park(c2, 0);
		System.out.println("level: " + loc2.level + " spot:" + loc2.spot);
		System.out.println(pl.canPark(c3, 0) + " expected true");
		ParkingLocation loc3 = pl.park(c3, 0);
		System.out.println("level: " + loc3.level + " spot:" + loc3.spot);
		System.out.println(pl.canPark(t1, 0) + " expected false");
		System.out.println(pl.canPark(t1, 1) + " expected true");
		System.out.println(pl.getNumOfAvailableSpots(t1) + " expected 2");
		System.out.println(pl.getNumOfAvailableSpots(c3) + " expected 6");
		ParkingLocation loc4 = pl.canPark(c3);
		System.out.println("level: " + loc4.level + " spot:" + loc4.spot + " expected (1,0)");
		pl.unpark(c3);
		pl.park(c3);
		System.out.println(pl.getNumOfAvailableSpots(t1) + " expected 3");
		System.out.println(pl.getNumOfAvailableSpots(c3) + " expected 6");
		ParkingLocation loc5 = pl.canPark(c3);
		System.out.println("level: " + loc5.level + " spot:" + loc5.spot + " expected (0,2)");
		boolean res1 = pl.unpark(c3);
		System.out.println("unpark c3 " + res1);
		System.out.println(pl.getNumOfAvailableSpots(c3) + " expected 7");
		pl.unpark(c2);
		System.out.println(pl.getNumOfAvailableSpots(c2) + " expected 8");
	}

}
