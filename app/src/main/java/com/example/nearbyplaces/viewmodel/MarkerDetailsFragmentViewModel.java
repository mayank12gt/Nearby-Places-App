package com.example.nearbyplaces.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.model.PlaceDetailsResponse;
import com.example.nearbyplaces.repo.NearbyPlacesRepo;

public class MarkerDetailsFragmentViewModel extends AndroidViewModel {

    private NearbyPlacesRepo repo;
    public MarkerDetailsFragmentViewModel(@NonNull Application application) {
        super(application);
        repo = new NearbyPlacesRepo();
    }

    public LiveData<PlaceDetailsResponse> getPlaceDetails(String placeID, String key){
        return repo.getPlaceDetails(placeID, key);
    }
}
