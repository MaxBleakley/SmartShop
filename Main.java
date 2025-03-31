import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;



class Product{
    //attributes
    public String product_name;
    public double product_price;
    public Integer product_inv_count;
    public double sale_count;
    public double total_dosh;
    //methods
    //constucter
    public Product(String product_name,double product_price,Integer product_inv_count,double sale_count){
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_inv_count=product_inv_count;
        this.sale_count = sale_count;
    }
    //gets product inventory count
    public Integer getproduct_inv_count(){
        return  product_inv_count;
    }
    //sets product inventory to entered value
    public void setproduct_inv_count(Integer product_inv_count) {
        this.product_inv_count = product_inv_count;
    }
    //sets products price
    public void setproduct_price(double product_price) {
        this.product_price = product_price;
    }
    //gets product price
    public double getproduct_price() {
        return product_price;
    }
    //updates the inventory count based on sales also calculates total money made and total number of sales
    public void product_sale(Integer x ) {
        Integer temp = this.product_inv_count;
        this.product_inv_count = (temp - x);
        this.sale_count = this.sale_count + x;
        double index = 0;
        index = (this.sale_count * this.product_price);
        this.total_dosh = index;
    }
    //generate scuffed report if we get time we should sort this god awful code i just made
    public void generate_report(){
        try (FileWriter writer = new FileWriter("report.txt",true)){
            writer.write("name:" + this.product_name + "\n");
            writer.write("total sales:" + this.sale_count +"\n");
            writer.write("total sold:" + this.total_dosh +"\n");
            writer.write("total inventory left:" + this.product_inv_count + "\n");
            writer.write("------------------------------------------------------------ \n");

        }catch (IOException e){
            System.out.println("ts(this shit) mad broken bruzz(bro huzz) ong(on god) ts(this shit) is pmo(pissing me off)");
        }
    }
}
class ProductUI extends JFrame{
    private JTextField namefield,pricefield,invfield,salefield;
    private JTextArea outputarea;
    private Product product;


    public ProductUI(){
        //makes the chopped ah ui
        setTitle("ts mad pmo");
        setSize(690,420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,4));


        add(new JLabel("product name:"));
        namefield = new JTextField();
        add(namefield);

        add(new JLabel("product price:"));
        pricefield = new JTextField();
        add(pricefield);

        add(new JLabel("inventory count" +
                ":"));
        invfield = new JTextField();
        add(invfield);

        add(new JLabel("sales:"));
        salefield = new JTextField();
        add(salefield);

        JButton addButton = new JButton("add product");
        JButton sellButton = new JButton("sale");
        JButton reportButton = new JButton("make report");

        add(addButton);
        add(sellButton);
        add(reportButton);

        outputarea = new JTextArea(10, 30);
        outputarea.setEditable(false);
        add(new JScrollPane(outputarea));
    }
}


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        new ProductUI();
        }
}
