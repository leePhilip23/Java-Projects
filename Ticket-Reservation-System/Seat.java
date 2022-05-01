/*
 * Philip Lee (phl200002)
 * CS 2336.001 Project 2
 * Seat class
 */

public class Seat {
    private int row; // Stores Row
    private char seat; // Stores Seat Column
    private char ticket; // Stores Ticket Type

    // Default Constructor
    public Seat() {}

    // Overloaded Constructor
    public Seat(int rows, char seats, char tickets) {
        this.row = rows;
        this.seat = seats;
        this.ticket = tickets;
    }

    // Getter Method for int row
    public int getRow() {
        return row;
    }

    // Getter Method for char seat
    public char getSeat() {
        return seat;
    }

    // Getter Method for char ticket
    public char getTicket() {
        return ticket;
    }

    // Setter Method for int row
    public void setRow(int row) {
        this.row = row;
    }

    // Setter Method for char seat
    public void setSeat(char seat) {
        this.seat = seat;
    }

    // Setter Method for char ticket
    public void setTicket(char ticket) {
        this.ticket = ticket;
    }
}
