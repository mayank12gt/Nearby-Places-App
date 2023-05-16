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

public class BindingAdapters {

    @BindingAdapter("imageURL")
    public static void setImage(ImageView imageView, String photo_ref){
//        String url = "https://maps.googleapis.com/maps/api/place/photo" +
//                "?photo_reference="+photo_ref+"&key="+ BuildConfig.MAPS_API_KEY;
//        if(photo_ref!=null){
//            Glide.with(imageView.getContext()).load("https://maps.googleapis.com/maps/api/place/photo?photo_reference=AZose0k5YBSCWTHz75gzKtBXlaBsUKsTdYKZ6ebi_lVei8IEKeVhzEWtpVMu1zj0xrF6dqlk_7-LHm0PQGBOHHW0gS3USrgxLdj6W69ioOUPgehH8AdDLgZbGpncV-9fvgH3XVs8Jn_CO7lF8jPuBIBI0QTygeYft-ko-dHWL35kMnjiBWON&key=AIzaSyCWW8S1nqod_hvzzIbXMICscl2tqm8wIvE")
//                   .into(imageView);
//
//
//        }
//        else{
//            imageView.setVisibility(View.GONE);
//        }
    }
}
