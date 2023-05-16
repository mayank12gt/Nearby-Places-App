package com.example.nearbyplaces;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nearbyplaces.databinding.FragmentMarkerDetailsBinding;
import com.example.nearbyplaces.model.NearbyPlace;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class MarkerDetailsFragment extends BottomSheetDialogFragment {


        FragmentMarkerDetailsBinding binding;
        NearbyPlace place;
        MarkerDetailsFragment(NearbyPlace place){
            this.place = place;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_marker_details, container, false);
        binding = DataBindingUtil.bind(v);


        if(place!=null) {
            Toast.makeText(getContext(), place.getName(), Toast.LENGTH_SHORT).show();
            binding.setPlace(place);

        }
        else{
            Toast.makeText(getContext(), "D", Toast.LENGTH_SHORT).show();
        }
        return v;
    }
}