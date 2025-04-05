**Overview**
This Java Swing application is designed to manage products through a GUI It allows users to add products record sales and generate sales reports The application is built using a single window uses a CardLayout to switch between different panels for each of the key functionalities

**Class Description**
string product name product name  
double product name product price  
int product inventory count inventory amount  
double sales count sale quantity  
double total dosh sale price found by sale count  product price

**1 Products**
**Methods**
Constructor initializes product with specified name price inventory count and initial sales count  
Product_salesInteger x deducts x units from the inventory if sufficient stock is available updates the sale count and recalculates the total revenue  
generate report writes a sales report for the product to the file reporttxt including details such as total sales total revenue and remaining inventory  
toString Overridden to return the product name allowing products to be displayed in GUI components like combo boxes

**2 MainFrame**
CardLayout cardLayout manages between pannels  
JPanel cardPanel The container that holds all panels  
ArrayListProduct productList A global list holding all products  
References to various panels  
    MainMenuPanel mainMenuPanel  
    AddProductPanel addProductPanel  
    RecordSalePanel recordSalePanel  
    GenerateReportPanel generateReportPanel

**Key Methods**
Constructor  
    Set up the window initializes panels add them to the cardPanel  
    showPanelString name  
    Switches the current view to the specified panel also refreshes panels that use product selection to keep latest product data  
    mainString args  
    The entry point of the application that creates an instance of MainFrame

**4 AddProductPanel**
Purpose  
add a new product  
**Components**    
Text Fields  
        Product Name  
        Product Price  
        Inventory Count  
    Buttons  
        Add Product Validates input creates a new Product adds it to the list and displays a success message  
        Back Returns to the main menu  
**Functionality**
    Validates that the price and inventory count are in the correct numeric format if success clears the input fields and navigates back to the main menu  
    Displays error using JOptionPane if validation fails

**5 RecordSalePanel**
Purpose  
Allows the user to record a sale

**Components**
    Combo Box  
    Displays the list of products available for sale from the global product list  
    Text Field  
    For entering the sales quantity  
    Buttons  
        Record Sale Validates updates and displays confirmation  
        Back Returns to the main menu  
**Key Methods**
    refreshProductList  
    Clears and repopulates the combo box with the list of products from MainFrame

**Functionality**
    Checks if a product is selected  
    Ensures that the sale quantity does not  the available inventory  
    Updates the products inventory and sales figure if the sale is valid  
    Displays appropriate error or confirmation messages

**6 GenerateReportPanel**
Purpose  
Provides a user interface to generate a sales report

**Components**
    Combo Box  
    Contains options to select a specific product or All Items reports  
    Buttons  
        Generate Report Processes the selected option to generate a report  
        Back Returns to the main menu  
**Key Methods**
    refreshProductList  
    Clears and repopulates the combo box with a All Items option  
Functionality  
    When All Items is selected goes through entire product list and generates report for each product  
    If a single product is selected generates a report for that product only  
    Uses JOptionPane to display success messages or errors
