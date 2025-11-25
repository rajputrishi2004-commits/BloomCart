package bloomcart;
public class Flower {

    // Attributes
    private String name;
    private String color;
    private double price;

    // Constructor
    public Flower(String name, String color, double price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    // toString Method
    @Override
    public String toString() {
        return "Flower{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}


