package com.example.nearbyplaces;



import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.nearbyplaces.model.NearbyPlace;
import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.viewmodel.MapsActivityViewModel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nearbyplaces.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GetFilterValues {
    private final int LOCATION_PERMISSION_CODE=1;
    private static final Integer REQUEST_CODE_SHOW_MORE = 2;
    private static final Integer REQUEST_CODE_FILTER = 3;

    int Searchradius=1000;
    String est_type="";

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FusedLocationProviderClient client;
    Location currentLocation;
    MapsActivityViewModel viewModel;
    MarkerDetailsFragment markerDetailsBtmSheet;
    List<Marker> markers;
    String next_page_token="";

    Button filter_btn;
   Button more_results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        filter_btn = binding.filterBtn;
        more_results = binding.more;
        markers = new ArrayList<>();
        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);

        PlacesClient placesClient = Places.createClient(this);




        more_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shownearbyLocations(Searchradius,est_type,REQUEST_CODE_SHOW_MORE);

            }
        });


        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });



        viewModel = new ViewModelProvider(this).get(MapsActivityViewModel.class);
        client = LocationServices.getFusedLocationProviderClient(this);

        getLocation();

    }

    private void showFilterDialog() {
        FilterDialogFragment dialogFragment = new FilterDialogFragment(Searchradius,est_type);

      dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                markerDetailsBtmSheet = new MarkerDetailsFragment((String) marker.getTag(),getApplicationContext());

                markerDetailsBtmSheet.show(getSupportFragmentManager(), markerDetailsBtmSheet.getTag());


                return true;
            }
        });


    }


    public void getLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);

            return;
        }


        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        currentLocation=location;
                        Toast.makeText(MapsActivity.this, "Location recieved", Toast.LENGTH_SHORT).show();
                        shownearbyLocations(Searchradius,est_type,REQUEST_CODE_FILTER);
                        loadMap();
                    }
                    else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
                        alertDialog.setTitle("Location setting!");
                        alertDialog.setMessage("Location is not enabled, Do you want to go to settings menu to enable location?");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                MapsActivity.this.startActivity(intent);
                                finish();
                                System.exit(0);
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MapsActivity.this, "Location Access is required", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                finish();
                                System.exit(0);
                            }
                        });
                        alertDialog.show();

                        Toast.makeText(MapsActivity.this, "Error in detecting location, Turn on Location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MapsActivity.this, "Error in fetching location", Toast.LENGTH_SHORT).show();
                }
            });
    }



    private void loadMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==LOCATION_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                getLocation();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
            }
        }
    }


    public void shownearbyLocations(int radius, String type, Integer REQUEST_CODE) {
        String loc = String.valueOf(currentLocation.getLatitude() + "," + currentLocation.getLongitude());

        if (REQUEST_CODE == REQUEST_CODE_FILTER) {

            if (markers != null) {
                removeMarkers(markers);
            }



            viewModel.getNearbyPlaces(loc, 10000, type, BuildConfig.MAPS_API_KEY, "")
                    .observe(this, new Observer<NearbySearchResponse>() {
                        @Override
                        public void onChanged(NearbySearchResponse nearbySearchResponse) {
                            if (nearbySearchResponse != null) {
                                if (nearbySearchResponse.getResults() != null) {

                                    for (NearbyPlace place : nearbySearchResponse.getResults()) {


                                        LatLng nearbyLocation = new LatLng(place.getGeometry().getLocation().getLat(),
                                                place.getGeometry().getLocation().getLng());
                                        //Log.d("Loc", String.valueOf(place.getName()));
                                        MarkerOptions markerOptions = new MarkerOptions().position(nearbyLocation).title(place.getName());
                                        Marker m = mMap.addMarker(markerOptions);
                                        m.setTag(place.getPlaceId());

                                        markers.add(m);

                                    }
                                    next_page_token=nearbySearchResponse.getNextPageToken();


                                }
                            }
                        }
                    });


        }
        else if (REQUEST_CODE==REQUEST_CODE_SHOW_MORE){
            if(next_page_token!=null) {


                viewModel.getNearbyPlaces(loc, radius, type, BuildConfig.MAPS_API_KEY, next_page_token)
                        .observe(this, new Observer<NearbySearchResponse>() {
                            @Override
                            public void onChanged(NearbySearchResponse nearbySearchResponse) {
                                if (nearbySearchResponse != null) {
                                    if (nearbySearchResponse.getResults() != null) {

                                        for (NearbyPlace place : nearbySearchResponse.getResults()) {


                                            LatLng nearbyLocation = new LatLng(place.getGeometry().getLocation().getLat(),
                                                    place.getGeometry().getLocation().getLng());
                                            //Log.d("Loc", String.valueOf(place.getName()));
                                            MarkerOptions markerOptions = new MarkerOptions().position(nearbyLocation).title(place.getName());
                                            Marker m = mMap.addMarker(markerOptions);
                                           m.setTag(place.getPlaceId());

                                            markers.add(m);

                                        }
                                        next_page_token = nearbySearchResponse.getNextPageToken();


                                    }
                                    else{
                                        Toast.makeText(MapsActivity.this, "No Results for applied filter", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });


            }
            else{
                Toast.makeText(this, "No more search results", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void removeMarkers(List<Marker> markers) {
        for (Marker m:markers) {
            m.remove();


        }
        markers.clear();
    }


    @Override
    public void getFilterValues(String type, Float radius) {

        Searchradius= (int)(radius*1000);
        if(type!=null){
            est_type=type.toLowerCase();
        }
        else if(type==null){
            est_type="";
        }
        shownearbyLocations(Searchradius,est_type,REQUEST_CODE_FILTER);
    }
}

