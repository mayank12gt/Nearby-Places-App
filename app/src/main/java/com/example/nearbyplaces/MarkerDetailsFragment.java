package com.example.nearbyplaces;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nearbyplaces.databinding.FragmentMarkerDetailsBinding;
import com.example.nearbyplaces.model.NearbyPlace;
import com.example.nearbyplaces.model.PlaceDetailsResponse;
import com.example.nearbyplaces.viewmodel.MarkerDetailsFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MarkerDetailsFragment extends BottomSheetDialogFragment {


        FragmentMarkerDetailsBinding binding;
        String placeID;
        Context context;
        MarkerDetailsFragmentViewModel viewModel;

        MarkerDetailsFragment(String placeID, Context context){
            this.placeID = placeID;
            this.context = context;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_marker_details, container, false);
        binding = DataBindingUtil.bind(v);
        viewModel = new ViewModelProvider(this).get(MarkerDetailsFragmentViewModel.class);


        if(placeID!=null) {

           // Log.d("ID",placeID);

            ExecutorService service = Executors.newSingleThreadExecutor();
            viewModel.getPlaceDetails(placeID,BuildConfig.MAPS_API_KEY).observe(this, new Observer<PlaceDetailsResponse>() {
                @Override
                public void onChanged(PlaceDetailsResponse placeDetailsResponse) {
                    if(placeDetailsResponse!=null) {
                        //Log.d("details",placeDetailsResponse.getPlaceDeatails().getFormattedAddress());
                        binding.setPlace(placeDetailsResponse.getPlaceDeatails());
                    }
                    else{
                        Toast.makeText(context,"Details not found",Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            });
        }
        else{
           Toast.makeText(context,"Error fetching details",Toast.LENGTH_SHORT).show();
        }
        return v;
    }
}