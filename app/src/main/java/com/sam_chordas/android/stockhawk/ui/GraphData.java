package com.sam_chordas.android.stockhawk.ui;

/**
 * Created by DIMPESH : ${month}
 */
public class GraphData

{
    String date;
    double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GraphData{" +
                "date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
