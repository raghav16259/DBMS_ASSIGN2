import java.util.*;
public class Passenger {

    public int id;
    public ArrayList<Seat> booked;
    public int status;

    public Passenger(int id){
        status=0;
        this.id=id;
        booked=new ArrayList<Seat>();
    }
}