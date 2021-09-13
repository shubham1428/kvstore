package com.keys;

import com.google.common.collect.Lists;
import com.kvstore.dao.InMemoryKVStore;
import com.kvstore.dao.KVStore;
import com.kvstore.entities.Attribute;
import com.kvstore.entities.AttributeKey;
import com.kvstore.entities.AttributeValueTypeFactory;
import com.kvstore.entities.Key;
import com.kvstore.exceptions.KeyNotExistsException;
import com.kvstore.exceptions.TypeMismatchException;
import com.kvstore.service.KVStoreService;
import com.kvstore.service.KVStoreServiceImpl;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class KVStoreTest {
    private AttributeValueTypeFactory attributeValueTypeFactory = new AttributeValueTypeFactory();
    private KVStore kvStore = new InMemoryKVStore();
    private KVStoreService kvStoreService = new KVStoreServiceImpl(kvStore, attributeValueTypeFactory);

    private Random random = new Random();

    @Test
    void testAddKeys() {
        String key = "key1";
        List<Attribute> attributes = Lists.newArrayList(createBoolean("is_hungry"), createNumber("latitude"), createString("capital"));
        assertTrue(kvStoreService.insert(key, attributes));
    }

    @Test
    void testAddKeysFailOnDuplicate() {
        String key = "key1";
        List<Attribute> attributes = Lists.newArrayList(createBoolean("is_hungry"), createNumber("latitude"), createString("capital"));
        assertTrue(kvStoreService.insert(key, attributes));
        assertFalse(kvStoreService.insert(key, attributes));
    }

    @org.junit.Test(expected = TypeMismatchException.class)
    void testAddIncorrectDataType() {
        String key1 = "key1";
        List<Attribute> attributes1 = Lists.newArrayList(createBoolean("is_hungry"), createNumber("latitude"), createString("capital"));
        String key2 = "key2";
        List<Attribute> attributes2 = Lists.newArrayList(createString("is_hungry"));

        assertTrue(kvStoreService.insert(key1, attributes1));
        kvStoreService.insert(key2, attributes2);
    }

    @Test
    void fetchKeyExists() {
        String key1 = "key1";
        List<Attribute> attributes1 = Lists.newArrayList(createBoolean("is_hungry"), createNumber("latitude"), createString("capital"));
        assertTrue(kvStoreService.insert(key1, attributes1));
        final List<Attribute> actualAttributes = kvStoreService.get(key1);
        assertEquals(attributes1, actualAttributes);

    }

    @org.junit.Test(expected = KeyNotExistsException.class)
    void fetchKeyDoesntExist() {
        String key1 = "key1";
        kvStoreService.get(key1);
        //fail("Key doesn't exist");
    }

    @Test
    void mergeKeys() {
        String key = "key1";
        final Attribute hungry1 = createBoolean("is_hungry");
        final Attribute latitude = createNumber("latitude");
        final Attribute capital = createString("capital");
        final Attribute state = createString("state");
        hungry1.setAttributeValue(true);
        final Attribute hungry2 = createBoolean("is_hungry");
        hungry2.setAttributeValue(false);
        List<Attribute> attributes = Lists.newArrayList(hungry1, latitude, capital);
        List<Attribute> updateAttributes = Lists.newArrayList(hungry2, state);
        //List<Attribute> mergedAttributes = Lists.newArrayList(capital, latitude, hungry2, state);
        assertTrue(kvStoreService.insert(key, attributes));
        final List<Attribute> actualAttributes = kvStoreService.get(key);
        assertEquals(actualAttributes, attributes);
        assertTrue(kvStoreService.update(key, updateAttributes));
        //TODO: Fix for any ordering issues when comparing attributes.
        assertEquals(updateAttributes, kvStoreService.get(key));
    }

    @Test
    void deleteKeyExists() {
        String key1 = "key1";
        List<Attribute> attributes1 = Lists.newArrayList(createBoolean("is_hungry"), createNumber("latitude"), createString("capital"));
        assertTrue(kvStoreService.insert(key1, attributes1));
        final List<Attribute> actualAttributes = kvStoreService.get(key1);
        assertEquals(attributes1, actualAttributes);
        assertTrue(kvStoreService.delete(key1));
    }

    @Test
    void deleteKeyDoesntExist() {
        String key1 = "key1";
        assertFalse(kvStoreService.delete(key1));
    }

    @Test
    void testSecondaryIndex() {
        String key1 = "key1";
        final Attribute valid = new Attribute(new AttributeKey("is_valid"), "true");
        final Attribute longitude = createNumber("longitude");
        final Attribute capital = createString("capital");
        final Attribute state = createString("state");
        String key2 = "key2";

        kvStoreService.insert(key1, Lists.newArrayList(valid, longitude, capital));
        kvStoreService.insert(key2, Lists.newArrayList(valid, capital, state));
        final List<Pair<Key,List<Attribute>>> secondaryList = kvStoreService.search("is_valid", "true");
        assertEquals(2, secondaryList.size());

    }

    Attribute createBoolean(String name) {
        List<Object> values = Lists.newArrayList("true", true, false, "false");
        return new Attribute(new AttributeKey(name), values.get(random.nextInt(values.size())));
    }

    Attribute createString(String name) {
        List<Object> values = Lists.newArrayList("delhi", "snow", "flake", "db");
        return new Attribute(new AttributeKey(name), values.get(random.nextInt(values.size())));
    }

    Attribute createNumber(String name) {
        List<Object> values = Lists.newArrayList(11.12, 23.12, 119.12, -123.423);
        return new Attribute(new AttributeKey(name), values.get(random.nextInt(values.size())));
    }
}
