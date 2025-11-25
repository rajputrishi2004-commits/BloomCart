package bloomcart;

import java.util.ArrayList;

public class Bouquet {
    private ArrayList<Flower> flowers;
    private String wrappingType;
    private String greetingMessage;

    public Bouquet() {
        flowers = new ArrayList<>();
        wrappingType = "";
        greetingMessage = "";
    }

    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public ArrayList<Flower> getFlowers() {
        return flowers;
    }

    public String getWrappingType() {
        return wrappingType;
    }

    public void setWrappingType(String wrappingType) {
        this.wrappingType = wrappingType;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Flower f : flowers) {
            total += f.getPrice();
        }
        // optional: add wrapping cost if needed
        return total;
    }

    // ← Add this method
    public boolean isEmpty() {
        return flowers.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Flower f : flowers) {
            sb.append(f.getName()).append(" (").append(f.getColor()).append(") - ₹").append(f.getPrice()).append("\n");
        }
        sb.append("Wrapping: ").append(wrappingType).append("\n");
        sb.append("Message: ").append(greetingMessage).append("\n");
        sb.append("Total Cost: ₹").append(calculateTotalCost());
        return sb.toString();
    }
}

