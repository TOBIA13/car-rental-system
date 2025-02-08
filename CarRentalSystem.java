import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String model;
    private boolean isAvailable;

    public Car(String carId, String model) {
        this.carId = carId;
        this.model = model;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rentCar() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "Car ID: " + carId + ", Model: " + model + ", Available: " + isAvailable;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name;
    }
}

class RentalTransaction {
    private String transactionId;
    private Customer customer;
    private Car car;

    public RentalTransaction(String transactionId, Customer customer, Car car) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.car = car;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Customer: " + customer.getName() + ", Car: " + car.getModel();
    }
}

class RentalAgency {
    private List<Car> cars;
    private List<Customer> customers;
    private List<RentalTransaction> transactions;

    public RentalAgency() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Car findAvailableCar(String model) {
        for (Car car : cars) {
            if (car.getModel().equalsIgnoreCase(model) && car.isAvailable()) {
                return car;
            }
        }
        return null;
    }

    public Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public void rentCar(String transactionId, String customerId, String model) {
        Customer customer = findCustomer(customerId);
        Car car = findAvailableCar(model);

        if (customer != null && car != null) {
            car.rentCar();
            RentalTransaction transaction = new RentalTransaction(transactionId, customer, car);
            transactions.add(transaction);
            System.out.println("Rental successful: " + transaction);
        } else {
            System.out.println("Rental failed: Either car or customer not found.");
        }
    }

    public void returnCar(String carId) {
        for (Car car : cars) {
            if (car.getCarId().equals(carId) && !car.isAvailable()) {
                car.returnCar();
                System.out.println("Car returned successfully.");
                return;
            }
        }
        System.out.println("Car return failed: Car not found or already available.");
    }

    public void listCars() {
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    public void listCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}

public class CarRentalSystem {
        public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();
        Scanner scanner = new Scanner(System.in);

        // Adding some cars and customers
        agency.addCar(new Car("S550", "Mercedes"));
        agency.addCar(new Car("M5", "BMW"));
        agency.addCustomer(new Customer("123", "Tobias"));
        agency.addCustomer(new Customer("456", "Draco"));

        while (true) {
            System.out.println("\nCar Rental System");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. List Cars");
            System.out.println("4. List Customers");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter Transaction ID: ");
                    String transactionId = scanner.nextLine();
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Car Model: ");
                    String model = scanner.nextLine();
                    agency.rentCar(transactionId, customerId, model);
                    break;
                case 2:
                    System.out.print("Enter Car ID: ");
                    String carId = scanner.nextLine();
                    agency.returnCar(carId);
                    break;
                case 3:
                    agency.listCars();
                    break;
                case 4:
                    agency.listCustomers();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
