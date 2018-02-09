import java.util.Random;

public class Flight {
    public int f_id;
    public int num;
    public int[] status;
    public Flight(){
        Random random= new Random();
        num=random.nextInt(100);
        num++;
        status=new int[num];
    }
}