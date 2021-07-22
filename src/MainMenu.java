import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;

import java.util.*;

public class MainMenu {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Reservation Application\n" +
                "-------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "-----------------------------------\n" +
                "Please select a number for the menu option:\n");

        Scanner scanner = new Scanner(System.in);
        int order = scanner.nextInt();
        switch (order) {
            case 1:
                findAndReserveARoom();
                main(null);
                break;
            case 2:
                seeReservations();
                main(null);
                break;
            case 3:
                createAnAccount();
                main(null);
                break;
            case 4:
                AdminMenu.main(null);
                break;
            case 5:
                return;
            default:
                System.out.println("Error: Invalid Input");
                main(null);
                break;
        }
    }

    private static void findAndReserveARoom() {

        System.out.println("Enter CheckIn Date mm/dd/yyyy example 07/01/2021");
        Scanner scanner = new Scanner(System.in);
        String checkInString = scanner.nextLine();
        String[] dates = checkInString.split("/");
        if(dates.length != 3) {
            System.out.println("Date invalid");
            return;
        }
        Calendar checkInCalendar = Calendar.getInstance();
        checkInCalendar.set(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]) - 1, Integer.parseInt(dates[1]));
        Date checkIn = checkInCalendar.getTime();

        System.out.println("Enter CheckOut Date month/day/year example 7/21/2021");
        String checkOutString = scanner.nextLine();
        dates = checkOutString.split("/");
        if(dates.length != 3) {
            System.out.println("Date invalid");
            return;
        }
        Calendar checkOutCalendar = Calendar.getInstance();
        checkOutCalendar.set(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]) - 1, Integer.parseInt(dates[1]));
        Date checkOut = checkOutCalendar.getTime();

        Collection<IRoom> rooms = HotelResource.findARoom(checkIn, checkOut);
        if(rooms.size() == 0) {
            System.out.println("There is no rooms available between " + checkIn + " and " + checkOut);
            checkInCalendar.add(Calendar.DAY_OF_MONTH, 7);
            checkOutCalendar.add(Calendar.DAY_OF_MONTH, 7);
            checkIn = checkInCalendar.getTime();
            checkOut = checkOutCalendar.getTime();
            rooms = HotelResource.findARoom(checkIn, checkOut);
            if(rooms.size() > 0) {
                System.out.println("But we found these rooms between " + checkIn + " and " + checkOut);
                for(IRoom room : rooms) {
                    System.out.println(room);
                }
            } else {
                return;
            }
        } else {
            for(IRoom room : rooms) {
                System.out.println(room);
            }
        }

        System.out.println("Would you like to book a room? y/n");
        String answer = scanner.nextLine();
        while(!(answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("y"))) {
            System.out.println("Please enter Y (Yes) or N (No)\n");
            answer = scanner.nextLine();
        }
        if(answer.equalsIgnoreCase("y")) {
            System.out.println("Do you have an account with us? y/n");
            String hasAnAccount = scanner.nextLine();
            while(!(hasAnAccount.equalsIgnoreCase("n") || hasAnAccount.equalsIgnoreCase("y"))) {
                System.out.println("Please enter Y (Yes) or N (No)\n");
                hasAnAccount = scanner.nextLine();
            }
            if(hasAnAccount.equalsIgnoreCase("y")) {
                System.out.println("Enter Email format: name@domain.com");
                String email = scanner.nextLine();
                Customer customer = HotelResource.getCustomer(email);
                if(customer == null) {
                    System.out.println("There is no account with that email.");
                } else {
                    System.out.println("What room number would you like to reserve:");
                    String roomNumber = scanner.nextLine();
                    IRoom room = HotelResource.getRoom(roomNumber);
                    if(room == null) {
                        System.out.println("Room not available or does not exist.");

                    } else {
                        System.out.println("Reservation" +
                                customer.getFirstName() + ' ' + customer.getLastName() + '\n' +
                                "Room: " + room.getRoomNumber() + " - " + room.getRoomType() + " bed" + '\n' +
                                "Price: $" + room.getRoomPrice() + " price per night" + '\n' +
                                "Checkin Date: " + checkIn + '\n' +
                                "Checkout Date: " + checkOut + '\n');
                        HotelResource.bookARoom(customer.getEmail(), room, checkIn, checkOut);
                    }
                }
            } else {
                createAnAccount();
                main(null);
            }
        }
    }

    private static void seeReservations() {
        System.out.println("Enter Email format: name@domain.com");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
        if(reservations.size() == 0) {
            System.out.println("Sorry, there is no reservations with this email.");
        } else {
            for(Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }

    }

    private static void createAnAccount() {
        System.out.println("Enter Email format: name@domain.com");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();
        if(HotelResource.getCustomer(email) != null) {
            System.out.println("Email already taken.");
        } else {
            try {
                HotelResource.createACustomer(email, firstName, lastName);
            } catch (IllegalArgumentException e) {
                System.out.println("Email invalid.");
            }
        }


    }
}
