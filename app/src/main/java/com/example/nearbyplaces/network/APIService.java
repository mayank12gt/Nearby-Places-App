package com.example.nearbyplaces.network;

import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.model.PlaceDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearbyPlaces(@Query("location") String location,
                                               @Query("radius") int radius,
                                               @Query("type") String type,
                                               @Query("key") String key,
                                                                @Query("pagetoken") String pagetoken);



    @GET("details/json")
    Call<PlaceDetailsResponse> getPlaceDetails(@Query("place_id") String place_id,
                                               @Query("key") String key
                                               );
}
