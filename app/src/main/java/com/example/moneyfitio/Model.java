package com.example.moneyfitio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Model {
    @SerializedName("date")
    private String date;
    @SerializedName("investment_invest_value")
    private int invest_value;
    @SerializedName("investment_current_value")
    private int current_value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getInvest_value() {
        return invest_value;
    }

    public void setInvest_value(int invest_value) {
        this.invest_value = invest_value;
    }

    public int getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(int current_value) {
        this.current_value = current_value;
    }

    public Model(String date, int invest_value, int current_value) {
        this.date = date;
        this.invest_value = invest_value;
        this.current_value = current_value;
    }
}
