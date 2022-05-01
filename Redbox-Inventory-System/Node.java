/*
   Philip Lee (phl200002)
   Project 3
   Node Class
*/

public class Node <Type extends Comparable<Type>> implements Comparable<Node<Type>>{
    private Node<Type> left; // Left node
    private Node<Type> right; // Right node
    private Type payload;  // Payload object

    // Constructor
    public Node(){}

    // Overloaded Constructor
    public Node(Type obj) {this.payload = obj;}

    // Getter method for "payload"
    public Type getPayload() {
        return payload;
    }

    // Getter method for "left"
    public Node<Type> getLeft() {
        return left;
    }

    // Getter method for "right"
    public Node<Type> getRight() {return right;}

    // Setter method for "payload"
    public void setPayload(Type obj) {
        this.payload = obj;
    }

    // Setter method for "left"
    public void setLeft(Node<Type> obj) {
        this.left = obj;
    }

    // Setter method for "right"
    public void setRight(Node<Type> obj) {
        this.right = obj;
    }

    // Overloaded compareTo() method
    public int compareTo(Node<Type> o) {
        return payload.compareTo(o.payload);
    }

    // Overloaded toString() method
    public String toString() {
        return payload.toString();
    }
}
