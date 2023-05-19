package com.example.nearbyplaces.repo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.model.PlaceDetailsResponse;
import com.example.nearbyplaces.network.APIClient;
import com.example.nearbyplaces.network.APIService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPlacesRepo {
    private APIService apiService;

    public NearbyPlacesRepo(){
        apiService= APIClient.getRetrofit().create(APIService.class);
    }

    public LiveData<NearbySearchResponse> getNearbyPlaces(String location, int radius,String type, String key,String pagetoken){
        MutableLiveData<NearbySearchResponse> mutableLiveData = new MutableLiveData<>();
        ExecutorService service = Executors.newSingleThreadExecutor();


        service.execute(new Runnable() {
            @Override
            public void run() {
                apiService.getNearbyPlaces(location,radius,type,key,pagetoken).enqueue(new Callback<NearbySearchResponse>() {
                    @Override
                    public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                        mutableLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                        Log.d("Network",t.getLocalizedMessage());
                    }
                });
            }
        });

        return mutableLiveData;
    }


    public LiveData<PlaceDetailsResponse> getPlaceDetails(String placeID, String key){
        MutableLiveData<PlaceDetailsResponse> placeDetailsResponseMutableLiveData = new MutableLiveData<>();


        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                apiService.getPlaceDetails(placeID,key).enqueue(new Callback<PlaceDetailsResponse>() {
                    @Override
                    public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                        placeDetailsResponseMutableLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                        Log.d("Network",t.getLocalizedMessage());
                    }
                });
            }
        });



        return placeDetailsResponseMutableLiveData;
    }
}
