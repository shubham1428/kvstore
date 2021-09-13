package com.kvstore.service;

import com.kvstore.dao.KVStore;
import com.kvstore.entities.*;
import com.kvstore.exceptions.KeyNotExistsException;
import com.kvstore.exceptions.TypeMismatchException;
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
        //List<Attribute> attributeList = null;
        this.attributeValueTypeFactory = attributeValueTypeFactory;
    }


    @Override
    public List<Attribute> get(String key) {
        List<Attribute> result = kvStore.get(new Key(key));
        if(result == null){
            throw new KeyNotExistsException("Key doesn't exist");
            //System.out.println("Key doesn't exist");
        }
        return result;
    }

    @Override
    public List<Pair<Key,List<Attribute>> > search(String attributeKey, String attributeValue) {
        AttributeKey searchAttributeKey = new AttributeKey(attributeKey);
        AttributeValueType searchAttributeValueType = attributeValueTypeFactory.getAttributeValueType(attributeValue);
        Object searchAttributeValue  = attributeValueTypeFactory.convertValueToType(attributeValue, searchAttributeValueType);
        List<Pair<Key,List<Attribute>> > result = new ArrayList<>();
        for (Map.Entry<Key, List<Attribute>> e : kvStore.getKeyValueMap().entrySet()) {
            List<Attribute> list = e.getValue();
            for(Attribute attribute: list){
                if(attribute.getAttributeKey().equals(searchAttributeKey) && attribute.getAttributeValue().equals(searchAttributeValue)){
                    result.add(new Pair<>(e.getKey(), e.getValue()));
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public boolean insert(String key, List<Attribute> listOfAttributes) {
        if(kvStore.get(new Key(key)) != null) {
            //throw exception
            //throw new DuplicateException()
            System.out.println("Key already exists. Ignoring this record");
            return false;
        }
        else {
            return put(key, listOfAttributes);
        }
    }

    @Override
    public boolean update(String key, List<Attribute> listOfAttributes) {
        return put(key, listOfAttributes);
    }

    private boolean put(String key, List<Attribute> listOfAttributes) {
        Map<AttributeKey, AttributeValueType> attributeKeyAttributeValueTypeHashMap = new HashMap<>();
        for(Attribute attribute: listOfAttributes){
            AttributeValueType expectedAttributeValueType = kvStore.get(attribute.getAttributeKey());
            AttributeValueType attributeValueType = attributeValueTypeFactory.getAttributeValueType(attribute.getAttributeValue().toString());

            if(expectedAttributeValueType == null){
                attributeKeyAttributeValueTypeHashMap.put(attribute.getAttributeKey(), attributeValueType);
            }
            else if(expectedAttributeValueType != attributeValueType){
                    throw new TypeMismatchException("Type mismatch");
                    //System.out.println("Type mismatch");
                    //return false;
            }

            attribute.setAttributeValue(attributeValueTypeFactory.convertValueToType(attribute.getAttributeValue().toString(), attributeValueType));
        }
        try{
            kvStore.put(new Key(key), listOfAttributes, attributeKeyAttributeValueTypeHashMap);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        Key k = new Key(key);
        if(kvStore.get(k) == null)
            return false;
        try {
            kvStore.delete(k);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
