package com.riskshield;

public class MyDataObject {
    public String name;
    public int value;

    public MyDataObject() {} // Default constructor for JSON-B

    public MyDataObject(String name, int value) {
        this.name = name;
        this.value = value;
    }
}