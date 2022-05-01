/*
 * Philip Lee (phl200002)
 * CS 2336.001 Project 2
 * Auditorium class
 */

public class Auditorium<Type> {
    private Node<Type> first; // First Pointer

    // Default Constructor
    public Auditorium() {}

    // Overloaded Constructor that connects and populates nodes with "seat"
    public Auditorium(Node<Type> first, Node<Type> tail, Node<Type> downTail, Type seat, int column) {
        Node<Type> newNode = new Node<>(seat);
        if (first == null) {
            this.first = newNode;
        }
        else if (column == 0) {
            downTail.setDown(newNode);
            this.first = first;
        }
        else {
            tail.setNext(newNode);
            this.first = first;
        }
    }

    // Getter Method for Node<Seat> first
    public Node<Type> getFirst() {
        return first;
    }

    // Setter Method for Node<Seat> first
    public void setFirst(Node<Type> first) {
        this.first = first;
    }
}
