import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

// Product class to manage product details and operations
class Product {
    public String product_name;
    public double product_price;
    public Integer product_inv_count;
    public double sale_count;
    public double total_dosh;

    // Constructor
    public Product(String product_name, double product_price, Integer product_inv_count, double sale_count) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_inv_count = product_inv_count;
        this.sale_count = sale_count;
    }

    // Getter for product inventory count
    public Integer getproduct_inv_count() {
        return product_inv_count;
    }

    // Setter for product inventory count
    public void setproduct_inv_count(Integer product_inv_count) {
        this.product_inv_count = product_inv_count;
    }

    // Setter for product price
    public void setproduct_price(double product_price) {
        this.product_price = product_price;
    }

    // Getter for product price
    public double getproduct_price() {
        return product_price;
    }

    // Updates inventory count based on sales and calculates total revenue
    public void product_sale(Integer x) {
        if (x <= this.product_inv_count) {
            this.product_inv_count -= x;
            this.sale_count += x;
            this.total_dosh = this.sale_count * this.product_price;
        }
    }

    // Generates a sales report and saves it to a file
    public void generate_report() {
        try (FileWriter writer = new FileWriter("report.txt", true)) {
            writer.write("Name: " + this.product_name + "\n");
            writer.write("Total Sales (£): " + this.sale_count + "\n");
            writer.write("Total Revenue (£): " + this.total_dosh + "\n");
            writer.write("Inventory Left: " + this.product_inv_count + "\n");
            writer.write("------------------------------------------------------------ \n");
        } catch (IOException e) {
            System.out.println("Error writing report file.");
        }
    }
}

// GUI class for managing product inventory
class ProductUI extends JFrame {
    private JTextField namefield, pricefield, invfield, salefield;
    private JTextArea outputarea;
    private Product product;

    public ProductUI() {
        setTitle("Product Management");
        setSize(690, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 4));

        add(new JLabel("Product Name:"));
        namefield = new JTextField();
        add(namefield);

        add(new JLabel("Product Price:"));
        pricefield = new JTextField();
        add(pricefield);

        add(new JLabel("Inventory Count:"));
        invfield = new JTextField();
        add(invfield);

        add(new JLabel("Sales:"));
        salefield = new JTextField();
        add(salefield);

        JButton addButton = new JButton("Add Product");
        JButton sellButton = new JButton("Sale");
        JButton reportButton = new JButton("Make Report");

        add(addButton);
        add(sellButton);
        add(reportButton);

        outputarea = new JTextArea(10, 30);
        outputarea.setEditable(false);
        add(new JScrollPane(outputarea));

        // Event Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = namefield.getText();
                double price;
                int inventory;
                try {
                    price = Double.parseDouble(pricefield.getText());
                    inventory = Integer.parseInt(invfield.getText());
                    product = new Product(name, price, inventory, 0);
                    outputarea.setText("Product added successfully!\n" +
                            "Name: " + name + "\nPrice (£): " + price + "\nInventory: " + inventory);
                } catch (NumberFormatException ex) {
                    outputarea.setText("Invalid input! Please enter valid values for price and inventory.");
                }
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (product == null) {
                    outputarea.setText("No product available. Please add a product first.");
                    return;
                }
                try {
                    int sales = Integer.parseInt(salefield.getText());
                    if (sales > product.getproduct_inv_count()) {
                        outputarea.setText("Not enough inventory to complete the sale.");
                    } else {
                        product.product_sale(sales);
                        outputarea.setText("Sale recorded!\n" +
                                "New Inventory: " + product.getproduct_inv_count() + "\n" +
                                "Total Sales (£): " + product.sale_count);
                    }
                } catch (NumberFormatException ex) {
                    outputarea.setText("Invalid input! Please enter a valid number for sales.");
                }
            }
        });

        setVisible(true);
    }
}

// Main class to run the application
public class Main {
    public static void main(String[] args) {
        new ProductUI();
    }
}
