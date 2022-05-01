/*
   Philip Lee (phl200002)
   Project 3
   BinTree Class
*/

public class BinTree <Type extends Comparable<Type>> {
    private Node<Type> root = null; // root node of tree

    // Constructor
    public BinTree() {}

    // Overloaded Constructor
    public BinTree(Node<Type> r) {
        this.root = r;
    }

    // Getter Method for "root"
    public Node<Type> getRoot() {
        return root;
    }

    // Setter Method for "root"
    public void setRoot(Node<Type> r) {
        this.root = r;
    }

    /*
        This search() method searches for a particular node in a tree
    */
    public Node<Type> search(Node<Type> find, Node<Type> curr)
    {
        Node<Type> nodes; // Temporarily stores recursive calls

        // Return null if no node is found
        if (curr == null) {
            return null;
        }

        // Return the current node if found
        if (find.compareTo(curr) == 0) {
            return curr;
        }

        // Goes to left child node if "find" is less than "curr"
        else if(find.compareTo(curr) < 0) {
            nodes = search(find, curr.getLeft());
        }

        // Goes to right child node if "find" is more than "curr"
        else {
            nodes = search(find, curr.getRight());
        }

        return nodes;
    }

    /*
       This method inserts a node to the tree
    */
    public void insert(Node<Type> newNode, Node<Type> curr) {
        // Goes to left node if the "newNode" is less than "curr"
        if (newNode.compareTo(curr) < 0) {
            // Recursively calls again if left node isn't empty
            if (curr.getLeft() != null) {
                insert(newNode, curr.getLeft());
            }

            // Inserts to next left node if the left node is empty
            else {
                curr.setLeft(newNode);
            }
        }

        // Goes to left node if the "newNode" is less than "curr"
        else if (newNode.compareTo(curr) > 0) {
            // Recursively calls again if right node isn't empty
            if (curr.getRight() != null) {
                insert(newNode, curr.getRight());
            }

            // Inserts to next left node if the right node is empty
            else {
                curr.setRight(newNode);
            }
        }
    }


    /*
      This method finds the "max" node that can replace the node that's being deleted
    */
    public Node<Type> maxElement(Node<Type> curr) {
        if (curr.getRight() == null) {
            return curr;
        }
        return maxElement(curr.getRight());
    }

    /*
      This method finds the node that is before the "max" node
    */
    public Node<Type> findNode(Node<Type> curr, Node<Type> minNode) {
        if (curr.getLeft() == minNode || curr.getRight() == minNode || curr.getRight() == null) {
            return curr;
        }
        return findNode(curr.getRight(), minNode);
    }

    /*
       This method deletes a node from the tree
    */
    public Node<Type> delete(Node<Type> newNode, Node<Type> curr, Node<Type> parent) {
        // Return null if tree is empty
        if (curr == null) {
            return null;
        }

        // Goes left if "newNode" is less than "curr"
        if (newNode.compareTo(curr) < 0) {
            parent = curr;
            curr = delete(newNode, curr.getLeft(), parent);
        }

        // Goes right if "newNode" is less than "curr"
        else if (newNode.compareTo(curr) > 0) {
            parent = curr;
            curr = delete(newNode, curr.getRight(), parent);

        }

        // Else statement operates when newNode.compareTo(curr) == 0
        else {
            // Applies for zero children
            if(curr.getLeft() == null && curr.getRight() == null) {
                // Check if single node tree
                if (parent == null) {
                    root = null;
                }

                // Check left side of parent
                else if(parent.getLeft() == curr) {
                    parent.setLeft(null);
                }

                // Check right side of parent
                else {
                    parent.setRight(null);
                }
            }
            // Applies if node have two children
            else if (curr.getLeft() != null && curr.getRight() != null) {
                Node<Type> temp = curr; // Used to find "max" node from left subtree from root
                parent = curr; // For previous node to "max"

                // Finding minimum element from right
                Node<Type> maxNodeForLeft = maxElement(temp.getLeft());

                // Find the previous node from the minimum element from right
                if (parent.getLeft() != maxNodeForLeft) {
                    parent = findNode(parent.getLeft(), maxNodeForLeft);
                }

                // Replacing current node with minimum node from right subtree
                temp.setPayload(maxNodeForLeft.getPayload());

                // If the node found only has one left child one
                if (parent.getLeft() == maxNodeForLeft && maxNodeForLeft.getLeft() == null) {
                    parent.setLeft(null);
                }

                // If the node found has more than one left child nodes
                else if (parent.getLeft() == maxNodeForLeft && maxNodeForLeft.getLeft() != null) {
                    parent.setLeft(maxNodeForLeft.getLeft());
                }

                // If the node has both left and right child nodes
                else if (maxNodeForLeft.getLeft() != null) {
                    parent.setRight(maxNodeForLeft.getLeft());
                    maxNodeForLeft.setLeft(null);
                }

                // Otherwise disconnect nodes
                else {
                    parent.setRight(null);
                }
            }

            // If nodeToBeDeleted has only one child
            else {
                // Delete root with left child
                if (parent == null && curr.getLeft() == null)  {
                    root = curr.getRight();   //change root
                    curr.setRight(null);      //disconnect
                }

                // Delete root with right child
                else if (parent == null)  {
                    root = curr.getLeft();
                    curr.setLeft(null);
                }

                // Delete node with more than one left children
                else if (parent.getLeft() == curr && curr.getLeft() != null)  {
                    parent.setLeft(curr.getLeft());
                    curr.setLeft(null);
                }

                // Delete node with one left children
                else if (parent.getLeft() == curr)  {
                    parent.setLeft(curr.getRight());
                    curr.setRight(null);
                }

                // Delete node with more than one right child
                else if (parent.getRight() == curr && curr.getLeft() != null) {
                    parent.setRight(curr.getLeft());
                    curr.setLeft(null);
                }

                // Delete node with one right child
                else {
                    parent.setRight(curr.getRight());
                    curr.setRight(null);
                }
            }
        }
        return curr;
    }
}
