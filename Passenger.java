import java.util.*;
public class Passenger {

    public int id;
    public ArrayList<Seat> booked;

    public Passenger(int id){
        this.id=id;
        booked=new ArrayList<Seat>();
    }
}