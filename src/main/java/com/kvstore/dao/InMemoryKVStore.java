package com.kvstore.dao;

import com.kvstore.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shubham.gupta
 */
public class InMemoryKVStore implements KVStore {
    private Map<Key, List<Attribute>> keyValueMap = new ConcurrentHashMap<>();
    private Map<AttributeKey, AttributeValueType> attributeKeyAttributeValueTypeMap = new ConcurrentHashMap<>();
    private final AttributeValueTypeFactory attributeValueTypeFactory;

    public InMemoryKVStore(AttributeValueTypeFactory attributeValueTypeFactory) {
        this.attributeValueTypeFactory = attributeValueTypeFactory;
    }

    public Map<Key, List<Attribute>> getKeyValueMap() {
        return keyValueMap;
    }

    public Map<AttributeKey, AttributeValueType> getAttributeKeyAttributeValueTypeMap() {
        return attributeKeyAttributeValueTypeMap;
    }

    public AttributeValueType get(AttributeKey key){
        return attributeKeyAttributeValueTypeMap.get(key);
    }

    public List<Key> getKeys(){
        return new ArrayList<>(keyValueMap.keySet());
    }

    public List<Attribute> get(Key key){
        return keyValueMap.get(key);
    }

    public void delete(Key key){
        keyValueMap.remove(key);
    }

    public void put(Key key, List<Attribute> listOfAttributes, Map<AttributeKey, AttributeValueType> hashMap){
        keyValueMap.put(key, listOfAttributes);
        for(Map.Entry<AttributeKey, AttributeValueType> entry: hashMap.entrySet()) {
            attributeKeyAttributeValueTypeMap.put(entry.getKey(), entry.getValue());
        }
    }
}
