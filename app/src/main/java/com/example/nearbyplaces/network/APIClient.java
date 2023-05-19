package com.example.nearbyplaces.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit;

    public static synchronized Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/").
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
