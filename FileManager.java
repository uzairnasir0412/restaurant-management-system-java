import java.util.*;
import java.io.*;

public class FileManager {

    // MENU
    public void loadMenu(ArrayList<MenuItem> menu) {
        try {
            File file = new File("menu.txt");
            if (!file.exists()) return;

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                menu.add(new MenuItem(
                        Integer.parseInt(p[0]),
                        p[1], p[2],
                        Double.parseDouble(p[3])));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Menu load error");
        }
    }

    public void saveMenu(ArrayList<MenuItem> menu) {
        try {
            PrintWriter pw = new PrintWriter("menu.txt");
            for (int i = 0; i < menu.size(); i++)
                pw.println(menu.get(i).toFileString());
            pw.close();
        } catch (Exception e) {
            System.out.println("Menu save error");
        }
    }

    // EMPLOYEES
    public void loadEmployees(ArrayList<Employee> employees) {
        try {
            File file = new File("employees.txt");
            if (!file.exists()) return;

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                Employee e = null;

                if (p[0].equals("Waiter"))
                    e = new Waiter(Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]));
                else if (p[0].equals("Chef"))
                    e = new Chef(Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]));
                else if (p[0].equals("Manager"))
                    e = new Manager(Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]));

                if (e != null) employees.add(e);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Employee load error");
        }
    }

    public void saveEmployees(ArrayList<Employee> employees) {
        try {
            PrintWriter pw = new PrintWriter("employees.txt");
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                String type =
                        (e instanceof Waiter) ? "Waiter" :
                        (e instanceof Chef) ? "Chef" : "Manager";

                pw.println(type + "," + e.getId() + "," +
                        e.getName() + "," + e.getBaseSalary());
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Employee save error");
        }
    }

    // ORDERS
    public void saveOrder(Order order) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("orders.txt", true));
            pw.println(order.toFileString());
            pw.close();
        } catch (Exception e) {
            System.out.println("Order save error");
        }
    }

    // LOAD ORDERS
public void loadOrders(ArrayList<Order> orders) {
    try {
        File file = new File("orders.txt");
        if (!file.exists()) return;

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String[] p = sc.nextLine().split(";");

            int id = Integer.parseInt(p[0]);
            String date = p[3];
            String status = p[4];

            Order o = new Order(id, date);
            o.setStatus(status);

            orders.add(o);
        }
        sc.close();
    } catch (Exception e) {
        System.out.println("Order load error");
    }
}

}


