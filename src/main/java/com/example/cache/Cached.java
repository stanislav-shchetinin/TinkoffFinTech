package com.example.cache;

public interface Cached<K, V> {
    V get(K key);
    void add(K key, V val);
    void delete(K key);
}
