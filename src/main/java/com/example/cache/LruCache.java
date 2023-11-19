package com.example.cache;

import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> implements Cached<K, V>{

    private final Map<K, V> cacheMap;

    @Value("$cache.course.size")
    private int capacity;

    @Value("$cache.course.loadFactor")
    private float loadFactor;

    @Value("$cache.course.accessOrder")
    private boolean accessOrder;

    public LruCache(){
        cacheMap = new LinkedHashMap<>(capacity, loadFactor, accessOrder){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public synchronized V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public synchronized void add(K key, V val) {
        cacheMap.put(key, val);
    }

    @Override
    public synchronized void delete(K key) {
        cacheMap.remove(key);
    }
}
