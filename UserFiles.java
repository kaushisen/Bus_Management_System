import java.io.*;
import java.util.*;

class Customer {
    int customerId;
    String name;
    int CustomerMobileNum;

    public Customer(int customerId, String name, int CustomerMobileNum) {
        this.customerId = customerId;
        this.name = name;
        this.CustomerMobileNum = CustomerMobileNum;
    }

    @Override
    public String toString() {
        return customerId + "," + name + "," + CustomerMobileNum;
    }

    public static Customer fromString(String line) {
        String[] parts = line.split(",");
        return new Customer(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
    }
}

class Bus {
    int busId;
    String busName;
    String sourceLocation;
    String destinationLocation;
    List<String> subStops;
    int availableSeats;
    String type;
    String addedBy;

    public Bus(int busId, String busName, String sourceLocation, String destinationLocation,
               List<String> subStops, int availableSeats, String type, String addedBy) {
        this.busId = busId;
        this.busName = busName;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.subStops = subStops;
        this.availableSeats = availableSeats;
        this.type = type;
        this.addedBy = addedBy;
    }

    @Override
    public String toString() {
        return busId + "," + busName + "," + sourceLocation + "," + destinationLocation + "," +
               String.join("|", subStops) + "," + availableSeats + "," + type + "," + addedBy;
    }

    public static Bus fromString(String line) {
        String[] parts = line.split(",");
        List<String> stops = Arrays.asList(parts[4].split("\\|"));
        return new Bus(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], stops,
                       Integer.parseInt(parts[5]), parts[6], parts[7]);
    }
}

class Order {
    int customerId;
    int busId;
    double price;
    String sourceLocation;
    String destinationLocation;

    public Order(int customerId, int busId, double price, String sourceLocation, String destinationLocation) {
        this.customerId = customerId;
        this.busId = busId;
        this.price = price;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
    }

    @Override
    public String toString() {
        return customerId + "," + busId + "," + price + "," + sourceLocation + "," + destinationLocation;
    }

    public static Order fromString(String line) {
        String[] parts = line.split(",");
        return new Order(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                         Double.parseDouble(parts[2]), parts[3], parts[4]);
    }
}

public class UserFiles {
    static Scanner scanner = new Scanner(System.in);
    static List<Bus> busList = new ArrayList<>();
    static List<Customer> customerList = new ArrayList<>();
    static List<Order> orderList = new ArrayList<>();

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\nEnter user type: 1. Customer  2. Admin");
            System.out.println("\nEnter your choice ...");
            int choice = scanner.nextInt();

            if (choice == 1) {
                customer();
            } else if (choice == 2) {
                admin();
            } else {
                System.out.println("Invalid input.");
            }

            saveData();
        }
    }

    static void loadData() {
        busList.clear();
        customerList.clear();
        orderList.clear();
        readFile("buses.txt");
        readFile("customers.txt");
        readFile("orders.txt");

        if (busList.isEmpty()) {
            busList.add(new Bus(1, "Express A", "Chennai", "Bangalore", Arrays.asList("Vellore", "Hosur"), 10, "AC", "Default"));
            busList.add(new Bus(2, "Express B", "Trichy", "Chennai", Arrays.asList("Vilupuram", "Chidambaram"), 8, "DELUX", "Default"));
            busList.add(new Bus(3, "Express C", "Trichy", "Salem", Arrays.asList("Karur"), 12, "Non-AC", "Default"));
        }
    }

    static void saveData() {
        writeFile("buses.txt", busList);
        writeFile("customers.txt", customerList);
        writeFile("orders.txt", orderList);
    }

    static void writeFile(String filename, List<?> data) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Object item : data) {
                writer.println(item.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to " + filename);
        }
    }

    
    static void readFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (filename.equals("buses.txt")) {
                    busList.add(Bus.fromString(line));
                } else if (filename.equals("customers.txt")) {
                    customerList.add(Customer.fromString(line));
                } else if (filename.equals("orders.txt")) {
                    orderList.add(Order.fromString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from " + filename);
        }
    }

    static void admin() {
        System.out.println("1. Add Bus\n2. View All Buses\n3. Delete Bus");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addBus();
            case 2 -> viewAllBuses();
            case 3 -> deleteBus();
            default -> System.out.println("Invalid option.");
        }
    }

    static void addBus() {
        System.out.print("Enter Bus ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Bus Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Source: ");
        String source = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();

        System.out.print("Enter Sub Stops ");
        List<String> subStops = Arrays.asList(scanner.nextLine().split("\\s*,\\s*"));

        System.out.print("Enter Available Seats: ");
        int seats = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Bus Type: ");
        String type = scanner.nextLine();

        System.out.print("Enter Admin Name: ");
        String admin = scanner.nextLine();

        busList.add(new Bus(id, name, source, dest, subStops, seats, type, admin));
        System.out.println("Bus added successfully.");
    }

    static void viewAllBuses() {
        if (busList.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }

        for (Bus bus : busList) {
            System.out.println("\nBus ID: " + bus.busId +
                    ", Name: " + bus.busName +
                    ", From: " + bus.sourceLocation +
                    ", To: " + bus.destinationLocation +
                    ", Sub Stops: " + String.join(", ", bus.subStops) +
                    ", Seats: " + bus.availableSeats +
                    ", Type: " + bus.type +
                    (bus.addedBy.equals("Default") ? "" : ", Added By: " + bus.addedBy));
        }
    }

    static void deleteBus() {
        System.out.print("Enter Bus ID to delete: ");
        int id = scanner.nextInt();

        busList.removeIf(bus -> bus.busId == id);
        System.out.println("Bus deleted if existed.");
    }

    static void customer() {
        scanner.nextLine();

        System.out.print("Enter Customer ID: ");
        int custId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Mobile: ");
        int mobile = scanner.nextInt();
        scanner.nextLine();

        Customer customer = new Customer(custId, name, mobile);
        customerList.add(customer);

        System.out.print("Enter Source: ");
        String source = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();

        List<Bus> matchedBuses = new ArrayList<>();
        for (Bus bus : busList) {
            if (bus.sourceLocation.equalsIgnoreCase(source) &&
                bus.destinationLocation.equalsIgnoreCase(dest)) {
                matchedBuses.add(bus);
            }
        }

        if (matchedBuses.isEmpty()) {
            System.out.println("No buses found.");
            return;
        }

        double price = 500.0;
        System.out.println("\nAvailable Buses:");
        for (Bus bus : matchedBuses) {
            System.out.println("Bus ID: " + bus.busId + ", Name: " + bus.busName +
                    ", Type: " + bus.type + ", Seats: " + bus.availableSeats +
                    ", Ticket: Rs " + price);
        }

        System.out.print("\nEnter Bus ID to book: ");
        int busId = scanner.nextInt();

        for (Bus bus : matchedBuses) {
            if (bus.busId == busId) {
                if (bus.availableSeats > 0) {
                    bus.availableSeats--;
                    orderList.add(new Order(custId, busId, price, source, dest));
                    System.out.println("Booking successful.");
                } else {
                    System.out.println("No seats available.");
                }
            }
        }
    }
}
