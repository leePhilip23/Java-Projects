/*
   Philip Lee (phl200002)
   Project 3
   DVD Class
*/

public class DVD implements Comparable<DVD>{
    private String title; // Title of movie
    private int available; // Number of movies available
    private int rented; // Number of movies rented

    // Constructor
    public DVD() {}

    // Overloaded Constructor
    public DVD(String title_, int available_, int rented_) {
        this.title = title_;
        this.available = available_;
        this.rented = rented_;
    }

    // Getter method for "title"
    public String getTitle() {
        return title;
    }

    // Getter method for "available"
    public int getAvailable() {
        return available;
    }

    // Getter method for "rented"
    public int getRented() {
        return rented;
    }

    // Setter method for "title"
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter method for "available"
    public void setAvailable(int available) {
        this.available = available;
    }

    // Setter method for "setRented"
    public void setRented(int rented) {
        this.rented = rented;
    }

    // Overloaded compareTo() method
    public int compareTo(DVD o) {
        return title.compareTo(o.getTitle());
    }

    // Overloaded toString() method
    public String toString() {
        return title + "\t\t" + this.available + " " + this.rented;
    }
}
