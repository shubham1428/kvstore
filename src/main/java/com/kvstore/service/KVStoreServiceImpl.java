package com.kvstore.service;

import com.kvstore.dao.KVStore;
import com.kvstore.entities.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shubham.gupta
 */
public class KVStoreServiceImpl implements KVStoreService {
    private final KVStore kvStore;
    private final AttributeValueTypeFactory attributeValueTypeFactory;

    public KVStoreServiceImpl(KVStore kvStore, AttributeValueTypeFactory attributeValueTypeFactory) {
        this.kvStore = kvStore;
        List<Attribute> attributePairList = null;
        this.attributeValueTypeFactory = attributeValueTypeFactory;
    }


    @Override
    public List<Attribute> get(String key) {
        return kvStore.get(new Key(key));
    }

    @Override
    public List<Pair<Key,List<Attribute>> > search(String attributeKey, String attributeValue) {
        AttributeKey searchAttributeKey = new AttributeKey(attributeKey);
        AttributeValueType searchAttributeValueType = attributeValueTypeFactory.getAttributeValueType(attributeValue);
        Object searchAttributeValue  = attributeValueTypeFactory.convertValueToType(attributeValue, searchAttributeValueType);
        List<Pair<Key,List<Attribute>> > result = new ArrayList<>();
        for (Map.Entry<Key, List<Attribute>> e : kvStore.getKeyValueMap().entrySet()) {
            for(Attribute attributePair: e.getValue()){
                if(attributePair.getAttributeKey().equals(searchAttributeKey) && attributePair.getAttributeValue().equals(searchAttributeValue)){
                    result.add(new Pair<>(e.getKey(), e.getValue()));
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void insert(String key, List<Attribute> listOfAttributes) {
        if(kvStore.get(new Key(key)) != null) {
            //throw exception
            System.out.println("Key already exists. Ignoring this record");
        }
        else put(key, listOfAttributes);
    }

    @Override
    public void update(String key, List<Attribute> listOfAttributes) {
        put(key, listOfAttributes);
    }

    private void put(String key, List<Attribute> listOfAttributes) {
        Map<AttributeKey, AttributeValueType> hashMap = new HashMap<>();
        for(Attribute attributePair: listOfAttributes){
            AttributeValueType expectedAttributeValueType = kvStore.get(attributePair.getAttributeKey());
            AttributeValueType attributeValueType = attributeValueTypeFactory.getAttributeValueType(attributePair.getAttributeValue().toString());

            if(expectedAttributeValueType == null){
                hashMap.put(attributePair.getAttributeKey(), attributeValueType);
            }
            else if(expectedAttributeValueType != attributeValueType){
                    System.out.println("Invalid type given");
                    return;
            }

            attributePair.setAttributeValue(attributeValueTypeFactory.convertValueToType(attributePair.getAttributeValue().toString(), attributeValueType));
        }
        kvStore.put(new Key(key), listOfAttributes, hashMap);
    }

    @Override
    public void delete(String key) {
        kvStore.delete(new Key(key));
    }

    @Override
    public List<Key> keys() {
        return kvStore.getKeys();
    }
}
