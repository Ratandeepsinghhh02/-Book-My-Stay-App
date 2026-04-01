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

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // UC6
        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v6.1 ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));

        HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());

        int roomCounter = 1;

        System.out.println("----- Processing Bookings -----");

        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();

            if (inventory.getAvailability(r.roomType) > 0) {
                String roomId = r.roomType.substring(0, 2).toUpperCase() + roomCounter++;

                allocatedRooms.get(r.roomType).add(roomId);
                inventory.reduceAvailability(r.roomType);

                System.out.println(r.guestName + " booked " + r.roomType + " | Room ID: " + roomId);
            } else {
                System.out.println(r.guestName + " booking failed for " + r.roomType);
            }
        }

        System.out.println("Application terminated.");
    }
}