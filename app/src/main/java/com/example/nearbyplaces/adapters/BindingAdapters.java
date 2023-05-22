package com.example.nearbyplaces.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.nearbyplaces.BuildConfig;
import com.example.nearbyplaces.R;
import com.example.nearbyplaces.model.Photo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class BindingAdapters {




    @BindingAdapter("loadImages")
    public static void setImage(ImageSlider slider, List<Photo> photos) {
        List<SlideModel> imageList = new ArrayList<>();
        if (photos != null) {
            for (Photo photo : photos) {

                String url = "https://maps.googleapis.com/maps/api/place/photo" + "?maxwidth=400" +
                        "&photo_reference=" + photo.getPhotoReference() + "&key=" + BuildConfig.MAPS_API_KEY;
                if (photo.getPhotoReference() != null) {


                    imageList.add(new SlideModel(url, ScaleTypes.FIT));


                } else {
                    imageList.add(new SlideModel(R.drawable.placeholder, ScaleTypes.CENTER_CROP));
                }



            }


        }
        else{
            imageList.add(new SlideModel(R.drawable.placeholder, ScaleTypes.CENTER_CROP));
            }

        slider.setImageList(imageList);
        slider.setSlideAnimation(AnimationTypes.ZOOM_OUT);
    }

    @BindingAdapter("loadTags")
    public static void loadTags(ChipGroup chipGroup, List<String> tags) {
        if (tags != null) {
            for (String tag : tags) {
                if(tag!=null) {

                    Chip chip = (Chip) LayoutInflater.from((chipGroup.getContext())).inflate(R.layout.single_chip_layout, chipGroup, false);
                    chip.setText(tag);
                    chipGroup.addView(chip);
                }


            }

        }
    }
}
