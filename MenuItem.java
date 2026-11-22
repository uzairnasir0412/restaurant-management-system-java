public class MenuItem {
    private int id;
    private String name;
    private String category;
    private double price;

    public MenuItem(int id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }

    public String toString() {
        return id + " - " + name + " (" + category + ") : " + price;
    }

    public String toFileString() {
        return id + "," + name + "," + category + "," + price;
    }
}
