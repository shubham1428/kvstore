package com.kvstore;

public class Attribute {
    private String name;

    private Object value;

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Attribute() {
        super();
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
