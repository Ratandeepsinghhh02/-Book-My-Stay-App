import java.util.HashMap;

abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    abstract void display();
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 2000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String type) {
        return inventory.get(type);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // UC4
        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v4.1 ");
        System.out.println("======================================");

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        System.out.println("----- Available Rooms -----");

        if (inventory.getAvailability("Single Room") > 0) {
            single.display();
            System.out.println("Available: " + inventory.getAvailability("Single Room"));
        }

        if (inventory.getAvailability("Double Room") > 0) {
            dbl.display();
            System.out.println("Available: " + inventory.getAvailability("Double Room"));
        }

        if (inventory.getAvailability("Suite Room") > 0) {
            suite.display();
            System.out.println("Available: " + inventory.getAvailability("Suite Room"));
        }

        System.out.println("Application terminated.");
    }
}