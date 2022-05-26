package com.smile.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author smile
 */
public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, LRUNode<K, V>> map = new HashMap<>();
    private final LRUNode<K, V> head;
    private final LRUNode<K, V> tail;

    public LRUCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity至少为1");
        }
        this.capacity = capacity;
        this.head = new LRUNode<>();
        this.tail = new LRUNode<>();
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public void put(K k, V v) {
        LRUNode<K, V> node = map.get(k);
        if (node == null) {
            node = new LRUNode<>(k, v);
            map.put(k, node);
            addToHead(node);
        } else {
            node.v = v;
            moveToHead(node);
        }

        if (map.size() > capacity) {
            LRUNode<K, V> tailNode = removeTail();
            map.remove(tailNode.k);
        }
    }

    public V get(K k) {
        LRUNode<K, V> node = map.get(k);
        if (node == null) {
            return null;
        }

        moveToHead(node);
        return node.v;
    }


    private void addToHead(LRUNode<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;

    }

    private void moveToHead(LRUNode<K, V> node) {
        removeNode(node);
        addToHead(node);
    }

    private LRUNode<K, V> removeTail() {
        LRUNode<K, V> node = tail.prev;
        removeNode(node);
        return node;
    }

    private void removeNode(LRUNode<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private static class LRUNode<K, V> {
        K k;
        V v;
        LRUNode<K, V> prev;
        LRUNode<K, V> next;

        public LRUNode() {
        }

        public LRUNode(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }
}
