
public class Employee {

    int employeeId;
    String employeeName;
    String department;
    double salary;

    // Constructor
    public Employee(int employeeId, String employeeName,
                    String department, double salary) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.salary = salary;
    }

    // Display employee details
    public void displayEmployee() {

        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + employeeName);
        System.out.println("Department: " + department);
        System.out.println("Salary: " + salary);

        System.out.println("-------------------------");
    }
}
