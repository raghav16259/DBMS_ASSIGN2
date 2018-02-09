public class Transaction {

    public int type;
    public int flight_id;
    public int passenger_id;

    public Transaction(int type,int flight_id, int passenger_id) {
        this.type = type;
        this.flight_id = flight_id;
        this.passenger_id = passenger_id;
        switch (type)
        {
            case 1: reserve(flight_id,passenger_id);
            break;
            case 2: reserve();
                break;
            case 3: reserve();
                break;
            case 4: reserve();
                break;
            case 5: reserve();
                break;
        }
    }
}
