package com.sam_chordas.android.stockhawk.ui;

import java.util.ArrayList;

/**
 * Created by DIMPESH : $month
 */
public class GraphDetail
{
    String company_name;
    String ticker;
    String currency;
    String low;
    String prev_price;
    String exch_name;

    public String getPrev_price() {
        return prev_price;
    }

    public void setPrev_price(String prev_price) {
        this.prev_price = prev_price;
    }

    public String getExch_name() {
        return exch_name;
    }

    public void setExch_name(String exch_name) {
        this.exch_name = exch_name;
    }

    String high;
//    GraphData[] graphData=new GraphData[30];
    ArrayList<String> date=new ArrayList<String>();
    ArrayList<Float> value=new ArrayList<Float>();


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }
}
