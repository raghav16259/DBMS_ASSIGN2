public class Seat{

    public int flight_id;
    public int seat_number;
    public int passenger_id;
    public Seat(int flight_id,int seat_number,int passenger_id){
        this.flight_id=flight_id;
        this.seat_number=seat_number;
        this.passenger_id=passenger_id;
    }

    @Override
    public String toString() {
        return ("Seat Number: "+seat_number+" Paseenger_ID "+passenger_id);
    }
}