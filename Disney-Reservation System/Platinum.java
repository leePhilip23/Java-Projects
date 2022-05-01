public class Platinum extends Customer {
    protected int bonus;

    public Platinum(String fName, String lName, String id, float spend, int bucks) {
        super(fName, lName, id, spend);
        this.bonus = bucks;
    }

    public void setBonus(int bucks) {
        this.bonus = bucks;
    }

    public int getBonus() {
        return bonus;
    }

}
