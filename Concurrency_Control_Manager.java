import java.io.*;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Concurrency_Control_Manager implements Runnable
{
    static volatile Flight[] flight=new Flight[5];
    static Passenger[] passenger=new Passenger[10];
    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i=0;i<5;i++){
            flight[i]=new Flight(i+1);
        }

        for (int i=0;i<10;i++){
            passenger[i]=new Passenger(i+1);
        }
        ExecutorService exec = Executors.newFixedThreadPool(3);

        for(int i=0;i<10;i++)
        {
            Concurrency_Control_Manager thread=new Concurrency_Control_Manager();
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
        while(counter<10)
        {
            counter++;
            Random random=new Random();
            int type=random.nextInt(5)+1;
            int flight_id=random.nextInt(5)+1;
            int passenger_id=random.nextInt(10)+1;
            int flight_id_2=0;
            if(type==5)
            {
                flight_id_2=random.nextInt(5)+1;
                while (flight_id_2==flight_id)
                    flight_id_2=random.nextInt(5)+1;
            }
            synchronized(this)
            {
                Transaction transaction=new Transaction(flight,passenger,type,flight_id,passenger_id,flight_id_2);
            }
        }

    }
}