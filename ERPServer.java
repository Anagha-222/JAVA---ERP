
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ERPServer {

    static ERPSystem erp = new ERPSystem();

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0);

        server.createContext("/api/employees", ERPServer::employees);
        server.createContext("/api/products", ERPServer::products);
        server.createContext("/api/stock", ERPServer::stock);
        server.createContext("/api/sales", ERPServer::sales);

        server.start();

        System.out.println("ERP Java Backend Started!");
        System.out.println("Server running at http://localhost:8081");
    }

    static Map<String, String> readData(HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8);

        Map<String, String> data = new HashMap<>();

        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);

            if (parts.length == 2) {
                data.put(
                    URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
                    URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                );
            }
        }

        return data;
    }

    static void send(HttpExchange exchange, String response)
            throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type", "application/json; charset=UTF-8");

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin", "*");

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    static String escape(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");
    }

    static void employees(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("OPTIONS")) {
            send(exchange, "{}");
            return;
        }

        if (exchange.getRequestMethod().equals("POST")) {

            Map<String, String> data = readData(exchange);

            Employee employee = new Employee(
                Integer.parseInt(data.get("id")),
                data.get("name"),
                data.get("department"),
                Double.parseDouble(data.get("salary"))
            );

            erp.addEmployee(employee);

            send(exchange, "{\"message\":\"Employee added successfully\"}");
            return;
        }

        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < erp.employees.size(); i++) {

            Employee e = erp.employees.get(i);

            if (i > 0) result.append(",");

            result.append("{")
                .append("\"id\":").append(e.employeeId).append(",")
                .append("\"name\":\"").append(escape(e.employeeName)).append("\",")
                .append("\"department\":\"").append(escape(e.department)).append("\",")
                .append("\"salary\":").append(e.salary)
                .append("}");
        }

        result.append("]");

        send(exchange, result.toString());
    }

    static void products(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("OPTIONS")) {
            send(exchange, "{}");
            return;
        }

        if (exchange.getRequestMethod().equals("POST")) {

            Map<String, String> data = readData(exchange);

            Product product = new Product(
                Integer.parseInt(data.get("id")),
                data.get("name"),
                Double.parseDouble(data.get("price")),
                Integer.parseInt(data.get("quantity"))
            );

            erp.addProduct(product);

            send(exchange, "{\"message\":\"Product added successfully\"}");
            return;
        }

        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < erp.products.size(); i++) {

            Product p = erp.products.get(i);

            if (i > 0) result.append(",");

            result.append("{")
                .append("\"id\":").append(p.productId).append(",")
                .append("\"name\":\"").append(escape(p.productName)).append("\",")
                .append("\"price\":").append(p.price).append(",")
                .append("\"quantity\":").append(p.quantity)
                .append("}");
        }

        result.append("]");

        send(exchange, result.toString());
    }

    
static void stock(HttpExchange exchange) throws IOException {

    if (exchange.getRequestMethod().equals("OPTIONS")) {
        send(exchange, "{}");
        return;
    }

    Map<String, String> data = readData(exchange);

    int id = Integer.parseInt(data.get("id"));
    int change = Integer.parseInt(data.get("change"));

    for (Product p : erp.products) {

        if (p.productId == id) {

            if (change == 0 || p.quantity + change < 0) {
                send(exchange, "{\"error\":\"Invalid stock quantity\"}");
                return;
            }

            p.quantity += change;

            send(exchange,
                "{\"message\":\"Stock updated successfully\",\"quantity\":"
                + p.quantity + "}");
            return;
        }
    }

    send(exchange, "{\"error\":\"Product not found\"}");
}


    static void sales(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("OPTIONS")) {
            send(exchange, "{}");
            return;
        }

        Map<String, String> data = readData(exchange);

        int id = Integer.parseInt(data.get("id"));
        int quantity = Integer.parseInt(data.get("quantity"));

        for (Product p : erp.products) {

            if (p.productId == id) {

                if (quantity <= 0 || p.quantity < quantity) {
                    send(exchange, "{\"error\":\"Invalid quantity or insufficient stock\"}");
                    return;
                }

                double total = p.price * quantity;
                p.quantity -= quantity;

                send(exchange,
                    "{\"message\":\"Sale successful\",\"total\":" + total + "}");
                return;
            }
        }

        send(exchange, "{\"error\":\"Product not found\"}");
    }
}
