
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ERPSystem erp = new ERPSystem();

        while (true) {

            System.out.println("\n===== JAVA ERP MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Add Product");
            System.out.println("4. Display Products");
            System.out.println("5. Update Inventory");
            System.out.println("6. Record Sale");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Employee Name: ");
                    String empName = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();

                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();

                    Employee employee = new Employee(
                            empId, empName, department, salary);

                    erp.addEmployee(employee);
                    break;

                case 2:

                    erp.displayEmployees();
                    break;

                case 3:

                    System.out.print("Enter Product ID: ");
                    int productId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Product Name: ");
                    String productName = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    Product product = new Product(
                            productId, productName, price, quantity);

                    erp.addProduct(product);
                    break;

                case 4:

                    erp.displayProducts();
                    break;

                case 5:

                    System.out.print("Enter Product ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter quantity to add or remove: ");
                    int stockChange = sc.nextInt();

                    erp.updateStock(updateId, stockChange);
                    break;

                case 6:

                    System.out.print("Enter Product ID: ");
                    int saleId = sc.nextInt();

                    System.out.print("Enter Sale Quantity: ");
                    int saleQuantity = sc.nextInt();

                    erp.recordSale(saleId, saleQuantity);
                    break;

                case 7:

                    System.out.println("Thank you for using Java ERP!");
                    sc.close();
                    return;

                default:

                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
