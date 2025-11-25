package bloomcart;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private static int idCounter = 1000;  // Auto-incrementing ID generator

    private int orderID;
    private Customer customer;
    private Bouquet bouquet;
    private Date deliveryDate;

    // Constructor
    public Order(Customer customer, Bouquet bouquet, Date deliveryDate) {
        this.orderID = ++idCounter;
        this.customer = customer;
        this.bouquet = bouquet;
        this.deliveryDate = deliveryDate;
    }

    // Getters
    public int getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Bouquet getBouquet() {
        return bouquet;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    // Calculate total amount
    public double getTotalAmount() {
        return bouquet.calculateTotalCost();
    }

    // Generate Order Summary
    public String generateSummary() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("\n===== ORDER SUMMARY =====\n");
        sb.append("Order ID: ").append(orderID).append("\n");
        sb.append("Customer Name: ").append(customer.getName()).append("\n");
        sb.append("Address: ").append(customer.getAddress()).append("\n");
        sb.append("Contact: ").append(customer.getContactNumber()).append("\n");
        sb.append("----------------------------\n");
        sb.append("Bouquet Details:\n");
        sb.append("Wrapping: ").append(bouquet.getWrappingType()).append("\n");
        sb.append("Greeting Message: ").append(bouquet.getGreetingMessage()).append("\n");
        sb.append("Flowers: ").append(bouquet.getFlowers()).append("\n");
        sb.append("Total Price: ₹").append(getTotalAmount()).append("\n");
        sb.append("Delivery Date: ").append(sdf.format(deliveryDate)).append("\n");
        sb.append("============================\n");

        return sb.toString();
    }

    // toString
    @Override
    public String toString() {
        return generateSummary();
    }
}

