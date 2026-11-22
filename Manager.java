public class Manager extends Employee {
    public Manager(int id, String name, double baseSalary) {
        super(id, name, baseSalary);
    }

    public double calculateSalary() {
        return baseSalary + 10000;
    }

    public String toString() {
        return id + " - Manager - " + name + " Salary: " + calculateSalary();
    }
}
