package com.kvstore.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shubham.gupta
 */
public class AttributeValueTypeFactory {
    private final Map<AttributeValueType, Class> valueTypeClassHashMap = new HashMap<>();
    public AttributeValueTypeFactory(){
        valueTypeClassHashMap.put(AttributeValueType.INTEGER, Integer.class);
        valueTypeClassHashMap.put(AttributeValueType.DOUBLE, Double.class);
        valueTypeClassHashMap.put(AttributeValueType.BOOLEAN, Boolean.class);
        valueTypeClassHashMap.put(AttributeValueType.STRING, String.class);
    }

    public AttributeValueType getAttributeValueType(String value) {
        for (Map.Entry<AttributeValueType, Class> entry : valueTypeClassHashMap.entrySet()) {
            AttributeValueType attributeValueType = entry.getKey();
            try {
                switch (attributeValueType) {
                    case INTEGER:
                        Integer.parseInt(value);
                        return AttributeValueType.INTEGER;
                    case DOUBLE:
                        Double.parseDouble(value);
                        return AttributeValueType.DOUBLE;
                    case BOOLEAN:
                        if(value.toLowerCase().equals("true") || value.toLowerCase().equals("false"))
                            return AttributeValueType.BOOLEAN;
                }
            } catch (Exception e) {

            }
        }
        return AttributeValueType.STRING;
    }

    public Object convertValueToType(String value, AttributeValueType attributeValueType){
        try {
            switch (attributeValueType) {
                case INTEGER:
                    return Integer.parseInt(value);
                case DOUBLE:
                    return Double.parseDouble(value);
                case BOOLEAN:
                    return Boolean.parseBoolean(value);
            }
        } catch (Exception e) {

        }
        return value;
    }
}
