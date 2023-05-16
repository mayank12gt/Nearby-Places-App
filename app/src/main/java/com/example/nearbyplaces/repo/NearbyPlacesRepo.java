package com.example.nearbyplaces.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.network.APIClient;
import com.example.nearbyplaces.network.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPlacesRepo {
    private APIService apiService;

    public NearbyPlacesRepo(){
        apiService= APIClient.getRetrofit().create(APIService.class);
    }

    public LiveData<NearbySearchResponse> getNearbyPlaces(String location, int radius, String key,String pagetoken){
        MutableLiveData<NearbySearchResponse> mutableLiveData = new MutableLiveData<>();

        apiService.getNearbyPlaces(location,radius,key,pagetoken).enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                mutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                Log.d("Network",t.getLocalizedMessage());
            }
        });
        return mutableLiveData;
    }
}
