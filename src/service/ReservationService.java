package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static Map<Customer, List<Reservation>> mapOfReservations = new HashMap<Customer, List<Reservation>>();
    private static Map<String, IRoom> mapOfRooms = new HashMap<String, IRoom>();

    public ReservationService() {
        mapOfReservations = new HashMap<Customer, List<Reservation>>();
        mapOfRooms = new HashMap<String, IRoom>();
    }

    public static void addRoom(IRoom room) {
        mapOfRooms.put(room.getRoomNumber(), room);
    }

    public static IRoom getARoom(String roomId) {
        return mapOfRooms.get(roomId);
    }

    public static Collection<IRoom> getAllRooms() {
        return mapOfRooms.values();
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate,Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        if(!mapOfReservations.containsKey(customer)) {
            List<Reservation> listOfReservations = new ArrayList<Reservation>();
            listOfReservations.add(reservation);
            mapOfReservations.put(customer, listOfReservations);
        } else {
            mapOfReservations.get(customer).add(reservation);
        }
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> listOfRoomsAvailable = new ArrayList<IRoom>();
        Collection<IRoom> collectionOfRooms = mapOfRooms.values();
        for (IRoom room : collectionOfRooms) {
            boolean isRoomAvailable = true;
            for(List<Reservation> reservations : mapOfReservations.values()) {
                for(Reservation reservation : reservations) {
                    if(!(reservation.getCheckOutDate().compareTo(checkInDate) < 0 || reservation.getCheckInDate().compareTo(checkOutDate) > 0)) isRoomAvailable = false;
                }
            }
            if(isRoomAvailable) listOfRoomsAvailable.add(room);
        }
        return listOfRoomsAvailable;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        return mapOfReservations.get(customer);
    }

    public static void printAllReservation() {
        System.out.println(mapOfReservations);
    }
    static void printAllRooms() {
        System.out.println(mapOfRooms);
    }
}
