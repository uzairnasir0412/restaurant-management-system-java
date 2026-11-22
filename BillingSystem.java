import java.util.ArrayList;

public class BillingSystem {

    public double calculateTax(double amount) {
        return amount * 0.10; // 10% tax
    }

    public double calculateDiscount(double amount) {
        if(amount > 2000) {
            return 200;
        }
        return 0;
    }

    public double calculateFinalTotal(Order order) {
        double sub = order.calculateTotal();
        double tax = calculateTax(sub);
        double discount = calculateDiscount(sub);
        return sub + tax - discount;
    }

    public void printReceipt(Order order) {
        System.out.println("------ Restaurant Receipt ------");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Date: " + order.getDateTime());
        System.out.println();
        System.out.println("Items:");

        ArrayList<MenuItem> items = order.getItems();

        for(int i = 0; i < items.size(); i++) {
            MenuItem m = items.get(i);
            System.out.println(m.getName() + " - " + m.getPrice());
        }

        double subtotal = order.calculateTotal();
        double tax = calculateTax(subtotal);
        double discount = calculateDiscount(subtotal);
        double finalTotal = subtotal + tax - discount;

        System.out.println("-------------------------------");
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Tax: " + tax);
        System.out.println("Discount: " + discount);
        System.out.println("-------------------------------");
        System.out.println("Final Total: " + finalTotal);
        System.out.println("-------------------------------");
        System.out.println("Thank you for visiting!");
    }
}
