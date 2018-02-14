import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Concurrency_Control_Manager2 implements Runnable {
    static volatile Flight[] flight = new Flight[5];
    static Passenger[] passenger = new Passenger[5];
    public int id;

    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i = 0; i < 5; i++) {
            flight[i] = new Flight(i + 1);
        }

        for (int i = 0; i < 5; i++) {
            passenger[i] = new Passenger(i + 1);
        }
        ExecutorService exec = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            Concurrency_Control_Manager2 thread = new Concurrency_Control_Manager2();
            thread.id = i;
            exec.execute(thread);
        }
        if(!exec.isTerminated())
        {
            exec.shutdown();
            exec.awaitTermination(5L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void run()
    {
        int counter=0;
        while(counter<5)
        {
            counter++;
            Random random=new Random();
            int type=random.nextInt(5)+1;
            int flight_id=random.nextInt(5)+1;
            int passenger_id=random.nextInt(5)+1;
            int flight_id_2=0;
            if(type==5)
            {
                flight_id_2=random.nextInt(5)+1;
                while (flight_id_2==flight_id)
                    flight_id_2=random.nextInt(5)+1;
            }

            System.out.println("ID: "+id+" Counter: "+counter+" started");

            if(type==1||type==2)
            {
                Object obj1=flight[flight_id-1];
                Object obj2=passenger[passenger_id-1];
                int temp=0;
                while(flight[flight_id-1].status!=0)
                {
                    if(temp==0)
                    {
                        System.out.println("Waiting for exclusive lock on Flight: "+flight_id+" ID: "+id+" Counter: "+counter);
                    }
                    temp++;
                }
                temp=0;
                synchronized (obj1)
                {

                    System.out.println("Acquired exclusive lock on Flight: "+flight_id+" ID: "+id+" Counter: "+counter);
                    flight[flight_id-1].status=-1;
                    while(passenger[passenger_id-1].status!=0)
                    {
                        if(temp==0)
                        {
                            System.out.println("Waiting for exclusive lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                        }
                        temp++;
                    }
                    temp=0;
                    synchronized (obj2)
                    {
                        System.out.println("Acquired exclusive lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                        passenger[passenger_id-1].status=-1;
                        System.out.println("Type: "+type+" Flight_ID: "+flight_id+" Passenger_ID: "+passenger_id+" ID: "+id+" Counter: "+counter);
                        try {
                            Transaction transaction=new Transaction(flight,passenger,type,flight_id,passenger_id,flight_id_2,id,counter);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Released exclusive lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                        passenger[passenger_id-1].status=0;
                    }
                    System.out.println("Released exclusive lock on Flight: "+flight_id+" ID: "+id+" Counter: "+counter);
                    flight[flight_id-1].status=0;
                    System.out.println("ID: "+id+" Counter: "+counter+" done");
                }
            }
            else if(type==3)
            {
                Object obj=passenger[passenger_id-1];
                int temp=0;
                while(passenger[passenger_id-1].status==-1)
                {
                    if(temp==0)
                    {
                        System.out.println("Waiting for shared lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                    }
                    temp++;
                }
                temp=0;
                synchronized (obj)
                {
                    System.out.println("Acquired shared lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                    passenger[passenger_id-1].status++;
                    System.out.println("Type: "+type+" Passenger_ID: "+passenger_id+" ID: "+id+" Counter: "+counter);
                    try {
                        Transaction transaction=new Transaction(flight,passenger,type,flight_id,passenger_id,flight_id_2,id,counter);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Released shared lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                    passenger[passenger_id-1].status--;
                    System.out.println("ID: "+id+" Counter: "+counter+" done");
                }
            }
            else if(type==4)
            {
                Object obj1=flight[0];
                Object obj2=flight[1];
                Object obj3=flight[2];
                Object obj4=flight[3];
                Object obj5=flight[4];
                int temp=0;
                while(flight[0].status!=0)
                {
                    if(temp==0)
                    {
                        System.out.println("Waiting for shared lock on Flight 1 ID: "+id+" Counter: "+counter);
                    }
                    temp++;
                }
                temp=0;
                synchronized (obj1)
                {
                    System.out.println("Acquired shared lock on Flight 1 ID: "+id+" Counter: "+counter);
                    flight[0].status++;
                    while(flight[1].status!=0)
                    {
                        if(temp==0)
                        {
                            System.out.println("Waiting for shared lock on Flight 2 ID: "+id+" Counter: "+counter);
                        }
                        temp++;
                    }
                    temp=0;
                    synchronized (obj2)
                    {
                        System.out.println("Acquired shared lock on Flight 2 ID: "+id+" Counter: "+counter);
                        flight[1].status++;
                        while(flight[2].status!=0)
                        {
                            if(temp==0)
                            {
                                System.out.println("Waiting for shared lock on Flight 3 ID: "+id+" Counter: "+counter);
                            }
                            temp++;
                        }
                        temp=0;
                        synchronized (obj3)
                        {
                            System.out.println("Acquired shared lock on Flight 3 ID: "+id+" Counter: "+counter);
                            flight[2].status++;
                            while(flight[3].status!=0)
                            {
                                if(temp==0)
                                {
                                    System.out.println("Waiting for shared lock on Flight 4 ID: "+id+" Counter: "+counter);
                                }
                                temp++;
                            }
                            temp=0;
                            synchronized (obj4)
                            {
                                System.out.println("Acquired shared lock on Flight 4 ID: "+id+" Counter: "+counter);
                                flight[3].status++;
                                while(flight[4].status!=0)
                                {
                                    if(temp==0)
                                    {
                                        System.out.println("Waiting for shared lock on Flight 5 ID: "+id+" Counter: "+counter);
                                    }
                                    temp++;
                                }
                                temp=0;
                                synchronized (obj5)
                                {
                                    System.out.println("Acquired shared lock on Flight 5 ID: "+id+" Counter: "+counter);
                                    flight[4].status++;
                                    System.out.println("Type: "+type+" ID: "+id+" Counter: "+counter);
                                    try {
                                        Transaction transaction=new Transaction(flight,passenger,type,flight_id,passenger_id,flight_id_2,id,counter);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Released shared lock on flight 5 ID: "+id+" Counter: "+counter);
                                    flight[4].status--;

                                }
                                System.out.println("Released shared lock on flight 4 ID: "+id+" Counter: "+counter);
                                flight[3].status--;
                            }
                            System.out.println("Released shared lock on flight 3 ID: "+id+" Counter: "+counter);
                            flight[2].status--;
                        }
                        System.out.println("Released shared lock on flight 2 ID: "+id+" Counter: "+counter);
                        flight[1].status--;
                    }
                    System.out.println("Released shared lock on flight 1 ID: "+id+" Counter: "+counter);
                    flight[0].status--;
                    System.out.println("ID: "+id+" Counter: "+counter+" done");
                }
            }
            else
            {
                Object obj1=null;
                Object obj2=null;

                if(flight_id<flight_id_2)
                {
                    obj1=flight[flight_id-1];
                    obj2=flight[flight_id_2-1];
                }
                else
                {
                    obj2=flight[flight_id-1];
                    obj1=flight[flight_id_2-1];
                }
                Object obj3=passenger[passenger_id-1];
                int temp=0;
                while(flight[Math.min(flight_id-1,flight_id_2-1)].status!=0)
                {
                    if(temp==0)
                    {
                        System.out.println("Waiting for exclusive lock on Flight: "+Math.min(flight_id,flight_id_2)+" ID: "+id+" Counter: "+counter);
                    }
                    temp++;
                }
                synchronized (obj1)
                {
                    System.out.println("Acquired exclusive lock on Flight: "+Math.min(flight_id,flight_id_2)+" ID: "+id+" Counter: "+counter);
                    flight[Math.min(flight_id-1,flight_id_2-1)].status=-1;
                    while(flight[Math.max(flight_id-1,flight_id_2-1)].status!=0)
                    {
                        if(temp==0)
                        {
                            System.out.println("Waiting for exclusive lock on Flight: "+Math.max(flight_id,flight_id_2)+" ID: "+id+" Counter: "+counter);
                        }
                        temp++;
                    }
                    synchronized (obj2)
                    {
                        System.out.println("Acquired exclusive lock on Flight: "+Math.max(flight_id,flight_id_2)+" ID: "+id+" Counter: "+counter);
                        flight[Math.max(flight_id-1,flight_id_2-1)].status=-1;
                        while(passenger[passenger_id-1].status==-1)
                        {
                            if(temp==0)
                            {
                                System.out.println("Waiting for exclusive lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                            }
                            temp++;
                        }
                        temp=0;
                        synchronized (obj3)
                        {
                            System.out.println("Acquired exclusive lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                            passenger[passenger_id-1].status=-1;
                            System.out.println("Type: "+type+" Flight_ID: "+flight_id+" Passenger_ID: "+passenger_id+" Flight_ID_2: "+flight_id_2);
                            try {
                                Transaction transaction=new Transaction(flight,passenger,type,flight_id,passenger_id,flight_id_2,id,counter);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Released exclusive lock on Passenger: "+passenger_id+" ID: "+id+" Counter: "+counter);
                            passenger[passenger_id-1].status=0;

                        }
                        System.out.println("Released exclusive lock on Flight: "+Math.max(flight_id,flight_id_2)+" ID: "+id+" Counter: "+counter);
                        flight[Math.max(flight_id-1,flight_id_2-1)].status=0;

                    }
                    System.out.println("Released exclusive lock on Flight: "+Math.min(flight_id,flight_id_2)+" ID: "+id+" Counter: "+counter);
                    flight[Math.min(flight_id-1,flight_id_2-1)].status=0;
                    System.out.println("ID: "+id+" Counter: "+counter+" done");
                }
            }
            /*synchronized()
            {

                System.out.println("ID: "+id+" Counter: "+counter);
                System.out.println("Acquired lock on: "+flight_id);
                System.out.println("Type: "+type+" Flight_ID: "+flight_id+" Passenger_ID: "+passenger_id+" Flight_ID_2: "+flight_id_2);
                try {
                    Transaction transaction=new Transaction(flight,passenger,type,flight_id,passenger_id,flight_id_2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Released lock on: "+flight_id);
                System.out.println("ID: "+id+" Counter: "+counter+" done");
            }*/
        }
    }
}
