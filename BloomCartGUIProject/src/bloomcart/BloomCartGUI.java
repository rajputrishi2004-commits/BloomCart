package bloomcart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Custom Exceptions
class EmptyBouquetException extends Exception {
    public EmptyBouquetException(String message) {
        super(message);
    }
}

class IncompleteCustomerException extends Exception {
    public IncompleteCustomerException(String message) {
        super(message);
    }
}

public class BloomCartGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel totalPriceLabel; // Live total price display

    private Bouquet bouquet;
    private Customer customer;
    private ArrayList<Flower> availableFlowers;

    public BloomCartGUI() {
        setTitle("BloomCart – Bouquet Customization App");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bouquet = new Bouquet();
        availableFlowers = loadFlowers();

        // Total Price Label
        totalPriceLabel = new JLabel("Total Price: ₹0.0");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add screens
        mainPanel.add(menuPanel(), "Menu");
        mainPanel.add(flowersPanel(), "Flowers");
        mainPanel.add(customizePanel(), "Customize");
        mainPanel.add(customerPanel(), "Customer");
        mainPanel.add(summaryPanel(), "Summary");

        // Layout: total price on top
        setLayout(new BorderLayout());
        add(totalPriceLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ---------------- Main Menu Panel ----------------
    private JPanel menuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton btnFlowers = new JButton("View Available Flowers");
        JButton btnCustomize = new JButton("Customize Bouquet");
        JButton btnCustomer = new JButton("Enter Customer Details");
        JButton btnSummary = new JButton("View Order Summary");
        JButton btnExit = new JButton("Exit");

        btnFlowers.addActionListener(e -> cardLayout.show(mainPanel, "Flowers"));
        btnCustomize.addActionListener(e -> cardLayout.show(mainPanel, "Customize"));
        btnCustomer.addActionListener(e -> cardLayout.show(mainPanel, "Customer"));
        btnSummary.addActionListener(e -> cardLayout.show(mainPanel, "Summary"));
        btnExit.addActionListener(e -> System.exit(0));

        panel.add(btnFlowers);
        panel.add(btnCustomize);
        panel.add(btnCustomer);
        panel.add(btnSummary);
        panel.add(btnExit);

        return panel;
    }

    // ---------------- Flowers Panel ----------------
    private JPanel flowersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Flower f : availableFlowers) {
            model.addElement(f.getName() + " - " + f.getColor() + " - ₹" + f.getPrice());
        }

        JList<String> flowerList = new JList<>(model);
        JScrollPane scroll = new JScrollPane(flowerList);

        JButton addButton = new JButton("Add Selected Flower to Bouquet");
        addButton.addActionListener(e -> {
            int index = flowerList.getSelectedIndex();
            if (index != -1) {
                bouquet.addFlower(availableFlowers.get(index));
                JOptionPane.showMessageDialog(null, "Flower added to bouquet!");
                totalPriceLabel.setText("Total Price: ₹" + bouquet.calculateTotalCost());
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        JPanel bottom = new JPanel();
        bottom.add(addButton);
        bottom.add(backBtn);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------- Customize Bouquet Panel ----------------
    private JPanel customizePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        String[] wrappingOptions = {"Classic Wrap", "Premium Wrap", "Luxury Gift Box"};
        JComboBox<String> wrapBox = new JComboBox<>(wrappingOptions);

        JTextArea messageArea = new JTextArea(3, 20);

        JButton saveBtn = new JButton("Save Customization");
        saveBtn.addActionListener(e -> {
            bouquet.setWrappingType((String) wrapBox.getSelectedItem());
            bouquet.setGreetingMessage(messageArea.getText());
            JOptionPane.showMessageDialog(null, "Customization saved!");
            totalPriceLabel.setText("Total Price: ₹" + bouquet.calculateTotalCost());
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        panel.add(new JLabel("Choose Wrapping:"));
        panel.add(wrapBox);

        panel.add(new JLabel("Greeting Message:"));
        panel.add(new JScrollPane(messageArea));
        panel.add(saveBtn);
        panel.add(backBtn);

        return panel;
    }

    // ---------------- Customer Details Panel ----------------
    private JPanel customerPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField contactField = new JTextField();

        JButton saveBtn = new JButton("Save Customer Details");
        saveBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String contact = contactField.getText().trim();

                if (name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                    throw new IncompleteCustomerException("Please fill in all customer details!");
                }

                customer = new Customer(name, address, contact);
                JOptionPane.showMessageDialog(null, "Customer details saved successfully!");

            } catch (IncompleteCustomerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        panel.add(new JLabel("Name: "));
        panel.add(nameField);

        panel.add(new JLabel("Address: "));
        panel.add(addressField);

        panel.add(new JLabel("Contact Number: "));
        panel.add(contactField);

        panel.add(saveBtn);
        panel.add(backBtn);

        return panel;
    }

    // ---------------- Order Summary Panel ----------------
    private JPanel summaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);

        JButton generateBtn = new JButton("Generate Summary");
        generateBtn.addActionListener(e -> {
            try {
                if (bouquet.isEmpty()) {
                    throw new EmptyBouquetException("Bouquet is empty! Please add flowers first.");
                }
                if (customer == null) {
                    throw new IncompleteCustomerException("Customer details missing! Please fill customer info.");
                }

                summaryArea.setText(
                        "===== ORDER SUMMARY =====\n" +
                        "Customer Name: " + customer.getName() + "\n" +
                        "Address: " + customer.getAddress() + "\n" +
                        "Contact: " + customer.getContactNumber() + "\n\n" +
                        "Bouquet Details:\n" +
                        "Flowers: " + bouquet.getFlowers() + "\n" +
                        "Wrapping: " + bouquet.getWrappingType() + "\n" +
                        "Greeting Message: " + bouquet.getGreetingMessage() + "\n" +
                        "Total Price: ₹" + bouquet.calculateTotalCost() + "\n" +
                        "=========================="
                );

            } catch (EmptyBouquetException | IncompleteCustomerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        JPanel bottom = new JPanel();
        bottom.add(generateBtn);
        bottom.add(backBtn);

        panel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // -------- Load Available Flowers --------
    private ArrayList<Flower> loadFlowers() {
        ArrayList<Flower> list = new ArrayList<>();
        list.add(new Flower("Rose", "Red", 20));
        list.add(new Flower("Lily", "White", 30));
        list.add(new Flower("Tulip", "Pink", 25));
        list.add(new Flower("Orchid", "Purple", 40));
        return list;
    }

    // -------- Main Method --------
    public static void main(String[] args) {
        new BloomCartGUI();
    }
}
