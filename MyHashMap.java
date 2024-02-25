package cs1501_p3;


public class MyHashMap<V> {

    private static class Node<U> {
        String key;
        U value;
        Node<U> next;

        public Node(String key, U value) {
            this.key = key;
            this.value = value;
        }
    }

    // Prime numbers are better
    private static final int DEFAULT_CAPACITY = 17;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int capacity;
    private float loadFactor;
    private int size;
    private Node<V>[] table;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = new Node[capacity];
    }

    // put in to MyHashMap
    public void put(String key, V value) {
        int index = hash(key);
        Node<V> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        Node<V> newNode = new Node<V>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        if (size > capacity * loadFactor) {
            resize();
        }
    }

    // get from the MyHashMap by key
    public V get(String key) {
        int index = hash(key);
        Node<V> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    // remove from MyHashMap by key
    public void remove(String key) {
        int index = hash(key);
        Node<V> node = table[index];
        Node<V> prev = null;
        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return;
            }
            prev = node;
            node = node.next;
        }
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    private int hash(String key) {
        return (key.hashCode() % capacity + capacity) % capacity;
    }

    private void resize() {
        int oldCapacity = capacity;
        capacity = oldCapacity * 2 - 1;
        Node<V>[] newTable = new Node[capacity];
        for (int i = 0; i < oldCapacity; i++) {
            Node<V> node = table[i];
            while (node != null) {
                Node<V> next = node.next;
                int index = hash(node.key);
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }
        table = newTable;
    }

}

