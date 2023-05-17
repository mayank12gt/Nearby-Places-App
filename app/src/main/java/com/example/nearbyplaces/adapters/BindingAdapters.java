package com.example.nearbyplaces.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.nearbyplaces.BuildConfig;
import com.example.nearbyplaces.R;

public class BindingAdapters {

    @BindingAdapter("imageURL")
    public static void setImage(ImageView imageView, String photo_ref){
        String url = "https://maps.googleapis.com/maps/api/place/photo"+"?maxwidth=400"+
               "&photo_reference="+photo_ref+"&key="+ BuildConfig.MAPS_API_KEY;
        if(photo_ref!=null){
            Glide.with(imageView.getContext()).load(url)
                   .into(imageView);
        }
        else{
            Glide.with(imageView.getContext()).load(R.drawable.placeholder)
                    .into(imageView);
       }
    }
}
