import java.util.*;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class RestaurantSystem {

    private ArrayList<MenuItem> menu;
    private ArrayList<Employee> employees;
    private ArrayList<Order> orders;

    private FileManager fileManager;
    private BillingSystem billingSystem;

    public RestaurantSystem() {
        menu = new ArrayList<MenuItem>();
        employees = new ArrayList<Employee>();
        orders = new ArrayList<Order>();

        fileManager = new FileManager();
        billingSystem = new BillingSystem();

        fileManager.loadMenu(menu);
        fileManager.loadEmployees(employees);
        fileManager.loadOrders(orders);
    }

    // ================= MAIN START =================
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== RESTAURANT SYSTEM =====");
            System.out.println("1. Menu Management");
            System.out.println("2. Employee Management");
            System.out.println("3. Take Order");
            System.out.println("4. Order Management");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            int c = readInt(sc);

            if (c == 1) manageMenu(sc);
            else if (c == 2) manageEmployees(sc);
            else if (c == 3) takeOrder(sc);
            else if (c == 4) manageOrders(sc);
            else if (c == 5) {
                System.out.println("Program terminated.");
                break;
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
        sc.close(); // ✅ only here
    }

    // ================= MENU MANAGEMENT =================
    private void manageMenu(Scanner sc) {
        while (true) {
            System.out.println("\n--- MENU MANAGEMENT ---");
            System.out.println("1. View Menu");
            System.out.println("2. Add Item");
            System.out.println("3. Delete Item");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            int c = readInt(sc);

            if (c == 1) {
                if (menu.size() == 0)
                    System.out.println("Menu is empty!");
                else
                    for (int i = 0; i < menu.size(); i++)
                        System.out.println(menu.get(i));
            }
            else if (c == 2) {
                int id = getNextMenuItemId();
                System.out.println("Generated Item ID: " + id);
                sc.nextLine();

                System.out.print("Name: ");
                String n = sc.nextLine();
                System.out.print("Category: ");
                String cat = sc.nextLine();
                System.out.print("Price: ");
                double p = sc.nextDouble();

                menu.add(new MenuItem(id, n, cat, p));
                fileManager.saveMenu(menu);
                System.out.println("Item added successfully!");
            }
            else if (c == 3) {
                System.out.print("Enter Item ID: ");
                int id = readInt(sc);

                boolean removed = false;
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.get(i).getId() == id) {
                        menu.remove(i);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    fileManager.saveMenu(menu);
                    System.out.println("Item deleted successfully!");
                } else {
                    System.out.println("Item not found!");
                }
            }
            else if (c == 4) {
                break; // ✅ BACK WORKS
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private int getNextMenuItemId() {
        int max = 0;
        for (int i = 0; i < menu.size(); i++)
            if (menu.get(i).getId() > max)
                max = menu.get(i).getId();
        return max + 1;
    }

    // ================= EMPLOYEE MANAGEMENT =================
    private void manageEmployees(Scanner sc) {
        while (true) {
            System.out.println("\n--- EMPLOYEE MANAGEMENT ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. View Salaries");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            int c = readInt(sc);

            if (c == 1) addEmployee(sc);
            else if (c == 2) {
                if (employees.size() == 0)
                    System.out.println("No employees found!");
                else
                    for (int i = 0; i < employees.size(); i++)
                        System.out.println(employees.get(i));
            }
            else if (c == 3) {
                for (int i = 0; i < employees.size(); i++)
                    System.out.println(
                        employees.get(i).getName() +
                        " Salary: " +
                        employees.get(i).calculateSalary()
                    );
            }
            else if (c == 4) {
                break;
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void addEmployee(Scanner sc) {
        System.out.println("\n1. Waiter\n2. Chef\n3. Manager");
        System.out.print("Type: ");
        int t = readInt(sc);

        int id = getNextEmployeeId();
        System.out.println("Generated Employee ID: " + id);
        sc.nextLine();

        System.out.print("Name: ");
        String n = sc.nextLine();
        System.out.print("Base Salary: ");
        double s = sc.nextDouble();

        Employee e = null;
        if (t == 1) e = new Waiter(id, n, s);
        else if (t == 2) e = new Chef(id, n, s);
        else if (t == 3) e = new Manager(id, n, s);

        if (e != null) {
            employees.add(e);
            fileManager.saveEmployees(employees);
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Invalid employee type!");
        }
    }

    private int getNextEmployeeId() {
        int max = 0;
        for (int i = 0; i < employees.size(); i++)
            if (employees.get(i).getId() > max)
                max = employees.get(i).getId();
        return max + 1;
    }

    // ================= ORDER =================
    private void takeOrder(Scanner sc) {
        if (menu.size() == 0) {
            System.out.println("Menu is empty!");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Order order = new Order(getNextOrderId(), sdf.format(new Date()));

        while (true) {
            for (int i = 0; i < menu.size(); i++)
                System.out.println(menu.get(i));

            System.out.print("Enter item ID (0 to finish): ");
            int id = readInt(sc);

            if (id == 0) break;

            boolean found = false;
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getId() == id) {
                    order.addItem(menu.get(i));
                    found = true;
                    break;
                }
            }

            if (!found)
                System.out.println("Invalid item ID!");
        }

        if (order.getItems().size() > 0) {
            billingSystem.printReceipt(order);
            fileManager.saveOrder(order);
            orders.add(order);
        }
    }

    private int getNextOrderId() {
        int max = 0;
        for (int i = 0; i < orders.size(); i++)
            if (orders.get(i).getOrderId() > max)
                max = orders.get(i).getOrderId();
        return max + 1;
    }

    // ================= ORDER MANAGEMENT =================
    private void manageOrders(Scanner sc) {
        while (true) {
            System.out.println("\n--- ORDER MANAGEMENT ---");
            System.out.println("1. View Orders");
            System.out.println("2. Complete Order");
            System.out.println("3. Back");
            System.out.print("Choice: ");

            int c = readInt(sc);

            if (c == 1) viewOrders();
            else if (c == 2) completeOrder(sc);
            else if (c == 3) break;
            else System.out.println("Invalid choice!");
        }
    }

    private void viewOrders() {
        if (orders.size() == 0) {
            System.out.println("No orders found!");
            return;
        }

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.println(
                "Order ID: " + o.getOrderId() +
                " | Date: " + o.getDateTime() +
                " | Status: " + o.getStatus()
            );
        }
    }

    private void completeOrder(Scanner sc) {
        System.out.print("Enter Order ID: ");
        int id = readInt(sc);

        boolean found = false;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == id) {
                orders.get(i).setStatus("COMPLETED");
                found = true;
                break;
            }
        }

        if (found) {
            try {
                PrintWriter pw = new PrintWriter("orders.txt");
                for (int i = 0; i < orders.size(); i++)
                    pw.println(orders.get(i).toFileString());
                pw.close();
                System.out.println("Order marked COMPLETED!");
            } catch (Exception e) {
                System.out.println("Error updating orders!");
            }
        } else {
            System.out.println("Order not found!");
        }
    }

    // ================= SAFE INPUT =================
    private int readInt(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input! Enter a number: ");
                sc.nextLine();
            }
        }
    }
}