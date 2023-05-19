package com.example.nearbyplaces;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;


public class FilterDialogFragment extends DialogFragment {


    String types[];
    AutoCompleteTextView spinner;
    GetFilterValues callback;

    AppCompatTextView get_results;
    String type;
    Slider radiusSlider;
Double radius=1.5;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        get_results = v.findViewById(R.id.results_btn);
        radiusSlider = v.findViewById(R.id.slider);

        types =getResources().getStringArray(R.array.types);

        spinner = v.findViewById(R.id.spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.drop_down_item,types);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                type=spinner.getText().toString();
            }
        });

        radiusSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
             radius = Double.valueOf(slider.getValue());
            }
        });



        get_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.getFilterValues(type,radius);
                dismiss();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (GetFilterValues) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement GetFilterValues");
        }
    }
}