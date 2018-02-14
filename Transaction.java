import java.util.*;
public class Transaction {

    public int type;
    public int flight_id;
    public int passenger_id;
    public Flight[] flights;
    public Passenger[] passenger;
    public Transaction(Flight[] flights,Passenger[] passenger,int type,int flight_id, int passenger_id,int flight_id_2) throws InterruptedException {
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
            case 4: System.out.println("count= "+total_reservations());
                break;
            case 5: transfer(flight_id,flight_id_2,passenger_id);
                break;
        }
    }
    public void reserve(int flight_id,int passenger_id) throws InterruptedException {
        int flag=0;
        Thread.sleep(250);
        for (int i=0;i<flights[flight_id-1].num;i++){
            Thread.sleep(5);
            if (flights[flight_id-1].seats[i].passenger_id==0){
                flights[flight_id-1].seats[i].passenger_id=passenger_id;
                passenger[passenger_id-1].booked.add(flights[flight_id-1].seats[i]);
                System.out.println("Passenger: "+passenger_id+" reserved Seat Number: "+i+" in Flight: "+flight_id);
                flag=1;
                break;
            }
        }
        if(flag==0)
            System.out.println("Reservation Unsuccessful");
    }
    public void cancel(int flight_id,int passenger_id) throws InterruptedException {
        int flag=0;
        Thread.sleep(250);
        for (int i=0;i<flights[flight_id-1].num;i++){
            Thread.sleep(5);
            if (flights[flight_id-1].seats[i].passenger_id==passenger_id){
                flights[flight_id-1].seats[i].passenger_id=0;
                passenger[passenger_id-1].booked.remove(flights[flight_id-1].seats[i]);
                System.out.println("Passenger: "+passenger_id+" cancelled Seat Number: "+i+" in Flight: "+flight_id);
                flag=1;
            }
        }
        if(flag==0)
            System.out.println("No reservation found for Passenger: "+passenger_id+" in Flight: "+flight_id);
    }
    public void my_flights(int passenger_id) throws InterruptedException {
        ArrayList<Seat> p=passenger[passenger_id-1].booked;
        if(p.size()==0)
            System.out.println("No flights");
        for(int i=0;i<p.size();i++)
        {
            Thread.sleep(5);
            System.out.println("Flight: "+p.get(i).flight_id+" Seat: "+p.get(i).seat_number);
        }
    }
    public int total_reservations() throws InterruptedException {
        int count=0;
        for (int i=0;i<5;i++){
            Thread.sleep(250);
            for (int j=0;j<flights[i].num;j++){
                Thread.sleep(5);
                if (flights[i].seats[j].passenger_id!=0)
                    count++;
            }
        }
        return count;
    }
    public void transfer(int flight_id_1,int flight_id_2,int passenger_id) throws InterruptedException {
        Flight f1=flights[flight_id_1-1];
        Flight f2=flights[flight_id_2-1];
        int flag=0;
        Thread.sleep(500);
        for (int i=0;i<f1.num;i++)
        {
            Thread.sleep(5);
            if (f1.seats[i].passenger_id == passenger_id)
            {
                for (int j=0;j<f2.num;j++){
                    Thread.sleep(5);
                    if (f2.seats[j].passenger_id==0)
                    {
                        flights[flight_id_1-1].seats[i].passenger_id=0;
                        passenger[passenger_id-1].booked.remove(flights[flight_id_1-1].seats[i]);
                        flights[flight_id_2-1].seats[j].passenger_id=passenger_id;
                        passenger[passenger_id-1].booked.add(flights[flight_id_2-1].seats[i]);
                        System.out.println("Passenger: "+passenger_id+"transferred from Flight: "+flight_id_1+" to Flight: "+flight_id_2);
                        flag=1;
                        break;
                    }
                }
            }
        }
        if(flag==0)
        System.out.println("Transaction Failed");

    }
}