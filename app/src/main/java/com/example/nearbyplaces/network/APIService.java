package com.example.nearbyplaces.network;

import com.example.nearbyplaces.model.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("json")
    Call<NearbySearchResponse> getNearbyPlaces(@Query("location") String location,
                                               @Query("radius") int radius,
                                               @Query("key") String key,
                                               @Query("pagetoken") String pagetoken);
}
