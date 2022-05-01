/*
 * Philip Lee (phl200002)
 * CS 2336.001 Project 2
 * Main class
 */

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        String alphabet = "  ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Holds the alphabet to print and store alphabet columns
        int[] start = new int[2]; // Stores the starting row and starting column
        int row = 0; // How many rows the char[][] array has
        char seatLetter = 'a'; // Stores the seat letter
        int column = 0; // Stores the total amount of seats in auditorium
        int seatRow = 0; // Stores the row selected by user
        int numTotal; // Counts the total amount of tickets

        // Get file name
        Scanner input = new Scanner(System.in);
        System.out.print("Enter File Name: ");
        String fileName = input.nextLine();
        File files = new File(fileName);

        // Initialize auditorium object to zero
        Auditorium<Seat> auditorium1 = null;
        // Read file if the file exists
        if (files.exists()) {
            try (Scanner scan = new Scanner(new FileReader(fileName))) {
                String line = scan.nextLine();
                column = line.length(); // column size

                // Initialize first seat in auditorium and pass it to auditorium constructor
                Seat newSeat = new Seat(1, alphabet.charAt(0), line.charAt(0));
                auditorium1 = new Auditorium<>(null, null, null, newSeat, -1);

                Node<Seat> head1 = auditorium1.getFirst(); // Head Pointer for auditorium
                Node<Seat> tail = auditorium1.getFirst(); // Tail Pointer
                Node<Seat> downTail = auditorium1.getFirst(); // Down Tail Pointer

                // Create seats for the first row of auditorium
                for (int i = 1; i < line.length(); i++) {
                    newSeat = new Seat(1, alphabet.charAt(i + 2), line.charAt(i));
                    auditorium1 = new Auditorium<>(head1, tail, downTail, newSeat, i);
                    tail = tail.getNext();
                }
                row++;

                // Create seats for rest of auditorium
                while (scan.hasNextLine()) {
                    line = scan.nextLine();
                    newSeat = new Seat(row + 1, alphabet.charAt(0), line.charAt(0));
                    auditorium1 = new Auditorium<>(head1, tail, downTail, newSeat, 0);
                    downTail = downTail.getDown();
                    tail = downTail;
                    for (int i = 1; i < line.length(); i++) {
                        newSeat = new Seat(i + 1, alphabet.charAt(i + 2), line.charAt(i));
                        auditorium1 = new Auditorium<>(head1, tail, downTail, newSeat, i);
                        tail = tail.getNext();
                    }
                    row++;
                } // Reads file line by line
            } catch (Exception ex) {
                System.out.print("Failed to read from file");
                System.exit(0);
            } // Catches exception if the file fails to open
        }

        // If the file doesn't exist, quit the program
        else {
            System.exit(0);
        }

        // Create auditorium object to connect and store contents in the nodes
        Node<Seat> head = auditorium1.getFirst();

        while (true) {
            System.out.print("\n1. Reserve Seats\n2. Exit\n");
            int choice = 0; // Choice to reserve seats or exit program
            boolean needInput1 = true; // flag for "choice" variable
            while (needInput1) {
                try {
                    choice = input.nextInt();
                    if (choice == 1 || choice == 2) {
                        needInput1 = false;
                    } // Input Validation to make sure choices equals one or two only
                    else {
                        System.out.print("Try Again: ");
                    }
                }
                catch (InputMismatchException e) {
                    System.out.print("Has to be integer: ");
                    input.nextLine(); // Throw away incorrect input
                }
            } // Input Validation with Exception Handling

            if (choice == 1) {
                displayAuditorium(head, alphabet, column); // displays auditorium

                boolean needInput2 = true; // flag for "seatRow" variable
                System.out.print("Row: ");
                while (needInput2) {
                    try {
                        seatRow = input.nextInt(); // Inputs row to reserve spot
                        int countIfAvailable = 0; // Flag variable to make sure there's at least one seat available
                        if (seatRow > 0 && seatRow <= row) {
                            Node<Seat> curr = head; // Current node pointer
                            Node<Seat> vertical = head; // Vertical node pointer
                            for (int i = 0; i < seatRow - 1; i++) {
                                vertical = vertical.getDown();
                                curr = vertical;
                            } // Traverses down the auditorium

                            for (int j = 0; j < column; j++) {
                                if (curr.getPayload().getTicket() == '#') {
                                    countIfAvailable++;
                                }
                                curr = curr.getNext();
                            } // Checks to see if the specified seats are taken already in that row

                        }
                        else {
                            System.out.print("Try Again: ");
                            seatRow = input.nextInt(); // Inputs row to reserve spot
                            Node<Seat> curr = head;
                            Node<Seat> vertical = head;
                            for (int i = 0; i < seatRow - 1; i++) {
                                vertical = vertical.getDown();
                                curr = vertical;
                            } // Checks to see if the specified seats are taken already in that row

                            for (int j = 1; j < column; j++) {
                                if (Character.isLetter(curr.getPayload().getTicket())) {
                                    countIfAvailable++;
                                }
                            } // Checks to see if there are any seats available

                        }
                        if (countIfAvailable != column) {
                            needInput2 = false;
                        }
                        else {
                            System.out.println("seats not available");
                        } // Prints "seats not available" if all seats are taken
                    }
                    catch (InputMismatchException e) {
                        System.out.print("Has to be integer: ");
                        input.nextLine(); // Throw away incorrect input
                    }
                } // Input validation with exception handling

                boolean needInput3 = true; // Flag for "seatLetter" variable
                System.out.print("Starting Seat Letter: ");
                while (needInput3) {
                    try {
                        seatLetter = input.next().charAt(0); // Inputs the exact column of the auditorium
                        char seat = Character.toUpperCase(seatLetter); // Makes Everything Uppercase in case a lowercase is inputted
                        System.out.println();

                        int num = seat - 'A';
                        if (num >= 0 && num < column) {
                            needInput3 = false;
                        } // flag turns false if the seat is within range

                        else {
                            System.out.println("Try Again: ");
                        } // Prints try again if input not valid
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Has to be a character: ");
                        input.nextLine(); // Throw away incorrect input
                    }
                } // Input validation with exception handling

                int adultTickets = 0; // holds the number of adult tickets
                int childTickets = 0; // holds the number of child tickets
                int seniorTickets = 0; // holds the number of senior tickets

                boolean needInput4 = true; // Flag for the adult tickets
                System.out.print("Amount of Adult Tickets: ");
                while (needInput4) {
                    try {
                        adultTickets = input.nextInt(); // Inputs number of adult tickets
                        System.out.println();

                        if (adultTickets >= 0) {
                            needInput4 = false;
                        } // Adult tickets needs to be greater than zero
                    }
                    catch (InputMismatchException e) {
                        System.out.print("Has to be integer: ");
                        input.nextLine(); // Throw away incorrect input
                    }
                }

                boolean needInput5 = true; // Flag for the child tickets
                System.out.print("Amount of Child Tickets: ");
                while (needInput5) {
                    try {
                        childTickets = input.nextInt(); // Inputs number of child tickets
                        System.out.println();

                        if (childTickets >= 0) {
                            needInput5 = false;
                        } // Children ticket needs to be greater than zero
                    }
                    catch (InputMismatchException e) {
                        System.out.print("Has to be integer: ");
                        input.nextLine(); // Throw away incorrect input
                    }
                }

                boolean needInput6 = true; // Flag for the senior tickets
                System.out.print("Amount of Senior Tickets: ");
                while (needInput6) {
                    try {
                        seniorTickets = input.nextInt(); // Inputs number of senior tickets
                        System.out.println();

                        if (seniorTickets >= 0) {
                            needInput6 = false;
                        } // Senior ticket needs to be greater than zero
                    }
                    catch (InputMismatchException e) {
                        System.out.print("Has to be integer: ");
                        input.nextLine(); // Throw away incorrect input
                    }
                }

                numTotal = adultTickets + childTickets + seniorTickets; // Count the total number of tickets

                Node<Seat> curr; // Current pointer
                Node<Seat> vertical = head; // Vertical Pointer
                for (int i = 0; i < seatRow - 1; i++) {
                    vertical = vertical.getDown();
                } // Checks to see if all the seats are taken already in that row
                curr = vertical;

                char seat = Character.toUpperCase(seatLetter);
                int num = seat - 'A';

                for (int i = 0; i < num; i++) {
                    curr = curr.getNext();
                } // Checks to see if all the seats are taken already in that row

                boolean checkEmpty = true;
                if (numTotal > column) {
                    System.out.println("no seats available");
                    continue;
                } // If the number of total tickets is greater than the number per column return to main menu

                for (int j = 0; j < numTotal; j++) {
                    if (Character.isLetter(curr.getPayload().getTicket())) {
                        checkEmpty = false;
                    }
                    curr = curr.getNext();
                } // Check to see if specified seats are empty

                if (!checkEmpty) {
                    start = bestAvailable(head, row, column, numTotal); // Find the best available seats
                    if (start[0] == -1 && start[1] == -1) {
                        System.out.println("no seats available");
                        continue;
                    } // If the best seats available are taken return to menu

                    char pickSeats; // Holds Y/N
                    System.out.print("Best seats Y/N? ");
                    pickSeats = input.next().charAt(0); // Input Y/N to reserve best seats available

                    // Even if the user picks 'N' the seats that should've been taken will be displayed on console
                    if (pickSeats == 'N') {
                        if (numTotal == 0 || start[0] == -1 && start[1] == -1) {
                            System.out.println();
                        } // Prints nothing if there isn't any total number of reserved seats

                        if (numTotal == 1 && start[0] != -1 && start[1] != -1) {
                            System.out.print(start[0]);
                            System.out.println(alphabet.charAt(start[1] + 1));
                        } // Prints only the row and seat letter taken for one person if there's one reserved seat

                        if (numTotal > 1 && start[0] != -1 && start[1] != -1) {
                            System.out.print(start[0]);
                            System.out.print(alphabet.charAt(start[1] + 1) +  " - ");
                            System.out.print(start[0]);
                            System.out.println(alphabet.charAt(start[1] + numTotal));
                        } // Prints a range of seats taken if there's more than one person
                        continue;
                    } // Return to menu if the user picks 'N'

                    reserveSeats(head, adultTickets, seniorTickets, childTickets, start[0], start[1]); // Reserve the following seats
                } // If statement operates when the specified seats are taken
                else {
                    start[0] = seatRow;
                    start[1] = seatLetter - 'A' + 1;

                    reserveSeats(head, adultTickets, seniorTickets, childTickets, start[0], start[1]);
                } // Else statement operates when the seats specified aren't taken

                // If the user picks 'Y' the seats taken will be displayed on the console
                if (numTotal == 0 || start[0] == -1 && start[1] == -1) {
                    System.out.println();
                } // Prints nothing if there isn't any total number of reserved seats

                if (numTotal == 1 && start[0] != -1 && start[1] != -1) {
                    System.out.print(start[0]);
                    System.out.println(alphabet.charAt(start[1] + 1));
                } // Prints only the row and seat letter taken for one person if there's one reserved seat

                if (numTotal > 1 && start[0] != -1 && start[1] != -1) {
                    System.out.print(start[0]);
                    System.out.print(alphabet.charAt(start[1] + 1) +  " - ");
                    System.out.print(start[0]);
                    System.out.println(alphabet.charAt(start[1] + numTotal));
                } // Prints a range of seats taken if there's more than one person
            }

            if (choice == 2) {
                displayReport(head, row, column); // Displays final report

                // Below will output to "A1.txt" file
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("A1.txt"))) {
                    Node<Seat> curr = head; // Current Pointer
                    Node<Seat> vertical = head; // Vertical Pointer
                    while (vertical != null) {
                        while (curr != null) {
                            bw.write(curr.getPayload().getTicket());
                            curr = curr.getNext();
                        }
                        bw.write("\n");
                        vertical = vertical.getDown();
                        curr = vertical;
                    }
                } catch(Exception ex) {
                    System.out.print("Failed");
                    System.exit(0);
                } // catches exception if output file fails to open
                break; // Break out of while(true) loop and end the program
            }
        }
    }

    // This method displays the auditorium using the parameters Node<Seat> head, String alphabet and int column
    public static void displayAuditorium(Node<Seat> head, String alphabet, int column) {
        for (int i = 0; i < column + 2; i++) {
            System.out.print(alphabet.charAt(i));
        }
        System.out.println();
        Node<Seat> curr = head; // Current Pointer
        Node<Seat> vertical = head; // Vertical Pointer
        int count = 1;
        while (vertical != null) {
            System.out.print(count++ + " ");
            for (int i = 0; i < column; i++) {
                if (Character.isLetter(curr.getPayload().getTicket())) {
                    System.out.print('#');
                } else {
                    System.out.print(curr.getPayload().getTicket());
                }
                curr = curr.getNext();
            }
            System.out.println();
            vertical = vertical.getDown();
            curr = vertical;
        }
    }

    /*
       This method calculates and returns the row and column of the best starting seat found.
       The parameters were Node<Seat> head, int row, int column, int total
    */
    public static int[] bestAvailable(Node<Seat> head, int row, int column, int total) {
        double middleRow = (row + 1) / 2.0; // Middle Row of auditorium
        double middleColumn = (column + 1) / 2.0; // Middle Column of auditorium
        ArrayList<double[]> same = new ArrayList<>(); // Stores all available seats
        int[] keep = new int[2]; // Stores starting row and column
        Node<Seat> current = head; // Current Pointer
        Node<Seat> vertical = head; // Vertical Pointer
        Node<Seat> temporary; // Temporary Pointer for "sliding window"
        double min = Double.MAX_VALUE; // Minimum distance from center

        /* Below uses "sliding window" technique to find the best available seats. It calculates and stores the
           minimum distance using the starting row and column to be returned from the method
        */
        for (int i = 1; i < row + 1; i++) {
            for (int j = 1; j < column - total + 2; j++) {
                temporary = current;
                for (int k = j; k < j + total; k++) {
                    if (Character.isLetter(temporary.getPayload().getTicket())) {
                        break;
                    }
                    double mid = j + (total - 1) / 2.0;
                    if (k == (j + total - 1)) {
                        double temp = 0.0;
                        if (Math.abs(middleRow - i) > 0.0 && Math.abs(middleColumn - mid) == 0.0) {
                            temp = Math.abs(middleRow - i);
                        } // Only stores in temp when Math.abs(middleRow - i) > 0.0 && Math.abs(middleColumn - mid) == 0.0
                        else if (Math.abs(middleRow - i) == 0.0 && Math.abs(middleColumn - mid) > 0.0){
                            temp = Math.abs(middleColumn - mid);
                        } // Only stores in temp when Math.abs(middleRow - i) == 0.0 && Math.abs(middleColumn - mid) > 0.0
                        else if (Math.abs(middleRow - i) > 0.0 && Math.abs(middleColumn - mid) > 0.0){
                            temp = Math.hypot(middleRow - i, middleColumn - mid);
                        } // Only stores in temp when Math.abs(middleRow - i) > 0.0 && Math.abs(middleColumn - mid) > 0.0

                        if (min >= temp) {
                            min = temp;
                        }

                        /* Below stores row, column, and distance from middle
                           in array and then stores the array in the array list */
                        double[] arr = new double[3];
                        arr[0] = i;
                        arr[1] = j;
                        arr[2] = temp;
                        same.add(arr);
                    }
                    temporary = temporary.getNext();
                }
                current = current.getNext();
            }
            vertical = vertical.getDown();
            current = vertical;
        }

        // Below ArrayList is used to find tied distance from middle of auditorium
        ArrayList<double[]> tieDistance = new ArrayList<>();
        if (min == Integer.MAX_VALUE) {
            keep[0] = -1;
            keep[1] = -1;
        } // If there are no best available seats found return -1 for keep[0] and keep[1]

        /*
           This else statement takes care of same distance and same row distance from middle of
           auditorium
        */
        else {
            for (double[] temp : same) {
                if (min == temp[2]) {
                    // Below array stores row and column for any row from middle of auditorium that's tied
                    double[] arr = new double[2];
                    arr[0] = temp[0];
                    arr[1] = temp[1];
                    tieDistance.add(arr);
                }
            }

            // Below ArrayList stores the row and column for the row that's "lower"
            ArrayList<double[]> closestToCenter = new ArrayList<>();

            // Below stores the best seats available
            ArrayList<Integer> correct = new ArrayList<>();
            if (tieDistance.size() > 1) {
                double minDistance = Double.MAX_VALUE;
                for (double[] temp : tieDistance) {
                    double closest = Math.abs(middleRow - temp[0]);
                    minDistance = Math.min(closest, minDistance);
                }

                for (double[] temp : tieDistance) {
                    double closest = Math.abs(middleRow - temp[0]);
                    if (minDistance == closest)  {
                        closestToCenter.add(temp);
                    }
                }

                if (closestToCenter.size() > 1) {
                    double less = Double.MIN_VALUE;
                    for (double[] temp : closestToCenter) {
                        double lesser = middleRow - temp[0];
                        less = Math.max(lesser, less);
                    }

                    for (double[] temp : closestToCenter) {
                        double lesser = middleRow - temp[0];
                        if (lesser == less) {
                            correct.add((int)temp[0]);
                            correct.add((int)temp[1]);
                        }
                    }
                }
                else {
                    // Stores the best possible seat
                    double[] temp = closestToCenter.get(0);
                    correct.add((int)temp[0]);
                    correct.add((int)temp[1]);
                }
            }
            else {
                double[] temp = tieDistance.get(0);
                correct.add((int)temp[0]);
                correct.add((int)temp[1]);
            }

            // stores the best possible seats to keep array and returns the array
            keep[0] = correct.get(0);
            keep[1] = correct.get(1);
        }

        return keep;
    }

    /*
       Reserve the best available seats found as well as the specified seats if those seats are available.
       Node<Seat> head1, int adult, int senior, int child, int row and int column were used as parameters
    */
    public static void reserveSeats(Node<Seat> head1, int adult, int senior, int child, int row, int column) {
        Node<Seat> curr;
        Node<Seat> vertical = head1;
        for (int i = 1; i < row; i++) {
            vertical = vertical.getDown();
        } // Checks to see if all the seats are taken already in that row
        curr = vertical;

        for (int i = 1; i < column; i++) {
            curr = curr.getNext();
        } // Goes to the correct column in the row
        
        while (adult != 0) {
            curr.getPayload().setTicket('A');
            curr = curr.getNext();
            adult--;
        } // Enters adult tickets

        while (child != 0) {
            curr.getPayload().setTicket('C');
            curr = curr.getNext();
            child--;
        } // Enters child tickets

        while (senior != 0) {
            curr.getPayload().setTicket('S');
            curr = curr.getNext();
            senior--;
        } // Enters Senior Tickets
    }

    /*
       This method displays the final report at the end of the program. The parameters
       are char[][] auditorium2, int row, and int column.
    */
    public static void displayReport(Node<Seat> head, int row, int column) {
        int total; // Total number of tickets
        int adult = 0; // Holds number of adults
        int child = 0; // Holds number of children
        int senior = 0; // Holds number of seniors
        double sales; // Holds number of sales

        Node<Seat> current = head; // Current Pointer
        Node<Seat> vertical = head; // Vertical Pointer
        // Below counts all the tickets in the auditorium
        while (vertical != null) {
            while (current != null) {
                if (current.getPayload().getTicket() == 'A') {
                    adult++;
                }

                if (current.getPayload().getTicket() == 'C') {
                    child++;
                }

                if (current.getPayload().getTicket() == 'S') {
                    senior++;
                }
                current = current.getNext();
            }
            vertical = vertical.getDown();
            current = vertical;
        }

        total = adult + child + senior; // Adds everything up
        sales = adult * 10.0 + child * 5.0 + senior * 7.5; // Find total sales

        // Print out report below
        System.out.println("Total Seats:    " + (row * column));
        System.out.println("Total Tickets:  " + total);
        System.out.println("Adult Tickets:  " + adult);
        System.out.println("Child Tickets:  " + child);
        System.out.println("Senior Tickets: " + senior);
        System.out.print("Total Sales:    $");
        System.out.printf("%.2f", sales);
    }
}
