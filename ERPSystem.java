
import java.util.ArrayList;

public class ERPSystem {

    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();

    // Add Employee
    public void addEmployee(Employee employee) {

        employees.add(employee);

        System.out.println("Employee added successfully!");
    }

    // Display Employees
    public void displayEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        System.out.println("\n===== EMPLOYEE DETAILS =====");

        for (Employee employee : employees) {
            employee.displayEmployee();
        }
    }

    // Add Product
    public void addProduct(Product product) {

        products.add(product);

        System.out.println("Product added successfully!");
    }

    // Display Products
    public void displayProducts() {

        if (products.isEmpty()) {
            System.out.println("No products found!");
            return;
        }

        System.out.println("\n===== PRODUCT DETAILS =====");

        for (Product product : products) {
            product.displayProduct();
        }
    }

    // Update Inventory
    public void updateStock(int productId, int quantity) {

        for (Product product : products) {

            if (product.productId == productId) {

                if (quantity < 0 &&
                        product.quantity < -quantity) {

                    System.out.println("Insufficient stock!");
                    return;
                }

                product.quantity += quantity;

                System.out.println("Stock updated successfully!");
                return;
            }
        }

        System.out.println("Product not found!");
    }

    // Record Sale
    public void recordSale(int productId, int quantity) {

        for (Product product : products) {

            if (product.productId == productId) {

                if (quantity <= 0) {
                    System.out.println("Enter a valid quantity!");
                    return;
                }

                if (product.quantity >= quantity) {

                    product.quantity -= quantity;

                    double total = product.price * quantity;

                    System.out.println("Sale completed successfully!");
                    System.out.println("Product: " + product.productName);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Total Amount: Rs. " + total);

                } else {

                    System.out.println("Insufficient stock!");
                }

                return;
            }
        }

        System.out.println("Product not found!");
    }
}
