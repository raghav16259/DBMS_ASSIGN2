import java.util.*;
public class Passenger {
    public int id;
    public ArrayList<Seat> booked;
    public Passenger(int id){
    	this.id=id;
    	booked=new ArrayList<Seat>();
    }
}
class Seat{
	public int flight_id;
	public int seat_number;
	public Seat(int flight_id,int seat_number){
		this.flight_id=flight_id;
		this.seat_number=seat_number;
	}
}