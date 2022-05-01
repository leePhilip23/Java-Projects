/*
 * Philip Lee (phl200002)
 * CS 2336.001 Project 2
 * Node class
 */

public class Node<Type> {
    private Node<Type> down; // Down Pointer
    private Node<Type> next; // Next Pointer
    private Type payload; // Payload Generic

    // Default Constructor
    public Node() {}

    // Overloaded Constructor
    public Node(Type payload) {
        this.down = null;
        this.next = null;
        this.payload = payload;
    }

    // Getter Method for Node<Seat> down
    public Node<Type> getDown() {
        return down;
    }

    // Getter Method for Node<Seat> next
    public Node<Type> getNext() {
        return next;
    }

    // Getter Method for Type payload
    public Type getPayload() {
        return payload;
    }

    // Setter Method for Node<Seat> down
    public void setDown(Node<Type> down) {
        this.down = down;
    }

    // Setter Method for Node<Seat> next
    public void setNext(Node<Type> next) {
        this.next = next;
    }

    // Setter Method for Type payload
    public void setPayload(Type payload) {
        this.payload = payload;
    }
}
