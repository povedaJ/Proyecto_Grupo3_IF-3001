package domain;

public class BSTTree {
    public static class Node {
        int key;
        Object value;
        Node left;
        Node right;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;

    public void insert(int key, Object value) {
        root = insertNode(root, key, value);
    }

    private Node insertNode(Node node, int key, Object value) {
        if (node == null) {
            return new Node(key, value);
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key, value);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value);
        }

        return node;
    }

    public Node[] getTopKNodes(int k) {
        Node[] topNodes = new Node[k];
        getTopKNodesUtil(root, topNodes, 0, k);
        return topNodes;
    }

    private int getTopKNodesUtil(Node node, Node[] topNodes, int index, int k) {
        if (node == null || index >= k) {
            return index;
        }

        index = getTopKNodesUtil(node.right, topNodes, index, k);
        if (index < k) {
            topNodes[index++] = node;
            index = getTopKNodesUtil(node.left, topNodes, index, k);
        }

        return index;
    }
}

