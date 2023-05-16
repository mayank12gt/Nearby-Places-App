package com.example.nearbyplaces.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.repo.NearbyPlacesRepo;

public class MapsActivityViewModel extends AndroidViewModel {

private NearbyPlacesRepo repo;
    public MapsActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new NearbyPlacesRepo();
    }

    public LiveData<NearbySearchResponse> getNearbyPlaces(String location, int radius, String key,String pagetoken){
        return repo.getNearbyPlaces(location, radius, key,pagetoken);
    }
}
