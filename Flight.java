import java.util.Random;

public class Flight {

    public int flight_id;
    public int num;
    public Seat[] seats;
    public int status;

    public Flight(int flight_id){
        status=0;
        Random random= new Random();
        this.flight_id=flight_id;
        num=random.nextInt(100);
        num++;
        seats=new Seat[num];
        for (int i=0;i<num;i++){
            seats[i]=new Seat(this.flight_id,i,0);
        }
    }
}