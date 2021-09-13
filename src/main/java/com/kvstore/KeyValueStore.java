package com.kvstore;

import java.util.List;
import java.util.Map;

/*
 * The core interface for the Key Value Store
 * Please implement this interface
 * Note - Focus on clean code, abstractions and best practices including SOLID during the implementation.
 * Check KeyValueStoreTest which has the test cases provided.
 */
public interface KeyValueStore {
    boolean store(String key, List<Attribute> value);

    List<Attribute> get(String key);

    boolean update(String key, List<Attribute> value);

    boolean delete(String key);

    Map<String, List<Attribute>> getSecondaryIndex(String predicateKey, Object predicateValue);

}
