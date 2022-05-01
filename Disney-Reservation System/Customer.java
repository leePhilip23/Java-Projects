public class Customer {
    protected String ID;
    protected String firstName;
    protected String lastName;
    protected float spent;

    public Customer() {
    }

    public Customer(String id, String fName, String lName, float spend) {
        this.firstName = fName;
        this.lastName = lName;
        this.ID = id;
        this.spent = spend;
    }

    public void setFirstName(String fName) {
        this.firstName = fName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public void setSpent(float spend) {
        this.spent = spend;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getID() {
        return ID;
    }

    public float getSpent() {
        return spent;
    }
}
