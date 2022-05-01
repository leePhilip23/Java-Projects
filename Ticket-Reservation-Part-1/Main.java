/*
 * Philip Lee (phl200002)
 * CS 2336.001 Project 1
 * Disneyland Dining Rewards
 * Main class
 */

import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        Customer[] regular = new Customer[0]; // Holds regular customers
        Customer[] preferred = null; // Holds gold and platinum customers

        Scanner input = new Scanner(System.in);
        System.out.print("Customer file: ");
        String fileName1 = input.nextLine();
        File files = new File(fileName1);

        // Reads file for regular customers and makes sure it exists
        if (files.exists()) {
            regular = readRegular(fileName1, regular);
        } else {
            System.out.println("Doesn't exist");
        }

        System.out.print("Preferred File: ");
        String fileName2 = input.nextLine();
        files = new File(fileName2);

        // Makes sure preferred customers exists
        if (files.exists()) {
            preferred = readPreferred(fileName2, preferred);
        } else {
            System.out.println("Doesn't exist");
        }

        System.out.print("Orders File: ");
        String fileName3 = input.nextLine();

        files = new File(fileName3);
        if (files.exists()) {
            preferred = readOrdersUpdate(fileName3, regular, preferred);
            regular = readOrdersReg(fileName3, regular);
        } else {
            System.out.println("Doesn't exist");
            System.exit(0);
        }

        System.out.println();
        System.out.println("Calculated Regular");
        System.out.println("Regular length: " + regular.length);
        for (Customer value : regular) {
            float tempSpent = value.getSpent();
            String spent = String.format("%.02f", tempSpent);
            System.out.println(value.getID() + " " + value.getFirstName() + " " + value.getLastName() + " " + spent);
        } // Prints auditorium 2 to output file

        System.out.println();
        System.out.println("Calculated Preferred");
        System.out.println("Preferred length: " + preferred.length);
        for (Object o : preferred) {
            if (o instanceof Gold) {
                Gold gold = (Gold)o;
                float tempSpent = gold.getSpent();
                String spent = String.format("%.02f", tempSpent);
                System.out.println(gold.getID() + " " + gold.getFirstName() + " " + gold.getLastName() + " " + spent + " " + (int)gold.getPercentage() + "%");
            }

            if (o instanceof Platinum) {
                Platinum platinum = (Platinum)o;
                float tempSpent = platinum.getSpent();
                String spent = String.format("%.02f", tempSpent);
                System.out.println(platinum.getID() + " " + platinum.getFirstName() + " " + platinum.getLastName() + " " + spent + " " + platinum.getBonus());
            }
        }

        printToFileRegular(regular);
        printToFilePreferred(preferred);

    }

    public static Customer[] appendRegular(Customer[] arr, String id, String fName, String lName, float spend) {
        Customer[] array = new Customer[arr.length + 1];
        System.arraycopy(arr, 0, array, 0, arr.length);
        array[arr.length] = new Customer(id, fName, lName, spend);
        return array;
    }

    public static Customer[] removeRegular(Customer[] arr) {
        int count = 0;
        int countNull = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                arr[count++] = arr[i];
            } else {
                countNull++;
            }
        }
        Customer[] array = new Customer[arr.length - countNull];
        System.arraycopy(arr, 0, array, 0, array.length);
        return array;
    }

    public static Customer[] removeRegDuplicates(Customer[] arr) {
        int count = 0;
        int countDuplicate = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i].getID() == arr[j].getID()) {
                    countDuplicate++;
                }

            }
        }
        Customer[] array = new Customer[arr.length - countDuplicate];
        System.arraycopy(arr, 0, array, 0, array.length);
        return array;
    }

    public static Customer[] appendGold(Customer[] arr, String id, String fName, String lName, float spend, float percentage) {
        Customer[] array = new Customer[arr.length + 1];
        System.arraycopy(arr, 0, array, 0, arr.length);
        array[arr.length] = new Gold(id, fName, lName, spend, percentage);
        return array;
    }

    public static Customer[] removeGold(Customer[] arr) {
        int count = 0;
        int countNull = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                arr[count++] = arr[i];
            } else {
                countNull++;
            }
        }
        Customer[] array = new Customer[arr.length - countNull];
        System.arraycopy(arr, 0, array, 0, array.length);
        return array;
    }

    public static Customer[] appendPlatinum(Customer[] arr, String id, String fName, String lName, float spend, int bonus) {
        Customer[] array = new Customer[arr.length + 1];
        System.arraycopy(arr, 0, array, 0, arr.length);
        array[arr.length] = new Platinum(id, fName, lName, spend, bonus);
        return array;
    }

    public static Customer[] readRegular(String fileName, Customer[] arr) {
        System.out.println();
        System.out.println("Regular Initial");
        try (Scanner readFile = new Scanner(new File(fileName))) {
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                System.out.println(line);
                String[] customer = line.split(" ");

                if (customer.length != 4) {
                    continue;
                }

                String tempID = customer[0];
                boolean flag1 = true;
                for (int i = 0; i < tempID.length(); i++) {
                    if (!Character.isDigit(tempID.charAt(i))) {
                        flag1 = false;
                        break;
                    }
                }

                if (!flag1) {
                    continue;
                }

                String tempFirstName = customer[1];
                boolean flag2 = true;
                for (int i = 0; i < tempFirstName.length(); i++) {
                    if (!Character.isLetter(tempFirstName.charAt(i))) {
                        flag2 = false;
                        break;
                    }
                }

                if (!flag2) {
                    continue;
                }

                String tempLastName = customer[2];
                boolean flag3 = true;
                for (int i = 0; i < tempLastName.length(); i++) {
                    if (!Character.isLetter(tempLastName.charAt(i))) {
                        flag3 = false;
                        break;
                    }
                }

                if (!flag3) {
                    continue;
                }

                String tempPrevPay = customer[3];
                boolean flag4 = true;
                int countDot = 0;
                int countAfterDot = 0;
                for (int i = 0; i < tempPrevPay.length(); i++) {
                    if (countDot == 1) {
                        countAfterDot++;
                        if (countAfterDot > 2) {
                            flag4 = false;
                            break;
                        }
                    }


                    if (!Character.isDigit(tempPrevPay.charAt(i)) && tempPrevPay.charAt(i) != '.') {
                        flag4 = false;
                        break;
                    }

                    if (tempPrevPay.charAt(i) == '.') {
                        countDot++;
                        if (countDot > 1) {
                            flag4 = false;
                            break;
                        }
                    }
                }

                if (flag4) {
                    float spend = Float.parseFloat(customer[3]);
                    arr = appendRegular(arr, customer[0], customer[1], customer[2], spend);
                }
            } // Reads file line by line
        } catch (Exception ex) {
            System.out.print("Count1 Failed"); // If file fails for any reason during the reading
            System.exit(0);
        } // Catches exception if the file fails to open
        System.out.println();
        return arr;
    }

    public static Customer[] readPreferred(String fileName, Customer[] arr) {
        arr = new Customer[0];
        System.out.println();
        System.out.println("Initial Preferred");
        try (Scanner readFile = new Scanner(new File(fileName))) {
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                System.out.println(line);
                String[] customer = line.split(" ");

                if (customer.length != 5) {
                    continue;
                }

                String tempID = customer[0];
                boolean flag1 = true;
                for (int i = 0; i < tempID.length(); i++) {
                    if (!Character.isDigit(tempID.charAt(i))) {
                        flag1 = false;
                        break;
                    }
                }

                if (!flag1) {
                    continue;
                }

                String tempFirstName = customer[1];
                boolean flag2 = true;
                for (int i = 0; i < tempFirstName.length(); i++) {
                    if (!Character.isLetter(tempFirstName.charAt(i))) {
                        flag2 = false;
                        break;
                    }
                }

                if (!flag2) {
                    continue;
                }

                String tempLastName = customer[2];
                boolean flag3 = true;
                for (int i = 0; i < tempLastName.length(); i++) {
                    if (!Character.isLetter(tempLastName.charAt(i))) {
                        flag3 = false;
                        break;
                    }
                }

                if (!flag3) {
                    continue;
                }

                String tempPrevPay = customer[3];
                boolean flag4 = true;
                int countDot = 0;
                int countAfterDot = 0;
                for (int i = 0; i < tempPrevPay.length(); i++) {
                    if (countDot == 1) {
                        countAfterDot++;
                        if (countAfterDot > 2) {
                            flag4 = false;
                            break;
                        }
                    }


                    if (!Character.isDigit(tempPrevPay.charAt(i)) && tempPrevPay.charAt(i) != '.') {
                        flag4 = false;
                        break;
                    }

                    if (tempPrevPay.charAt(i) == '.') {
                        countDot++;
                        if (countDot > 1) {
                            flag4 = false;
                            break;
                        }
                    }
                }

                if (!flag4) {
                    continue;
                }

                String tempPercentOrBonus = customer[4];
                boolean flag5 = true;

                // Check to see if it's a bonus
                if (tempPercentOrBonus.charAt(tempPercentOrBonus.length() - 1) == '%') {
                    flag5 = false;
                }

                for (int i = 0; i < tempPercentOrBonus.length(); i++) {
                    if (!Character.isDigit(tempPercentOrBonus.charAt(i))) {
                        flag5 = false;
                    }
                }


                // If it is a bonus make sure to add it to object array
                if (flag5) {
                    float spend = Float.parseFloat(customer[3]);
                    String bonuses = customer[4];
                    int bonus = Integer.parseInt(bonuses);
                    arr = appendPlatinum(arr, customer[0], customer[1], customer[2], spend, bonus);
                }

                // If it is not a bonus make sure at least it is at least it's a percentage
                if (tempPercentOrBonus.equals("5%") || tempPercentOrBonus.equals("10%") || tempPercentOrBonus.equals("15%")) {
                    float spend = Float.parseFloat(customer[3]);
                    String percent = customer[4];
                    float percentage;
                    if (tempPercentOrBonus.length() == 2) {
                        percentage = Float.parseFloat(percent.substring(0,1));
                    } else {
                        percentage = Float.parseFloat(percent.substring(0,2));
                    }
                    arr = appendGold(arr, customer[0], customer[1], customer[2], spend, percentage);
                }
            } // Reads file line by line
        } catch (Exception ex) {
            System.out.print("Count2 Failed");
            System.exit(0);
        } // Catches exception if the file fails to open for some reason
        System.out.println();
        return arr;
    }

    public static Customer[] readOrdersUpdate(String fileName, Customer[] regular, Customer[] preferred) {
        System.out.println();
        System.out.println("Intial Orders");
        try (Scanner readFile = new Scanner(new File(fileName))) {
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                System.out.println(line);
                String[] orders = line.split(" ");

                if (orders.length != 5) {
                    continue;
                }

                String tempID = orders[0];
                boolean flag1 = true;
                for (int i = 0; i < tempID.length(); i++) {
                    if (!Character.isDigit(tempID.charAt(i))) {
                        flag1 = false;
                        break;
                    }
                }

                if (!flag1) {
                    continue;
                }

                String tempCupSize = orders[1];
                if (tempCupSize.length() > 1) {
                    continue;
                }

                if (tempCupSize.charAt(0) != 'S' && tempCupSize.charAt(0) != 'M' && tempCupSize.charAt(0) != 'L') {
                    continue;
                }

                String tempDrinkType = orders[2];
                boolean flag2 = true;
                for (int i = 0; i < tempDrinkType.length(); i++) {
                    if (!Character.isLetter(tempDrinkType.charAt(i))) {
                        flag2 = false;
                    }
                }

                if (!flag2) {
                    continue;
                }

                String tempDrink = tempDrinkType.toLowerCase();

                if (!tempDrink.equals("punch") && !tempDrink.equals("tea") && !tempDrink.equals("soda")) {
                    continue;
                }

                String tempSqInPrice = orders[3];
                boolean flag3 = true;
                int countDot = 0;
                int countAfterDot = 0;
                for (int i = 0; i < tempSqInPrice.length(); i++) {
                    if (countDot == 1) {
                        countAfterDot++;
                        if (countAfterDot > 2) {
                            flag3 = false;
                            break;
                        }
                    }

                    if (!Character.isDigit(tempSqInPrice.charAt(i)) && tempSqInPrice.charAt(i) != '.') {
                        flag3 = false;
                        break;
                    }

                    if (tempSqInPrice.charAt(i) == '.') {
                        countDot++;
                        if (countDot > 1) {
                            flag3 = false;
                            break;
                        }
                    }
                }

                if (!flag3) {
                    continue;
                }

                String tempQuantity = orders[4];
                boolean flag4 = true;
                for (int i = 0; i < tempQuantity.length(); i++) {
                    if (!Character.isDigit(tempQuantity.charAt(i))) {
                        flag4 = false;
                        break;
                    }
                }

                if (!flag4) {
                    continue;
                }

                preferred = calculate(orders, regular, preferred);
                preferred = removeGold(preferred);

            } // Reads file line by line
        } catch (Exception ex) {
            System.out.print("Count3 Failed");
            System.exit(0);
        } // Catches exception if there are issues reading the file
        return preferred;
    }

    public static Customer[] readOrdersReg(String fileName, Customer[] regular) {
        try (Scanner readFile = new Scanner(new File(fileName))) {
            while (readFile.hasNextLine()) {
                String line = readFile.nextLine();
                String[] orders = line.split(" ");

                if (orders.length != 5) {
                    continue;
                }

                String tempID = orders[0];
                boolean flag1 = true;
                for (int i = 0; i < tempID.length(); i++) {
                    if (!Character.isDigit(tempID.charAt(i))) {
                        flag1 = false;
                        break;
                    }
                }

                if (!flag1) {
                    continue;
                }

                String tempCupSize = orders[1];
                if (tempCupSize.length() > 1) {
                    continue;
                }

                if (tempCupSize.charAt(0) != 'S' && tempCupSize.charAt(0) != 'M' && tempCupSize.charAt(0) != 'L') {
                    continue;
                }

                String tempDrinkType = orders[2];
                boolean flag2 = true;
                for (int i = 0; i < tempDrinkType.length(); i++) {
                    if (!Character.isLetter(tempDrinkType.charAt(i))) {
                        flag2 = false;
                    }
                }

                if (!flag2) {
                    continue;
                }

                String tempDrink = tempDrinkType.toLowerCase();

                if (!tempDrink.equals("punch") && !tempDrink.equals("tea") && !tempDrink.equals("soda")) {
                    continue;
                }

                String tempSqInPrice = orders[3];
                boolean flag3 = true;
                int countDot = 0;
                int countAfterDot = 0;
                for (int i = 0; i < tempSqInPrice.length(); i++) {
                    if (countDot == 1) {
                        countAfterDot++;
                        if (countAfterDot > 2) {
                            flag3 = false;
                            break;
                        }
                    }

                    if (!Character.isDigit(tempSqInPrice.charAt(i)) && tempSqInPrice.charAt(i) != '.') {
                        flag3 = false;
                        break;
                    }

                    if (tempSqInPrice.charAt(i) == '.') {
                        countDot++;
                        if (countDot > 1) {
                            flag3 = false;
                            break;
                        }
                    }
                }

                if (!flag3) {
                    continue;
                }

                String tempQuantity = orders[4];
                boolean flag4 = true;
                for (int i = 0; i < tempQuantity.length(); i++) {
                    if (!Character.isDigit(tempQuantity.charAt(i))) {
                        flag4 = false;
                        break;
                    }
                }

                if (!flag4) {
                    continue;
                }

                regular = removeRegular(regular);
                regular = calculateReg(orders, regular);

            } // Reads file line by line
        } catch (Exception ex) {
            System.out.print("Count4 Failed");
            System.exit(0);
        } // Catches exception if there are issues reading the file
        return regular;
    }

    public static Customer[] calculate(String[] orders, Customer[] regular, Customer[] preferred) {
        float costSoda = 0.20f;
        float costTea = 0.12f;
        float costPunch = 0.15f;

        float diameterS = 4f;
        float heightS = 4.5f;
        float ozS = 12f;

        float diameterM = 4.5f;
        float heightM = 5.75f;
        float ozM = 20f;

        float diameterL = 5.5f;
        float heightL = 7f;
        float ozL = 32f;

        float PI = (float)Math.PI;
        float cost = 0f;
        float prev = 0f;
        float first = 0f;
        float total;
        float finale;

        float sqIn = Float.parseFloat(orders[3]);
        float quantity = Float.parseFloat(orders[4]);

        if (preferred != null) {
            for (int j = 0; j < preferred.length; j++) {
                if (preferred[j].getID().equals(orders[0])) {
                    if (orders[2].equals("punch")) {
                        first = costPunch * quantity;
                    } else if (orders[2].equals("tea")) {
                        first = costTea * quantity;
                    } else if (orders[2].equals("soda")) {
                        first = costSoda * quantity;
                    }

                    if (orders[1].equals("S")) {
                        finale = PI * diameterS * heightS * sqIn * quantity + ozS * first;
                        prev = preferred[j].getSpent();
                        cost = preferred[j].getSpent() + finale;
                    } else if (orders[1].equals("M")) {
                        finale = PI * diameterM * heightM * sqIn * quantity + ozM * first;
                        prev = preferred[j].getSpent();
                        cost = preferred[j].getSpent() + finale;
                    } else if (orders[1].equals("L")) {
                        finale = PI * diameterL * heightL * sqIn * quantity + ozL * first;
                        prev = preferred[j].getSpent();
                        cost = preferred[j].getSpent() + finale;
                    }

                    if (cost >= 50.0f && cost <= 100f) {
                        total = cost - ((cost - prev) * (float)0.05);
                        Gold gold = (Gold)preferred[j];
                        preferred[j].setSpent(total);
                        gold.setPercentage(5);
                        break;
                    } else if (cost > 100f && cost <= 150f) {
                        total = cost - ((cost - prev) * (float)0.10);
                        Gold gold = (Gold)preferred[j];
                        preferred[j].setSpent(total);
                        gold.setPercentage(10);
                        break;
                    } else if (cost > 150f && cost <= 200f) {
                        total = cost - ((cost - prev) * (float)0.15);
                        Gold gold = (Gold)preferred[j];
                        preferred[j].setSpent(total);
                        gold.setPercentage(15);
                        break;
                    } else if (cost > 200f) {
                        total = cost - ((cost - prev) * (float)0.15);
                        if (total < 200f) {
                            Gold gold = (Gold) preferred[j];
                            preferred[j].setSpent(total);
                            gold.setPercentage(15);
                            break;
                        }
                        Object o = preferred[j];
                        if (o instanceof Platinum) {
                            Platinum platinum = (Platinum) o;
                            float remaining = (platinum.getSpent() - 200) % 5;
                            float rollOver = 5 - remaining;
                            if (rollOver == 5) {
                                rollOver = 0;
                            }
                            float increase = cost - platinum.getSpent() - platinum.getBonus() - rollOver;
                            float tempBucks = increase / 5;
                            int Bucks;
                            if (tempBucks > 0f && tempBucks < 1f) {
                                Bucks = 1;
                            } else if (cost < 205f) {
                                Bucks = 0;
                            } else {
                                Bucks = (int)tempBucks;
                                Bucks++;
                            }


                            if (rollOver == 0) {
                                Bucks--;
                            }
                            preferred[j].setSpent(cost - platinum.getBonus());
                            platinum.setBonus(Bucks);
                        } else {
                            float tempBucks = (total - 200) / 5;
                            int Bucks = (int) tempBucks;
                            preferred = appendPlatinum(preferred, preferred[j].getID(), preferred[j].getFirstName(), preferred[j].getLastName(), total, Bucks);
                            preferred[j] = null;
                        }
                        break;
                    }
                }
            }
        }


        if (regular != null) {
            for (int j = 0; j < regular.length; j++) {
                if (regular[j] == null) {
                    continue;
                }
                if (regular[j].getID().equals(orders[0])) {
                    if (orders[2].equals("punch")) {
                        first = costPunch * quantity;
                    } else if (orders[2].equals("tea")) {
                        first = costTea * quantity;
                    } else if (orders[2].equals("soda")) {
                        first = costSoda * quantity;
                    }

                    if (orders[1].equals("S")) {
                        finale = PI * diameterS * heightS * sqIn * quantity + ozS * first;
                        prev = regular[j].getSpent();
                        cost = regular[j].getSpent() + finale;
                    } else if (orders[1].equals("M")) {
                        finale = PI * diameterM * heightM * sqIn * quantity + ozM * first;
                        prev = regular[j].getSpent();
                        cost = regular[j].getSpent() + finale;
                    } else if (orders[1].equals("L")) {
                        finale = PI * diameterL * heightL * sqIn * quantity + ozL * first;
                        prev = regular[j].getSpent();
                        cost = regular[j].getSpent() + finale;
                    }

                    if (cost >= 50f && preferred == null) {
                        preferred = new Customer[0];
                    }

                    if (cost >= 50.0f && cost <= 100f) {
                        total = cost - ((cost - prev) * (float) 0.05);
                        preferred = appendGold(preferred, regular[j].getID(), regular[j].getFirstName(), regular[j].getLastName(), total, 5);
                        regular[j] = null;
                        break;
                    } else if (cost > 100f && cost <= 150f) {
                        total = cost - ((cost - prev) * (float) 0.10);
                        preferred = appendGold(preferred, regular[j].getID(), regular[j].getFirstName(), regular[j].getLastName(), total, 10);
                        regular[j] = null;
                        break;
                    } else if (cost > 150f && cost <= 200f) {
                        total = cost - ((cost - prev) * (float) 0.15);
                        System.out.println(regular[j].getFirstName() + " " + total);
                        preferred = appendGold(preferred, regular[j].getID(), regular[j].getFirstName(), regular[j].getLastName(), total, 15);
                        regular[j] = null;
                        break;
                    } else if (cost > 200f) {
                        total = cost - ((cost - prev) * (float) 0.15);
                        if (total < 200f) {
                            preferred = appendGold(preferred, regular[j].getID(), regular[j].getFirstName(), regular[j].getLastName(), total, 15);
                            regular[j] = null;
                            break;
                        }
                        float tempBonus = (total - 200) / 5;
                        int bonus;
                        bonus = (int) tempBonus;
                        preferred = appendPlatinum(preferred, regular[j].getID(), regular[j].getFirstName(), regular[j].getLastName(), cost, bonus);
                        regular[j] = null;
                        break;
                    }
                }
            }
        }
        return preferred;
    }

    public static Customer[] calculateReg(String[] orders, Customer[] regular) {
        float costSoda = 0.20f;
        float costTea = 0.12f;
        float costPunch = 0.15f;

        float diameterS = 4f;
        float heightS = 4.5f;
        float ozS = 12f;

        float diameterM = 4.5f;
        float heightM = 5.75f;
        float ozM = 20f;

        float diameterL = 5.5f;
        float heightL = 7f;
        float ozL = 32f;

        float PI = (float)Math.PI;
        float cost = 0f;
        float first = 0f;
        float finale;

        float sqIn = Float.parseFloat(orders[3]);
        float quantity = Float.parseFloat(orders[4]);

        for (int j = 0; j < regular.length; j++) {
            if (regular[j].getID().equals(orders[0])) {
                if (orders[2].equals("punch")) {
                    first = costPunch * quantity;
                } else if (orders[2].equals("tea")) {
                    first = costTea * quantity;
                } else if (orders[2].equals("soda")) {
                    first = costSoda * quantity;
                }

                if (orders[1].equals("S")) {
                    finale = PI * diameterS * heightS * sqIn * quantity + ozS * first;
                    cost = regular[j].getSpent() + finale;
                } else if (orders[1].equals("M")) {
                    finale = PI * diameterM * heightM * sqIn * quantity + ozM * first;
                    cost = regular[j].getSpent() + finale;
                } else if (orders[1].equals("L")) {
                    finale = PI * diameterL * heightL * sqIn * quantity + ozL * first;
                    cost = regular[j].getSpent() + finale;
                }

                System.out.println("COST3 " + cost);

                if (cost >= 50.0f && cost <= 100f) {
                    regular[j] = null;
                    break;
                } else if (cost > 100f && cost <= 150f) {
                    regular[j] = null;
                    break;
                } else if (cost > 150f && cost <= 200f) {
                    regular[j] = null;
                    break;
                } else if (cost > 200f) {
                    regular[j] = null;
                    break;
                } if (cost < 50f) {
                    regular[j].setSpent(cost);
                }
            }
        }
        return regular;
    }


    public static void printToFileRegular(Customer[] regular) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customer3.dat"))) {
            for (Customer customer : regular) {
                float tempSpent = customer.getSpent();
                String spent = String.format("%.02f", tempSpent);
                bw.write(customer.getID() + " " + customer.getFirstName() + " " + customer.getLastName() + " " + spent);
                bw.write("\n");
            } // Prints auditorium 2 to output file
        } catch(Exception ex) {
            System.out.print("Count5 Failed");
            System.exit(0);
        } // catches exception if output file fails to open
    }

    public static void printToFilePreferred(Customer[] preferred) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("preferred3.dat"))) {
            for (Object o : preferred) {
                if (o instanceof Gold) {
                    Gold gold = (Gold)o;
                    float tempSpent = gold.getSpent();
                    String spent = String.format("%.02f", tempSpent);
                    bw.write(gold.getID() + " " + gold.getFirstName() + " " + gold.getLastName() + " " + spent + " " + (int)gold.getPercentage() + "%");
                    bw.write("\n");
                }

                if (o instanceof Platinum) {
                    Platinum platinum = (Platinum)o;
                    float tempSpent = platinum.getSpent();
                    String spent = String.format("%.02f", tempSpent);
                    bw.write(platinum.getID() + " " + platinum.getFirstName() + " " + platinum.getLastName() + " " + spent + " " + platinum.getBonus());
                    bw.write("\n");
                }
            }

        } catch(Exception ex) {
            // System.out.print("Failed");
            System.exit(0);
        } // catches exception if output file fails to open
    }
}
