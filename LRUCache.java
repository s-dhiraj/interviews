package org.example;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    Node head = new Node(0, 0), tail = new Node(0, 0);
    Map < Integer, Node > map = new HashMap();
    int capacity;

    public LRUCache(int _capacity) {
        capacity = _capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        if (map.size() == capacity) {
            remove(tail.prev);
        }
        insert(new Node(key, value));
    }

    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insert(Node node) {
        map.put(node.key, node);
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    class Node {
        Node prev, next;
        int key, value;
        Node(int _key, int _value) {
            key = _key;
            value = _value;
        }
    }
}

// *******************************************************   LLD SOLUTION ************************************

class Node {
    public int key;
    public int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;
    Map<Integer, Node> map;

    public DoublyLinkedList() {
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
    }

    public void insertAtHead(Node node) {
        map.put(node.key, node);
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public Node removeLast() {
        if (tail.prev == head) {
            return null;
        }
        Node temp = tail.prev;
        remove(temp);
        return temp;
    }

    public void print() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.key + "  ");
            temp = temp.next;
        }
        System.out.println();
    }
}

class CachingFactory {
    public static Cache createFactory(String input, int capacity) {
        switch (input.toUpperCase()) {
            case "LRU":
                return new LRUCaching(capacity);
            case "LFU":
                break;
            default:
                break;
        }
        return null;
    }
}

abstract class Cache {
    int capacity;

    abstract void put(int key, int value);

    abstract int get(int key);

    public Cache(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

class LRUCaching extends Cache {
    DoublyLinkedList doublyLinkedList;

    public LRUCaching(int capacity) {
        super(capacity);
        this.doublyLinkedList = new DoublyLinkedList();
    }

    @Override
    void put(int key, int value) {
        if (doublyLinkedList.map.containsKey(key)) {
            doublyLinkedList.remove(doublyLinkedList.map.get(key));
        }
        if (doublyLinkedList.map.size() == capacity) {
            doublyLinkedList.remove(doublyLinkedList.tail.prev);
        }
        Node temp = new Node(key, value);
        doublyLinkedList.insertAtHead(temp);
    }

    @Override
    public int get(int key) {
        if (doublyLinkedList.map.containsKey(key)) {
            Node node = doublyLinkedList.map.get(key);
            doublyLinkedList.remove(node);
            doublyLinkedList.insertAtHead(node);
            return node.value;
        } else {
            return -1;
        }
    }
}

public class App {
    public static void main(String[] args) {
        String str = "lru";
        Cache cache = CachingFactory.createFactory(str, 2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
}
