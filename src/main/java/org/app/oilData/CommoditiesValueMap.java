package org.app.oilData;

public class CommoditiesValueMap {
    private String date;
    private double value;

    public CommoditiesValueMap(String date, double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }
}