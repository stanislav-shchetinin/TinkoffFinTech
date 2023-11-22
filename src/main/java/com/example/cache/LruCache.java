package com.example.cache;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LruCache<K, V> implements Cached<K, V>{

    private final Map<K, V> cacheMap;

    public LruCache(@Qualifier("cachedMap") Map<K, V> cacheMap) {
        this.cacheMap = cacheMap;
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
