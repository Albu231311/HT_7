import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    
    // Definición de la clase Node
    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E item) {
            data = item;
            left = right = null;
        }
    }
    
    // Definición de la clase BinaryTree
    private static class BinaryTree<E extends Comparable<E>> {
        Node<E> root;

        BinaryTree() {
            root = null;
        }

        void insert(E item) {
            root = insertRec(root, item);
        }

        private Node<E> insertRec(Node<E> root, E item) {
            if (root == null) {
                root = new Node<>(item);
                return root;
            }

            if (item.compareTo(root.data) < 0)
                root.left = insertRec(root.left, item);
            else if (item.compareTo(root.data) > 0)
                root.right = insertRec(root.right, item);

            return root;
        }

        void inorder() {
            inorderRec(root);
        }

        private void inorderRec(Node<E> root) {
            if (root != null) {
                inorderRec(root.left);
                System.out.print(root.data + " ");
                inorderRec(root.right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree<Association<String, String>> englishTree = new BinaryTree<>();
        BinaryTree<Association<String, String>> frenchTree = new BinaryTree<>();
        BinaryTree<Association<String, String>> spanishTree = new BinaryTree<>();

        // Leer el archivo de diccionario y construir los árboles de búsqueda
        readDictionary("diccionario.txt", englishTree, frenchTree, spanishTree);

        // Imprimir los árboles de búsqueda
        System.out.println("In-order traversal of English tree:");
        englishTree.inorder();
        System.out.println();

        System.out.println("In-order traversal of French tree:");
        frenchTree.inorder();
        System.out.println();

        System.out.println("In-order traversal of Spanish tree:");
        spanishTree.inorder();
        System.out.println();

        // Traducir el texto y mostrarlo
        translateText("texto.txt", englishTree);
    }

    public static void readDictionary(String filename, BinaryTree<Association<String, String>> englishTree,
            BinaryTree<Association<String, String>> frenchTree, BinaryTree<Association<String, String>> spanishTree) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Association<String, String> association = new Association<>(parts[0], parts[1]);
                englishTree.insert(association);
                frenchTree.insert(new Association<>(parts[2], parts[0]));
                spanishTree.insert(new Association<>(parts[1], parts[0]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the dictionary file.");
            e.printStackTrace();
        }
    }

    public static void translateText(String filename, BinaryTree<Association<String, String>> tree) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split(" ");
                for (String word : words) {
                    String translatedWord = findTranslation(word, tree);
                    System.out.print(translatedWord + " ");
                }
                System.out.println();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the text file.");
            e.printStackTrace();
        }
    }

    public static String findTranslation(String word, BinaryTree<Association<String, String>> tree) {
        String lowerCaseWord = word.toLowerCase();
        Association<String, String> association = new Association<>(lowerCaseWord, lowerCaseWord);
        Node<Association<String, String>> node = findRec(tree.root, association);
        if (node != null) {
            return node.data.getValue();
        } else {
            return "*" + word + "*";
        }
    }

    private static Node<Association<String, String>> findRec(Node<Association<String, String>> root,
            Association<String, String> item) {
        if (root == null || root.data.compareTo(item) == 0)
            return root;

        if (item.compareTo(root.data) < 0)
            return findRec(root.left, item);

        return findRec(root.right, item);
    }
}
