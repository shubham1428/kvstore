package com.kvstore.service;

import com.kvstore.entities.Attribute;
import com.kvstore.entities.Key;
import javafx.util.Pair;

import java.util.List;

/**
 * @author shubham.gupta
 */
public interface KVStoreService {
    List<Attribute> get(String key);
    List<Pair<Key,List<Attribute>>> search(String attributeKey, String attributeValue);
    void insert(String key, List<Attribute> listOfAttributes);
    void update(String key, List<Attribute> listOfAttributes);
    void delete(String key);
    List<Key> keys();
}
