package com.kvstore.entities;

/**
 * @author shubham.gupta
 */
public class GenericKey {
    private final String key;

    public GenericKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }

    @Override
    public int hashCode() {
        return this.getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        GenericKey key1= (GenericKey) obj;
        return this.getKey().equals(key1.getKey());
    }
}
