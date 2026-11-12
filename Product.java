
public class Product {

    int productId;
    String productName;
    double price;
    int quantity;

    // Constructor
    public Product(int productId, String productName,
                   double price, int quantity) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Display product details
    public void displayProduct() {

        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price: Rs. " + price);
        System.out.println("Available Quantity: " + quantity);

        System.out.println("-------------------------");
    }
}
