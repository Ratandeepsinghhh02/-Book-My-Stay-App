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
        return inventory.getOrDefault(type, -1);
    }

    void reduceAvailability(String type) throws InvalidBookingException {
        int available = getAvailability(type);
        if (available <= 0) throw new InvalidBookingException("No availability for " + type);
        inventory.put(type, available - 1);
    }

    void increaseAvailability(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    boolean isValidRoom(String type) {
        return inventory.containsKey(type);
    }
}

class Reservation {
    String guestName;
    String roomType;
    String reservationId;
    String roomId;

    Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }
}

class InvalidBookingException extends Exception {
    InvalidBookingException(String msg) {
        super(msg);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // UC10
        System.out.println("======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v10.1 ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room", "R1"));
        bookingQueue.add(new Reservation("Bob", "Double Room", "R2"));

        Map<String, Reservation> confirmedBookings = new HashMap<>();
        Stack<String> rollbackStack = new Stack<>();

        int roomCounter = 1;

        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();

            try {
                if (!inventory.isValidRoom(r.roomType)) {
                    throw new InvalidBookingException("Invalid room type: " + r.roomType);
                }

                inventory.reduceAvailability(r.roomType);

                String roomId = r.roomType.substring(0, 2).toUpperCase() + roomCounter++;
                r.roomId = roomId;

                confirmedBookings.put(r.reservationId, r);

                System.out.println(r.guestName + " booked " + r.roomType + " | Room ID: " + roomId);

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for " + r.guestName + ": " + e.getMessage());
            }
        }

        System.out.println("----- Cancellation -----");

        String cancelId = "R1";

        if (confirmedBookings.containsKey(cancelId)) {
            Reservation r = confirmedBookings.remove(cancelId);

            rollbackStack.push(r.roomId);
            inventory.increaseAvailability(r.roomType);

            System.out.println("Cancelled " + r.guestName + " | Room ID released: " + rollbackStack.peek());
        } else {
            System.out.println("Invalid cancellation request for " + cancelId);
        }

        System.out.println("Application terminated.");
    }
}