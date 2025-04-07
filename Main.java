import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

    // Product class to manage product details and operations (unchanged except for toString())
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
                writer.write("Total Sales: " + this.sale_count + "\n");
                writer.write("Total Revenue: " + this.total_dosh + "\n");
                writer.write("Inventory Left: " + this.product_inv_count + "\n");
                writer.write("------------------------------------------------------------ \n");
            } catch (IOException e) {
                System.out.println("Error writing report file.");
            }
        }

        // So that the JComboBox displays the product name
        @Override
        public String toString() {
            return product_name;
        }
    }

    // MainFrame that uses CardLayout to switch between panels in a single window
    class MainFrame extends JFrame {
        private CardLayout cardLayout;
        private JPanel cardPanel;

        // Panels for each feature
        private MainMenuPanel mainMenuPanel;
        private AddProductPanel addProductPanel;
        private RecordSalePanel recordSalePanel;
        private GenerateReportPanel generateReportPanel;

        // Global product list
        public static ArrayList<Product> productList = new ArrayList<>();

        public MainFrame() {
            setTitle("Product Management");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);

            // Initialize panels and add them to the cardPanel
            mainMenuPanel = new MainMenuPanel(this);
            addProductPanel = new AddProductPanel(this);
            recordSalePanel = new RecordSalePanel(this);
            generateReportPanel = new GenerateReportPanel(this);

            cardPanel.add(mainMenuPanel, "MainMenu");
            cardPanel.add(addProductPanel, "AddProduct");
            cardPanel.add(recordSalePanel, "RecordSale");
            cardPanel.add(generateReportPanel, "GenerateReport");

            add(cardPanel);
            setVisible(true);
        }

        // Method to switch between panels.
        // When showing panels with product choices, refresh the combo box.
        public void showPanel(String name) {
            if(name.equals("RecordSale")) {
                recordSalePanel.refreshProductList();
            } else if(name.equals("GenerateReport")) {
                generateReportPanel.refreshProductList();
            }
            cardLayout.show(cardPanel, name);
        }

        public static void main(String[] args) {
            new MainFrame();
        }
    }

    // Main menu panel with three buttons to access each feature
    class MainMenuPanel extends JPanel {
        public MainMenuPanel(MainFrame mainFrame) {
            setLayout(new GridLayout(3, 1, 10, 10));
            JButton addButton = new JButton("Add Product");
            JButton saleButton = new JButton("Record Sale");
            JButton reportButton = new JButton("Generate Report");

            add(addButton);
            add(saleButton);
            add(reportButton);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showPanel("AddProduct");
                }
            });

            saleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showPanel("RecordSale");
                }
            });

            reportButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showPanel("GenerateReport");
                }
            });
        }
    }

    // Panel for adding a product
    class AddProductPanel extends JPanel {
        private JTextField nameField, priceField, invField;

        public AddProductPanel(MainFrame mainFrame) {
            setLayout(new GridLayout(5, 2, 5, 5));

            add(new JLabel("Product Name:"));
            nameField = new JTextField();
            add(nameField);

            add(new JLabel("Product Price:"));
            priceField = new JTextField();
            add(priceField);

            add(new JLabel("Inventory Count:"));
            invField = new JTextField();
            add(invField);

            JButton addProductButton = new JButton("Add Product");
            add(addProductButton);
            JButton backButton = new JButton("Back");
            add(backButton);

            addProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    double price;
                    int inventory;
                    try {
                        price = Double.parseDouble(priceField.getText());
                        inventory = Integer.parseInt(invField.getText());
                        // Create product and add to global list
                        MainFrame.productList.add(new Product(name, price, inventory, 0));
                        JOptionPane.showMessageDialog(AddProductPanel.this, "Product added successfully!\n" +
                                "Name: " + name + "\nPrice: " + price + "\nInventory: " + inventory);
                        // Optionally clear fields after successful addition
                        nameField.setText("");
                        priceField.setText("");
                        invField.setText("");
                        mainFrame.showPanel("MainMenu");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AddProductPanel.this, "Invalid input! Please enter valid values for price and inventory.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showPanel("MainMenu");
                }
            });
        }
    }

    // Panel for recording a sale with a combo box to choose the product
    class RecordSalePanel extends JPanel {
        private JComboBox<Product> productComboBox;
        private JTextField saleField;
        private DefaultComboBoxModel<Product> comboBoxModel;

        public RecordSalePanel(MainFrame mainFrame) {
            setLayout(new GridLayout(4, 2, 5, 5));

            add(new JLabel("Select Product:"));
            comboBoxModel = new DefaultComboBoxModel<>();
            productComboBox = new JComboBox<>(comboBoxModel);
            add(productComboBox);

            add(new JLabel("Sales Quantity:"));
            saleField = new JTextField();
            add(saleField);

            JButton saleButton = new JButton("Record Sale");
            add(saleButton);
            JButton backButton = new JButton("Back");
            add(backButton);

            saleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product selectedProduct = (Product) productComboBox.getSelectedItem();
                    if (selectedProduct == null) {
                        JOptionPane.showMessageDialog(RecordSalePanel.this, "No product available. Please add a product first.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        int saleQty = Integer.parseInt(saleField.getText());
                        if (saleQty > selectedProduct.getproduct_inv_count()) {
                            JOptionPane.showMessageDialog(RecordSalePanel.this, "Not enough inventory to complete the sale.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            selectedProduct.product_sale(saleQty);
                            JOptionPane.showMessageDialog(RecordSalePanel.this, "Sale recorded!\n" +
                                    "New Inventory: " + selectedProduct.getproduct_inv_count() + "\n" +
                                    "Total Sales: " + selectedProduct.sale_count);
                            saleField.setText("");
                            mainFrame.showPanel("MainMenu");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(RecordSalePanel.this, "Invalid input! Please enter a valid number for sales.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showPanel("MainMenu");
                }
            });
        }

        // Refresh the product list in the combo box
        public void refreshProductList() {
            comboBoxModel.removeAllElements();
            for (Product p : MainFrame.productList) {
                comboBoxModel.addElement(p);
            }
        }
    }

    // Panel for generating a report with a combo box to choose a product or all items
    class GenerateReportPanel extends JPanel {
        // Using Object to allow both Product and a String option.
        private JComboBox<Object> productComboBox;
        private DefaultComboBoxModel<Object> comboBoxModel;

        public GenerateReportPanel(MainFrame mainFrame) {
            setLayout(new GridLayout(4, 1, 5, 5));

            JPanel topPanel = new JPanel(new FlowLayout());
            topPanel.add(new JLabel("Select Product for Report:"));
            comboBoxModel = new DefaultComboBoxModel<>();
            productComboBox = new JComboBox<>(comboBoxModel);
            topPanel.add(productComboBox);
            add(topPanel);

            JButton generateButton = new JButton("Generate Report");
            add(generateButton);

            JButton backButton = new JButton("Back");
            add(backButton);

            generateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object selectedItem = productComboBox.getSelectedItem();
                    if (selectedItem == null) {
                        JOptionPane.showMessageDialog(GenerateReportPanel.this, "No product available. Please add a product first.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (selectedItem instanceof String && selectedItem.equals("All Items")) {
                        if (MainFrame.productList.isEmpty()) {
                            JOptionPane.showMessageDialog(GenerateReportPanel.this, "No products available to generate a report.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Generate report for all products
                            for (Product p : MainFrame.productList) {
                                p.generate_report();
                            }
                            JOptionPane.showMessageDialog(GenerateReportPanel.this, "Report generated successfully for all items.");
                            mainFrame.showPanel("MainMenu");
                        }
                    } else if (selectedItem instanceof Product) {
                        Product selectedProduct = (Product) selectedItem;
                        selectedProduct.generate_report();
                        JOptionPane.showMessageDialog(GenerateReportPanel.this, "Report generated successfully for product: " + selectedProduct.product_name);
                        mainFrame.showPanel("MainMenu");
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showPanel("MainMenu");
                }
            });
        }

        // Refresh the product list in the combo box, adding "All Items" as the first option
        public void refreshProductList() {
            comboBoxModel.removeAllElements();
            comboBoxModel.addElement("All Items");
            for (Product p : MainFrame.productList) {
                comboBoxModel.addElement(p);
            }
        }
    }