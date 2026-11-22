import java.util.ArrayList;

public class Order {
    private int orderId;
    private ArrayList<MenuItem> items;
    private String dateTime;
    private String status;   // ACTIVE or COMPLETED

    public Order(int orderId, String dateTime) {
        this.orderId = orderId;
        this.dateTime = dateTime;
        this.status = "ACTIVE";
        items = new ArrayList<MenuItem>();
    }

    public int getOrderId() { return orderId; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public ArrayList<MenuItem> getItems() { return items; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double sum = 0;
        for (int i = 0; i < items.size(); i++) {
            sum += items.get(i).getPrice();
        }
        return sum;
    }

    public String toFileString() {
        String itemNames = "";
        for (int i = 0; i < items.size(); i++) {
            itemNames += items.get(i).getName();
            if (i < items.size() - 1)
                itemNames += "|";
        }

        return orderId + ";" + itemNames + ";" +
               calculateTotal() + ";" + dateTime + ";" + status;
    }
}
