package bloomcart;

import java.util.Date;

public class BloomCartTest {
    public static void main(String[] args) {

        // 1. Create flowers
        Flower rose = new Flower("Rose", "Red", 20);
        Flower lily = new Flower("Lily", "White", 30);
        Flower tulip = new Flower("Tulip", "Pink", 25);

        // 2. Create bouquet and add flowers
        Bouquet bouquet = new Bouquet();
        bouquet.addFlower(rose);
        bouquet.addFlower(lily);
        bouquet.addFlower(tulip);

        bouquet.setWrappingType("Luxury Gift Box");
        bouquet.setGreetingMessage("Happy Birthday!");

        // 3. Print bouquet info
        System.out.println("Bouquet Details:");
        System.out.println(bouquet);
        System.out.println("Total Price: ₹" + bouquet.calculateTotalCost());

        // 4. Create customer
        Customer customer = new Customer("Alice", "123 Flower St", "9876543210");

        // 5. Create order
        Order order = new Order(customer, bouquet, new Date());

        // 6. Print order summary
        System.out.println("\nOrder Summary:");
        System.out.println(order.generateSummary());

        // 7. Test exception: empty bouquet
        try {
            Bouquet emptyBouquet = new Bouquet();
            if (emptyBouquet.isEmpty()) {
                throw new Exception("Bouquet is empty! Cannot place order.");
            }
        } catch (Exception e) {
            System.out.println("\nException Test: " + e.getMessage());
        }

        // 8. Test exception: incomplete customer
        try {
            Customer incomplete = new Customer("", "Some Address", "");
            if (incomplete.getName().isEmpty() || incomplete.getContactNumber().isEmpty()) {
                throw new Exception("Customer details incomplete!");
            }
        } catch (Exception e) {
            System.out.println("Exception Test: " + e.getMessage());
        }
    }
}

