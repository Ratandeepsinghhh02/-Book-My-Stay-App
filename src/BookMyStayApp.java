import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String guestName;
    String roomType;
    String reservationId;

    Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }
}

class RoomInventory implements Serializable {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    HashMap<String, Integer> getInventory() {
        return inventory;
    }

    void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    void display() {
        System.out.println("Current Inventory: " + inventory);
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    static void saveState(List<Reservation> bookings, RoomInventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookings);
            oos.writeObject(inventory);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    static Object[] loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Reservation> bookings = (List<Reservation>) ois.readObject();
            RoomInventory inventory = (RoomInventory) ois.readObject();
            System.out.println("State restored successfully.");
            return new Object[]{bookings, inventory};
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Book My Stay - Persistence Demo ");
        System.out.println(" Use Case 12 (v12.1) ");
        System.out.println("======================================");

        List<Reservation> bookingHistory;
        RoomInventory inventory;

        Object[] restored = PersistenceService.loadState();

        if (restored != null) {
            bookingHistory = (List<Reservation>) restored[0];
            inventory = (RoomInventory) restored[1];
        } else {
            bookingHistory = new ArrayList<>();
            inventory = new RoomInventory();
        }

        // Simulate new booking
        Reservation r1 = new Reservation("Alice", "Single Room", "R1");
        bookingHistory.add(r1);

        System.out.println("Booking added: " + r1.guestName);

        inventory.display();

        // Save state before shutdown
        PersistenceService.saveState(bookingHistory, inventory);

        System.out.println("Application terminated safely.");
    }
}