package com.example.nearbyplaces;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nearbyplaces.databinding.FragmentMarkerDetailsBinding;
import com.example.nearbyplaces.model.PlaceDetailsResponse;
import com.example.nearbyplaces.viewmodel.MarkerDetailsFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MarkerDetailsFragment extends BottomSheetDialogFragment {


        FragmentMarkerDetailsBinding binding;
        String placeID;
        Context context;
        MarkerDetailsFragmentViewModel viewModel;
    Button getDirection;

    String place_lat;
    String place_lng;

    String user_lat;
    String user_lng;

        MarkerDetailsFragment(String placeID, Location location, Context context){
            this.placeID = placeID;
            this.context = context;
            user_lat = String.valueOf(location.getLatitude());
            user_lng = String.valueOf(location.getLongitude());
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_marker_details, container, false);
        binding = DataBindingUtil.bind(v);
        viewModel = new ViewModelProvider(this).get(MarkerDetailsFragmentViewModel.class);
        getDirection=v.findViewById(R.id.getDirection);

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+user_lat+","+user_lng
                                +"&daddr="+place_lat+","+place_lng));
                startActivity(intent);
            }
        });

        if(placeID!=null) {

           // Log.d("ID",placeID);

            ExecutorService service = Executors.newSingleThreadExecutor();
            viewModel.getPlaceDetails(placeID,BuildConfig.MAPS_API_KEY).observe(this, new Observer<PlaceDetailsResponse>() {
                @Override
                public void onChanged(PlaceDetailsResponse placeDetailsResponse) {
                    if(placeDetailsResponse!=null) {
                        //Log.d("details",placeDetailsResponse.getPlaceDeatails().getFormattedAddress());
                        binding.setPlace(placeDetailsResponse.getPlaceDeatails());
                        place_lat = placeDetailsResponse.getPlaceDeatails().getGeometry().getLocation().getLat().toString();
                        place_lng = placeDetailsResponse.getPlaceDeatails().getGeometry().getLocation().getLng().toString();
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