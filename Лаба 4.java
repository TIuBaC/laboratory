```java
import java.util.*;

public class BinaryTreesLab5 {

    static class Node {
        int data;
        Node left, right;
        Node(int value) { data = value; }
    }

    static void preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    static void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    static void postOrder(Node node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    static void levelOrder(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.data + " ");
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    static int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static boolean isFull(Node node) {
        if (node == null) return true;
        if ((node.left == null) ^ (node.right == null)) return false;
        return isFull(node.left) && isFull(node.right);
    }

    static Node buildFullTreeMinHeight3() {
        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4),
             n5 = new Node(5), n6 = new Node(6), n7 = new Node(7);
        n1.left = n2; n1.right = n3;
        n2.left = n4; n2.right = n5;
        n3.left = n6; n3.right = n7;
        return n1;
    }

    static Node insertBST(Node root, int value) {
        if (root == null) return new Node(value);
        if (value < root.data) root.left = insertBST(root.left, value);
        else root.right = insertBST(root.right, value);
        return root;
    }

    static Node buildBSTFromSequence(int[] sequence) {
        Node root = null;
        for (int value : sequence) root = insertBST(root, value);
        return root;
    }

    static Node buildBalancedFromSorted(int[] array) {
        return buildBalancedRec(array, 0, array.length - 1);
    }

    static Node buildBalancedRec(int[] array, int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        Node node = new Node(array[mid]);
        node.left = buildBalancedRec(array, left, mid - 1);
        node.right = buildBalancedRec(array, mid + 1, right);
        return node;
    }

    static class LevelOrderTree {
        Node root;

        void insert(int value) {
            Node newNode = new Node(value);
            if (root == null) { 
                root = newNode; 
                return; 
            }
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node current = queue.poll();
                if (current.left == null) { 
                    current.left = newNode; 
                    return; 
                } else {
                    queue.add(current.left);
                }
                if (current.right == null) { 
                    current.right = newNode; 
                    return; 
                } else {
                    queue.add(current.right);
                }
            }
        }

        boolean delete(int key) {
            if (root == null) return false;
            
            if (root.left == null && root.right == null) {
                if (root.data == key) {
                    root = null;
                    return true;
                }
                return false;
            }

            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node targetNode = null;
            Node current = null;
            
            while (!queue.isEmpty()) {
                current = queue.poll();
                if (current.data == key) targetNode = current;
                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }

            if (targetNode == null) return false;
            
            int deepestValue = current.data;
            targetNode.data = deepestValue;
            deleteDeepest(root, current);
            return true;
        }

        private void deleteDeepest(Node root, Node deepestNode) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            
            while (!queue.isEmpty()) {
                Node current = queue.poll();
                
                if (current.left != null) {
                    if (current.left == deepestNode) {
                        current.left = null;
                        return;
                    } else {
                        queue.add(current.left);
                    }
                }
                
                if (current.right != null) {
                    if (current.right == deepestNode) {
                        current.right = null;
                        return;
                    } else {
                        queue.add(current.right);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Node full = buildFullTreeMinHeight3();
        System.out.print("preOrder: "); preOrder(full); System.out.println();
        System.out.print("inOrder: "); inOrder(full); System.out.println();
        System.out.print("postOrder: "); postOrder(full); System.out.println();
        System.out.print("levelOrder: "); levelOrder(full); System.out.println();
        System.out.println("height: " + height(full));
        System.out.println("isFull: " + isFull(full));

        int[] sequence = {8, 3, 10, 1, 6, 14, 4, 7, 13};
        Node bst = buildBSTFromSequence(sequence);
        System.out.print("BST inOrder: "); inOrder(bst); System.out.println();
        System.out.print("BST levelOrder: "); levelOrder(bst); System.out.println();
        System.out.println("BST height: " + height(bst));

        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Node balanced = buildBalancedFromSorted(sorted);
        System.out.print("balanced levelOrder: "); levelOrder(balanced); System.out.println();
        System.out.println("balanced height: " + height(balanced));
        System.out.println("balanced isFull: " + isFull(balanced));

        LevelOrderTree lot = new LevelOrderTree();
        int[] toInsert = {1, 2, 3, 4, 5, 6, 7, 8};
        for (int value : toInsert) lot.insert(value);
        System.out.print("levelOrder after inserts: "); levelOrder(lot.root); System.out.println();
        System.out.println("delete 3: " + lot.delete(3));
        System.out.print("levelOrder after delete: "); levelOrder(lot.root); System.out.println();
        System.out.println("delete 100: " + lot.delete(100));
    }
}
