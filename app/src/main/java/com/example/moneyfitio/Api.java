package com.example.moneyfitio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Api {
    @GET("/b/5fd283d381ec296ae71c5040")
    Call<ModelList> getData();
}
