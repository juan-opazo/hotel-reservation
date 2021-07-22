import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static List<IRoom> rooms = new ArrayList<IRoom>();

    public static void main(String[] args) {
        System.out.println("Admin Menu\n" +
                "-------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "----------------------------------\n" +
                "Please select a number for the menu option:\n");
        Scanner scanner = new Scanner(System.in);
        int order = scanner.nextInt();
        switch (order) {
            case 1:
                for(Customer customer : AdminResource.getAllCustomers()) {
                    System.out.println(customer);
                }
                main(null);
                break;
            case 2:
                for(IRoom room : AdminResource.getAllRooms()) {
                    System.out.println(room);
                }
                main(null);
                break;
            case 3:
                AdminResource.displayAllReservations();
                main(null);
                break;
            case 4:
                addARoom();
                AdminResource.addRoom(rooms);
                main(null);
                break;
            case 5:
                MainMenu.main(null);
                break;
        }

    }

    public static void addARoom() {
        System.out.println("Enter room number:\n");
        Scanner scanner = new Scanner(System.in);
        String roomNumber = scanner.nextLine();
        for(IRoom roomSaved : AdminResource.getAllRooms()) {
            if(roomSaved.getRoomNumber().equals(roomNumber)) {
                System.out.println("Room Number already created.");
                return;
            }
        }
        System.out.println("Enter price per night:\n");
        Double price = scanner.nextDouble();
        System.out.println("Enter room type: 1 for single bed, 2 for double bed\n");
        int roomType = scanner.nextInt();
        while(!(roomType == 1 || roomType == 2)) {
            System.out.println("Please enter either 1 or 2\n");
            roomType = scanner.nextInt();
        }
        System.out.println("Would you like to add another room y/n");
        String anotherRoom = scanner.next();
        while(!(anotherRoom.equalsIgnoreCase("n") || anotherRoom.equalsIgnoreCase("y"))) {
            System.out.println("Please enter Y (Yes) or N (No)\n");
            anotherRoom = scanner.next();
        }
        if(roomType == 1) {
            rooms.add(new Room(roomNumber, price, RoomType.SINGLE));
        } else {
            rooms.add(new Room(roomNumber, price, RoomType.DOUBLE));
        }
        if(anotherRoom.equalsIgnoreCase("y")) addARoom();
    }
}
