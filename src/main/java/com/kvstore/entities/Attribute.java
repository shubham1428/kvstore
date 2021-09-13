package com.kvstore.entities;

/**
 * @author shubham.gupta
 */
public class Attribute {
    private AttributeKey attributeKey;
    private Object attributeValue;

    public Attribute(AttributeKey attributeKey, Object attributeValue) {
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
    }

    public void setAttributeKey(AttributeKey attributeKey) {
        this.attributeKey = attributeKey;
    }

    public void setAttributeValue(Object attributeValue) {
        this.attributeValue = attributeValue;
    }

    public AttributeKey getAttributeKey() {
        return attributeKey;
    }

    public Object getAttributeValue() {
        return attributeValue;
    }

    @Override
    public String toString() {
        return attributeKey +  ":" + attributeValue;
    }
}
