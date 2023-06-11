package domain;

public class AVLTree {
    private Node root;

    public boolean isEmpty() {
        return false;
    }

    public Node getRoot() {
        return root;
    }

    public class Node {
        public Proveedor proveedor;
        public Node left;
        public Node right;
        private int height;

        public Node(Proveedor proveedor) {
            this.proveedor = proveedor;
            this.height = 1;
        }
    }

    // Método para obtener la altura de un nodo
    private int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Método para obtener el balance de un nodo
    private int getBalance(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Método para realizar una rotación simple a la derecha
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Realizar la rotación
        x.right = y;
        y.left = T2;

        // Actualizar las alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Método para realizar una rotación simple a la izquierda
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Realizar la rotación
        y.left = x;
        x.right = T2;

        // Actualizar las alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Método para insertar un proveedor en el árbol AVL
    public void insert(Proveedor proveedor) {
        root = insertNode(root, proveedor);
    }

    private Node insertNode(Node node, Proveedor proveedor) {
        if (node == null)
            return new Node(proveedor);

        if (proveedor.getId() < node.proveedor.getId())
            node.left = insertNode(node.left, proveedor);
        else if (proveedor.getId() > node.proveedor.getId())
            node.right = insertNode(node.right, proveedor);
        else
            return node; // No se permiten duplicados

        // Actualizar la altura del nodo actual
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Obtener el factor de balance del nodo actual
        int balance = getBalance(node);

        // Realizar las rotaciones si el nodo se desbalancea
        if (balance > 1 && proveedor.getId() < node.left.proveedor.getId())
            return rotateRight(node);

        if (balance < -1 && proveedor.getId() > node.right.proveedor.getId())
            return rotateLeft(node);

        if (balance > 1 && proveedor.getId() > node.left.proveedor.getId()) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && proveedor.getId() < node.right.proveedor.getId()) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Método para buscar un proveedor por su ID en el árbol AVL
    public Proveedor search(int id) {
        Node node = searchNode(root, id);
        if (node != null)
            return node.proveedor;
        return null;
    }

    private Node searchNode(Node node, int id) {
        if (node == null || node.proveedor.getId() == id)
            return node;

        if (id < node.proveedor.getId())
            return searchNode(node.left, id);
        else
            return searchNode(node.right, id);
    }

    // Método para eliminar un proveedor por su ID en el árbol AVL
    public void delete(int id) {
        root = deleteNode(root, id);
    }

    private Node deleteNode(Node node, int id) {
        if (node == null)
            return node;

        if (id < node.proveedor.getId())
            node.left = deleteNode(node.left, id);
        else if (id > node.proveedor.getId())
            node.right = deleteNode(node.right, id);
        else {
            if (node.left == null || node.right == null) {
                Node temp = null;
                if (temp == node.left)
                    temp = node.right;
                else
                    temp = node.left;

                if (temp == null) {
                    temp = node;
                    node = null;
                } else
                    node = temp;
            } else {
                Node temp = minValueNode(node.right);
                node.proveedor = temp.proveedor;
                node.right = deleteNode(node.right, temp.proveedor.getId());
            }
        }

        if (node == null)
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }
    public void update(int id, String nombre) {
        Node node = searchNode(root, id);
        if (node != null) {
            node.proveedor.setNombre(nombre);
        }
    }
}


