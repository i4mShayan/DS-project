public class TrieTree {
    final int SIZE;
    Node root;

    TrieTree(int SIZE) {
        this.SIZE = SIZE;
        root = new Node();
    }

    class Node {
        boolean isEndOfName;
        boolean isInLib;
        long count;
        Node[] children;
        TrieTree sub_feature;
        long sub_feature_count;
        TrieTree sub_feature_history;
        long sub_feature_history_count;
        LongList in_time, out_time, sum_time;

        Node(){
            isEndOfName = false;
            isInLib = false;
            count = 0;
            children = new Node[SIZE];
            for (int i = 0; i < SIZE; i++) {
                children[i] = null;
            }
            sub_feature = null;
            sub_feature_count = 0;
            sub_feature_history = null;
            sub_feature_history_count = 0;
        }
    }

    Node insert(String name){
        return insert(name, root);
    }

    Node insert(String name, Node root) {
        if(root == null)
            root = new Node();
        Node moving_node = root;

        for (int i = 0; i < name.length(); i++)
        {
            int index = name.charAt(i) - 'a';
            if (moving_node.children[index] == null) {
                moving_node.children[index] = new Node();
            }
            moving_node = moving_node.children[index];
        }
        moving_node.isEndOfName = true;

        return moving_node;
    }

    void remove(String name){
        remove(name, root);
    }

    void remove(String name, Node root) {
        if(root == null)
            return;

        Node moving_node = search(name);

        if(moving_node != null){
            moving_node.isEndOfName = false;
            moving_node.isInLib = false;
        }
    }

    Node search(String name) {
        Node moving_node = root;
        for (int i = 0; i < name.length(); i++)
        {
            int index = name.charAt(i) - 'a';
            if (moving_node.children[index] == null) {
                return null;
            }
            moving_node = moving_node.children[index];
        }

        return moving_node.isEndOfName ? moving_node : null;
    }

    void print_all_children(Node root, String name, int index) {
        if(root == null) return;
        if(root.isEndOfName) System.out.print(name + " ");;

        for(int i = 0; i < SIZE; i++) {
            if(root.children[i] != null) {
                if(index < name.length()) {
                    name = name.substring(0, index) + (char) (i + 'a') + name.substring(index+1);
                } else {
                    name += (char) (i + 'a');
                }
                print_all_children(root.children[i], name, index + 1);
            }
        }
    }

    void print_all_children(Node root) {
        print_all_children(root, "", 0);
    }

    void print_all_children() {
        print_all_children(root, "", 0);
    }
}