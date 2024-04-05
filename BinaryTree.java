

class BinaryTree<E extends Comparable<E>> {
    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E item) {
            data = item;
            left = right = null;
        }
    }

    private Node<E> root;

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
