package com.kvstore.dao;

import com.kvstore.entities.AttributeKey;
import com.kvstore.entities.Attribute;
import com.kvstore.entities.AttributeValueType;
import com.kvstore.entities.Key;

import java.util.List;
import java.util.Map;

/**
 * @author shubham.gupta
 */
public interface KVStore {
    public Map<Key, List<Attribute>> getKeyValueMap();
    public Map<AttributeKey, AttributeValueType> getAttributeKeyAttributeValueTypeMap();
    public List<Key> getKeys();
    public List<Attribute> get(Key key);
    public void delete(Key key);
    public void put(Key key, List<Attribute> listOfAttributes, Map<AttributeKey, AttributeValueType> hashMap);
    public AttributeValueType get(AttributeKey key);
}
