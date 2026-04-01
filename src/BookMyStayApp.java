import java.util.*;

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

    void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class Reservation {
    String guestName;
    String roomType;
    String reservationId;

    Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }
}

class AddOnService {
    String name;
    double cost;

    AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // UC8
        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v8.1 ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room", "R1"));
        bookingQueue.add(new Reservation("Bob", "Double Room", "R2"));

        HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());

        HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();
        List<Reservation> bookingHistory = new ArrayList<>();

        int roomCounter = 1;

        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();

            if (inventory.getAvailability(r.roomType) > 0) {
                String roomId = r.roomType.substring(0, 2).toUpperCase() + roomCounter++;

                allocatedRooms.get(r.roomType).add(roomId);
                inventory.reduceAvailability(r.roomType);

                System.out.println(r.guestName + " booked " + r.roomType + " | Room ID: " + roomId);

                List<AddOnService> services = new ArrayList<>();
                services.add(new AddOnService("Breakfast", 500));
                services.add(new AddOnService("WiFi", 200));

                serviceMap.put(r.reservationId, services);

                double total = 0;
                for (AddOnService s : services) {
                    total += s.cost;
                }

                System.out.println("Add-ons for " + r.reservationId + ": " + total);

                bookingHistory.add(r);
            } else {
                System.out.println(r.guestName + " booking failed for " + r.roomType);
            }
        }

        System.out.println("----- Booking History -----");
        for (Reservation r : bookingHistory) {
            System.out.println(r.reservationId + " | " + r.guestName + " | " + r.roomType);
        }

        System.out.println("Application terminated.");
    }
}