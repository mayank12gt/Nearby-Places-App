package com.example.nearbyplaces;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.util.Locale;


public class FilterDialogFragment extends DialogFragment {


    String types[];
    AutoCompleteTextView spinner;
    GetFilterValues callback;

    Button get_results;
    Button reset;
    String type;
    Slider radiusSlider;
    Float radius=1.0f;

FilterDialogFragment(int searchradius, String est_type){
    radius= Float.valueOf(searchradius/1000);
    type = est_type;

}





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        get_results = v.findViewById(R.id.results_btn);
        radiusSlider = v.findViewById(R.id.slider);
        reset = v.findViewById(R.id.reset);

        types =getResources().getStringArray(R.array.types);

        spinner = v.findViewById(R.id.spinner);

        radiusSlider.setValue(radius);

        spinner.setText(type.length()!=0?type.substring(0, 1).toUpperCase() + type.substring(1):type);


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
             radius = Float.valueOf(slider.getValue());
            }
        });





        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFilters();
            }
        });



        get_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(), type+radius, Toast.LENGTH_SHORT).show();
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

    public void resetFilters(){
    radiusSlider.setValue(1.0f);
     spinner.setText("");
     type="";
     radius=1.0f;
    }
}