package model;

public class Room implements IRoom{
    private final String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(final String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public final void setEnumeration(RoomType enumeration) {
        this.enumeration = enumeration;
    }

    public final void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public final String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public final Double getRoomPrice() {
        return price;
    }

    @Override
    public final RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public final boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ' ' + enumeration + " bed Room Price: $" +  price;
    }
}
