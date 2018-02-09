import java.io.*;
public class Concurrency_Control_Manager
{
    public static void main(String[] args)throws IOException
    {
        Flight[] flight=new Flight[5];
        for (int i=0;i<5;i++){
            flight[i]=new Flight(i+1);
        }
        Passenger[] passenger=new Passenger[10];
        for (int i=0;i<10;i++){
            passenger[i]=new Passenger(i+1);
        }
    }
}