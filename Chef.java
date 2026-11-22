public class Chef extends Employee {
    public Chef(int id, String name, double baseSalary) {
        super(id, name, baseSalary);
    }

    public double calculateSalary() {
        return baseSalary + 5000;
    }

    public String toString() {
        return id + " - Chef - " + name + " Salary: " + calculateSalary();
    }
}

