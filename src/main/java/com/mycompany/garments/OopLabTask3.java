package com.mycompany.garments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Garment {

    public String id;
    public String name;
    public String description;
    public String size;
    public String color;
    public double price;
    public double discountPercentage;
    public int stockQuantity;

    double calculateDiscountPrice() {
        double discount = price * (discountPercentage / 100);
        return price - discount;
    }
}

class Invoice {
    Date date;
    Garment garment;
    double discountedPrice;
    Customer customer;

    Invoice(Garment garment, double discountedPrice, Customer customer) {
        this.date = new Date();
        this.garment = garment;
        this.discountedPrice = discountedPrice;
        this.customer = customer;
    }

    void printInvoice() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Invoice Date: " + dateFormat.format(date));
        System.out.println("Purchased Garment: " + garment.name);
        System.out.println("Description: " + garment.description);
        System.out.println("Size: " + garment.size);
        System.out.println("Color: " + garment.color);
        System.out.println("Original Price: " + garment.price);
        System.out.println("Discounted Price: " + discountedPrice);
        System.out.println("Remaining Stock: " + garment.stockQuantity);
        
        if (customer != null && customer.hasDetails()) {
            System.out.println("Customer Details:");
            System.out.println("ID: " + customer.customerId);
            System.out.println("Name: " + customer.name);
            System.out.println("Email: " + customer.email);
            System.out.println("Phone: " + customer.phone);
        }
        System.out.println("--------------------------------");
    }
}

class Inventory {

    List<Garment> garments = new ArrayList<>();
    List<Invoice> invoices = new ArrayList<>();

    Inventory() {
        // Adding pre-existing garments
        Garment g1 = new Garment();
        g1.id = "G001";
        g1.name = "Silk Shirt";
        g1.description = "Premium quality silk shirt";
        g1.size = "M";
        g1.color = "Blue";
        g1.price = 1200.00;
        g1.discountPercentage = 10;
        g1.stockQuantity = 5;

        Garment g2 = new Garment();
        g2.id = "G002";
        g2.name = "Cotton T-shirt";
        g2.description = "Comfortable cotton t-shirt";
        g2.size = "L";
        g2.color = "White";
        g2.price = 800.00;
        g2.discountPercentage = 5;
        g2.stockQuantity = 10;

        garments.add(g1);
        garments.add(g2);
    }

    void addGarment(Garment garment) {
        garments.add(garment);
        System.out.println("Garment added successfully.");
    }

    void removeGarment(String id) {
        garments.removeIf(g -> g.id.equals(id));
        System.out.println("Garment removed successfully.");
    }

    Garment findGarment(String id) {
        for (Garment g : garments) {
            if (g.id.equals(id))
                return g;
        }
        return null;
    }

    void displayAllGarments() {
        System.out.println("Available Garments:");
        for (Garment g : garments) {
            System.out.println("ID: " + g.id + ", Name: " + g.name + ", Price: " + g.price + 
                               ", Discounted Price: " + g.calculateDiscountPrice() + 
                               ", Stock: " + g.stockQuantity);
        }
    }

    void buyGarment(String id, Customer customer) {
        Garment garment = findGarment(id);
        if (garment != null && garment.stockQuantity > 0) {
            garment.stockQuantity--; // Decrease stock quantity
            double discountedPrice = garment.calculateDiscountPrice();
            invoices.add(new Invoice(garment, discountedPrice, customer)); // Add new invoice with customer details
            System.out.println("Purchase successful!");
        } else {
            System.out.println("Garment not available or out of stock.");
        }
    }

    void displayInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("No invoices to display.");
        } else {
            System.out.println("Invoices:");
            for (Invoice invoice : invoices) {
                invoice.printInvoice();
            }
        }
    }
}

class Customer {

    public String customerId;
    public String name;
    public String email;
    public String phone;

    boolean hasDetails() {
        return customerId != null && !customerId.isEmpty() && 
               name != null && !name.isEmpty() &&
               email != null && !email.isEmpty() &&
               phone != null && !phone.isEmpty();
    }

    void displayCustomerDetails() {
        System.out.println("Customer [ID=" + customerId + ", Name=" + name + ", Email=" + email + ", Phone=" + phone + "]");
    }
}

public class OopLabTask3 {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Customer customer = new Customer();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Garment Store Menu ***");
            System.out.println("1. Add Garment");
            System.out.println("2. Remove Garment");
            System.out.println("3. Find Garment");
            System.out.println("4. Display All Garments");
            System.out.println("5. Enter Customer Details");
            System.out.println("6. Display Customer Details");
            System.out.println("7. Buy Garment");
            System.out.println("8. Display Invoices");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    Garment newGarment = new Garment();
                    System.out.print("Enter Garment ID: ");
                    newGarment.id = scanner.nextLine();
                    System.out.print("Enter Garment Name: ");
                    newGarment.name = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    newGarment.description = scanner.nextLine();
                    System.out.print("Enter Size: ");
                    newGarment.size = scanner.nextLine();
                    System.out.print("Enter Color: ");
                    newGarment.color = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    newGarment.price = scanner.nextDouble();
                    System.out.print("Enter Discount Percentage: ");
                    newGarment.discountPercentage = scanner.nextDouble();
                    System.out.print("Enter Stock Quantity: ");
                    newGarment.stockQuantity = scanner.nextInt();
                    inventory.addGarment(newGarment);
                }

                case 2 -> {
                    System.out.print("Enter Garment ID to remove: ");
                    String removeId = scanner.nextLine();
                    inventory.removeGarment(removeId);
                }

                case 3 -> {
                    System.out.print("Enter Garment ID to find: ");
                    String findId = scanner.nextLine();
                    Garment foundGarment = inventory.findGarment(findId);
                    if (foundGarment != null) {
                        System.out.println("Garment Found: " + foundGarment);
                    } else {
                        System.out.println("Garment not found.");
                    }
                }

                case 4 -> inventory.displayAllGarments();

                case 5 -> {
                    System.out.print("Enter Customer ID: ");
                    customer.customerId = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    customer.name = scanner.nextLine();
                    System.out.print("Enter Customer Email: ");
                    customer.email = scanner.nextLine();
                    System.out.print("Enter Customer Phone: ");
                    customer.phone = scanner.nextLine();
                    System.out.println("Customer details saved.");
                }

                case 6 -> {
                    System.out.println("Customer Details:");
                    customer.displayCustomerDetails();
                }

                case 7 -> {
                    System.out.print("Enter Garment ID to buy: ");
                    String buyId = scanner.nextLine();
                    inventory.buyGarment(buyId, customer);
                }

                case 8 -> inventory.displayInvoices();

                case 9 -> {
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
