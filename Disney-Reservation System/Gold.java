public class Gold extends Customer {
    protected float percentage;

    public Gold(String fName, String lName, String id, float spend, float discount) {
        super(fName, lName, id, spend);
        this.percentage = discount;
    }

    public void setPercentage(float discount) {
        this.percentage = discount;
    }

    public float getPercentage() {
        return percentage;
    }
}