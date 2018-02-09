import java.util.*;
public class Transaction {

    public int type;
    public int flight_id;
    public int passenger_id;
    public Flight[] flights;
    public Passenger[] passenger;
    public Transaction(Flight[] flights,Passenger[] passenger,int type,int flight_id, int passenger_id,int flight_id_2) {
        this.type = type;
        this.flight_id = flight_id;
        this.passenger_id = passenger_id;
        this.flights=flights;
        this.passenger=passenger;
        switch (type)
        {
            case 1: reserve(flight_id,passenger_id);
            break;
            case 2: cancel(flight_id,passenger_id);
                break;
            case 3: my_flights(passenger_id);
                break;
            case 4: total_reservations();
                break;
            case 5: transfer(flight_id,flight_id_2,passenger_id);
                break;
        }
    }
    public void reserve(int flight_id,int passenger_id){
    	for (int i=0;i<flights[flight_id-1].num;i++){
    		if (flights[flight_id-1].seats[i].passenger_id==0){
    			flights[flight_id-1].seats[i].passenger_id=passenger_id;
    			passenger[passenger_id-1].booked.add(flights[flight_id-1].seats[i]);
    			break;
    		}
    	}
    }
    public void cancel(int flight_id,int passenger_id){
    	for (int i=0;i<flights[flight_id-1].num;i++){
    		if (flights[flight_id-1].seats[i].passenger_id==passenger_id){
    			flights[flight_id-1].seats[i].passenger_id=0;
    			passenger[passenger_id-1].booked.remove(flights[flight_id-1].seats[i]);
    		}
    	}
    }
    public void my_flights(int passenger_id){
    	ArrayList<Seat> p=passenger[passenger_id-1].booked;
    	for(int i=0;i<p.size();i++){
    		System.out.println(p.get(i).flight_id+" "+p.get(i).seat_number);
    	}
    }
    public int total_reservations(){
    	int ct=0;
    	for (int i=0;i<5;i++){
    		for (int j=0;j<flights[i].num;i++){
    			if (flights[i].seats[j].passenger_id!=0)ct++;
    		}
    	}
    	return ct;
    }
    public void transfer(int flight_id_1,int flight_id_2,int passenger_id){
    	
    }
}