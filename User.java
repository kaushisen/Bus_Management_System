import java.util.*;  

class Customer {
    int customerId;
    String name;
    String CustomerMobileNum;

    public Customer(int customerId, String name, String CustomerMobileNum) {
        this.customerId = customerId;
        this.name = name;
        this.CustomerMobileNum = CustomerMobileNum;
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
}


public class User{
    static Scanner scanner = new Scanner(System.in);
    static List<Bus> busList = new ArrayList<>();
    static List<Customer> customerList = new ArrayList<>();
    static List<Order> orderList = new ArrayList<>();

    public static void main(String[] args) {
        addDefaultBuses();

        while (true) {
            System.out.println("\nEnter user type: \n1. Customer  \n2. Admin");
            System.out.println("\nEnter your choice ...");
            int choice = scanner.nextInt();

            if (choice == 1) {
                customer();
            } else if (choice == 2) {
                admin();
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    
    static void addDefaultBuses() {
        busList.add(new Bus(1, "Express A", "Chennai", "Bangalore", Arrays.asList("Vellore", "Hosur"), 10, "AC", "Default"));
        busList.add(new Bus(2, "Express B", "Trichy", "Chennai", Arrays.asList("Vilupuram", "Chidambaram"), 8, "DELUX", "Default"));
        busList.add(new Bus(3, "Express C", "Trichy", "Salem", Arrays.asList("Karur"), 12, "Non-AC", "Default"));
    }

    static void admin() {
        System.out.println("1. Add Bus\n2. View All Bus Details\n3. Delete Bus");
		System.out.println("\nEnter your choice ...");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addBus(); 
                break;
            case 2:
                viewAllBuses();
                break;
            case 3:
                deleteBus();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    static void addBus() {
        System.out.print("Enter Bus ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Bus Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter sourceLocation Location: ");
        String sourceLocation = scanner.nextLine();

        System.out.print("Enter destinationLocation Location: ");
        String dest = scanner.nextLine();

        System.out.print("Enter Sub Stops (comma separated): ");
        String subStopsInput = scanner.nextLine();
        List<String> subStops = Arrays.asList(subStopsInput.split(",\\s*"));

        System.out.print("Enter Available Seats: ");
        int seats = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Bus Type (AC/DELUX/Non-AC): ");
        String type = scanner.nextLine();

        System.out.print("Enter your  Name: ");
        String adminName = scanner.nextLine();

        Bus bus = new Bus(id, name, sourceLocation, dest, subStops, seats, type, adminName);
        busList.add(bus);

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
                    ", sourceLocation: " + bus.sourceLocation +
                    ", destinationLocation: " + bus.destinationLocation +
                    ", Sub Stops: " + String.join(", ", bus.subStops) +
                    ", Available Seats: " + bus.availableSeats +
                    ", Type: " + bus.type +
                    (bus.addedBy.equals("Default") ? "" : ", Added By: " + bus.addedBy));
        }
    }

    static void deleteBus() {
        System.out.print("Enter Bus ID to delete: ");
        int id = scanner.nextInt();

        for (int i = 0; i < busList.size(); i++) {
            Bus bus = busList.get(i);
            if (bus.busId == id) {
                busList.remove(i);
                System.out.println("Bus with ID " + id + " deleted.");
                return;
                
            }
        }

       
        System.out.println("Bus not found!!!!");
        
    }

    static void customer() {
        scanner.nextLine();

        System.out.print("Enter Customer ID: ");
        int custId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Customer Name: ");
        String custName = scanner.nextLine();

        System.out.print("Enter Mobile Number: ");
        String mobile = scanner.nextLine();

        Customer customer = new Customer(custId, custName, mobile);
        customerList.add(customer);

        System.out.print("Enter sourceLocation : ");
        String sourceLocation = scanner.nextLine();

        System.out.print("Enter destinationLocation : ");
        String dest = scanner.nextLine();

        List<Bus> matchedBuses = new ArrayList<>();

        for (Bus bus : busList) {
            if (bus.sourceLocation.equalsIgnoreCase(sourceLocation) &&
                bus.destinationLocation.equalsIgnoreCase(dest)) {
                matchedBuses.add(bus);
            }
        }

        if (matchedBuses.isEmpty()) {
            System.out.println("No buses found.");
            return;
        }

        double fixedPrice = 500.0; 

        System.out.println("\nAvailable Buses:");
        for (Bus bus : matchedBuses) {
            System.out.println("Bus ID: " + bus.busId +
                    ", Name: " + bus.busName +
                    ", Type: " + bus.type +
                    ", Available Seats: " + bus.availableSeats +
                    ", Ticket Price: Rs " + fixedPrice);
        }

        System.out.print("\nEnter Bus ID to Book: ");
        int busId = scanner.nextInt();

        Bus selectedBus = null;
        for (Bus bus : busList) {
            if (bus.busId == busId) {
                selectedBus = bus;
                break;
            }
        }

        if (selectedBus != null && selectedBus.availableSeats > 0) {
            selectedBus.availableSeats--;

            Order order = new Order(custId, busId, fixedPrice, sourceLocation, dest);
            orderList.add(order);

            System.out.println("Ticket booked successfully!");
        } else {
            System.out.println("Invalid Bus ID or no available seats.");
        }

        System.out.println("\nPlease find your booking details below:");
        System.out.println("Customer ID: " + custId);
        System.out.println("Bus ID: " + busId);
        System.out.println("Source: " + sourceLocation);
        System.out.println("Destination: " + dest);
        System.out.println("Ticket Price: Rs " + fixedPrice);

    }
}
