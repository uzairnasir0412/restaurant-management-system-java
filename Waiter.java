public class Waiter extends Employee {
    public Waiter(int id, String name, double baseSalary) {
        super(id, name, baseSalary);
    }

    public double calculateSalary() {
        return baseSalary + 2000;
    }

    public String toString() {
        return id + " - Waiter - " + name + " Salary: " + calculateSalary();
    }
}
