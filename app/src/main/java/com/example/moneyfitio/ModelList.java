package com.example.moneyfitio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelList {

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    @SerializedName("data")
    private List<Model> modelList;
}
