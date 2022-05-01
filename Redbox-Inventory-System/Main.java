/*
   Philip Lee (phl200002)
   Project 3
   Main Class
*/

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // For input and file reading
        System.out.print("Inventory File: ");
        String inventory = input.nextLine(); // Input inventory file
        File file1 = new File(inventory); // file1 object

        BinTree<DVD> tree = new BinTree<>(); // Make tree

        // Check if file exists
        if (file1.exists()) {
            try (Scanner scan = new Scanner(new File(inventory))){
                String line = scan.nextLine(); // Gets each line in file
                String[] arr = line.split(","); // Stores all content in array
                String temp = arr[0]; // Title
                String title = temp.substring(1, temp.length() - 1); // Takes quotations off
                int available = Integer.parseInt(arr[1]); // available
                int rented = Integer.parseInt(arr[2]); // rented

                DVD dvd = new DVD(title, available, rented); // Makes new DVD node
                Node<DVD> node = new Node<>(dvd); // Sets DVD node to payload
                tree = new BinTree<>(node); // Sets new node to root of tree

                // Do same thing for the rest of file
                while (scan.hasNextLine()) {
                    line = scan.nextLine();
                    String[] array = line.split(",");
                    temp = array[0];
                    title = temp.substring(1, temp.length() - 1);
                    available = Integer.parseInt(array[1]);
                    rented = Integer.parseInt(array[2]);

                    DVD dvds = new DVD(title, available, rented);
                    Node<DVD> nodes = new Node<>(dvds);
                    Node<DVD> first = tree.getRoot();
                    tree.insert(nodes, first);
                }
            } catch (Exception ex) {
                System.out.println("Inventory Failed to Open");
                System.exit(0);
            } // Error if file fails to either open or read from file
        }
        else {
            System.out.println("Inventory file doesn't exists");
            System.exit(0);
        } // Error if file doesn't exist

        System.out.println("Transactions Log: ");
        String transactions = input.nextLine(); // Input transaction log
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        File file2 = new File(transactions); // file2 object

        // If file2 exists
        if (file2.exists()) {
            try (Scanner scan = new Scanner(new File(transactions))){
                while (scan.hasNextLine()) {
                    String line = scan.nextLine(); // Gets file in each line
                    String[] arr = line.split(","); // Stores all contents in array
                    String temp = arr[0]; // Either rent, remove, add, or return
                    String[] array = temp.split(" "); // Split the the title and number returned or added
                    String concatenate = array[1]; // Used to concatenate string

                    // Concatenates string
                    if (array.length > 2) {
                        for (int i = 2; i < array.length; i++) {
                            concatenate += " " + array[i];
                        }
                    }

                    String title = concatenate.substring(1, concatenate.length() - 1); // Quotation marks taken out of title
                    ArrayList<String> temporary = new ArrayList<>(); // Stores all contents in arrayList
                    temporary.add(array[0]);
                    temporary.add(title);
                    if (arr.length == 2) {
                        temporary.add(arr[1]);
                    }
                    list.add(temporary); // adds arrayList to another arrayList
                }
            } catch (Exception ex) {
                System.out.println("Transactions Failed to Open");
                System.exit(0);
            } // Error if file fails to open
        }
        else {
            System.out.println("Transaction file doesn't exists");
            System.exit(0);
        } // Error if file doesn't exist

        allTransactions(list, tree); // Processes all transactions that are put in 2D arrayList
        Node<DVD> root = tree.getRoot(); // Stores root of tree
        print(root); // Print inorder traversal to display alphabetically
    }

    /*
      Print inorder traversal to display alphabetically
    */
    public static void print(Node<DVD> root) {
        if (root == null) {
            return;
        }
        print(root.getLeft());
        System.out.println(root);
        print(root.getRight());
    }

    /*
       This method goes through all transactions stored in 2D arrayList
    */
    public static void allTransactions(ArrayList<ArrayList<String>> list, BinTree<DVD> tree) {
        // Goes through whole 2D arrayList
        for (ArrayList<String> strings : list) {
            Node<DVD> root;
            // "Rents" for a particular node
            if (strings.get(0).equals("rent")) {
                DVD node = new DVD(strings.get(1), 0, 0); // Dummy payload to find specific title
                Node<DVD> nodes = new Node<>(node); // Stores dummy payload in dummy node
                root = tree.search(nodes, tree.getRoot()); // Searches for specific node

                // Makes sure node isn't null before making transaction
                if (root != null) {
                    int available = root.getPayload().getAvailable();
                    int rented = root.getPayload().getRented();
                    available--;
                    rented++;
                    root.getPayload().setAvailable(available);
                    root.getPayload().setRented(rented);
                }
            }

            // "Returns" for a particular node
            else if (strings.get(0).equals("return")) {
                DVD node = new DVD(strings.get(1), 0, 0); // Dummy payload to find specific title
                Node<DVD> nodes = new Node<>(node); // Stores dummy payload in dummy node
                root = tree.search(nodes, tree.getRoot()); // Searches for specific node

                // Makes sure node isn't null before making transaction
                if (root != null) {
                    int available = root.getPayload().getAvailable();
                    int rented = root.getPayload().getRented();
                    available++;
                    rented--;
                    root.getPayload().setAvailable(available);
                    root.getPayload().setRented(rented);
                }
            }

            // "Adds" for a particular node
            else if (strings.get(0).equals("add")){
                DVD node = new DVD(strings.get(1), 0, 0); // Dummy payload to find specific title
                Node<DVD> nodes = new Node<>(node);// Stores dummy payload in dummy node
                root = tree.search(nodes, tree.getRoot()); // Searches for specific node

                // Makes sure node isn't null before making transaction
                if (root == null) {
                    String title = strings.get(1);
                    int available = Integer.parseInt(strings.get(2));
                    DVD dvd = new DVD(title, available, 0);
                    Node<DVD> temp = new Node<>(dvd);
                    tree.insert(temp, tree.getRoot());
                }

                // If it is null add new node to tree
                else {
                    int available = root.getPayload().getAvailable();
                    available += Integer.parseInt(strings.get(2));
                    root.getPayload().setAvailable(available);
                }
            }

            // "Removes" for a particular node
            else if (strings.get(0).equals("remove")) {
                DVD node = new DVD(strings.get(1), 0, 0);// Dummy payload to find specific title
                Node<DVD> nodes = new Node<>(node);// Stores dummy payload in dummy node
                root = tree.search(nodes, tree.getRoot()); // Searches for specific node

                // Makes sure node isn't null before making transaction
                if (root != null) {
                    int remove = root.getPayload().getAvailable();
                    remove -= Integer.parseInt(strings.get(2));

                    // Removes node if both "rent" and "available" are both zero
                    if (remove <= 0 && root.getPayload().getRented() == 0) {
                        tree.delete(root, tree.getRoot(), null);
                    }

                    // Removes from "available" if "rent" and available are both above zero or if only rent is above zero
                    else if (remove >= 0 && root.getPayload().getRented() >= 0){
                        root.getPayload().setAvailable(remove);
                    }
                }
            }
        }
    }
}
