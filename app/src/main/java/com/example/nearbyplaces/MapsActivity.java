package com.example.nearbyplaces;



import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nearbyplaces.model.NearbyPlace;
import com.example.nearbyplaces.model.NearbySearchResponse;
import com.example.nearbyplaces.viewmodel.MapsActivityViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nearbyplaces.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final int LOCATION_PERMISSION_CODE=1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FusedLocationProviderClient client;
    Location currentLocation;
    List<NearbyPlace> nearbyPlaces;
    //PlacesClient placesClient;


    //private static final int M_MAX_ENTRIES = 5;
//    private String[] likelyPlaceNames;
//    private String[] likelyPlaceAddresses;
//    private List[] likelyPlaceAttributions;
//    private LatLng[] likelyPlaceLatLngs;

    MapsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nearbyPlaces = new ArrayList<>();

        viewModel = new ViewModelProvider(this).get(MapsActivityViewModel.class);
        client = LocationServices.getFusedLocationProviderClient(this);
        //        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
//        placesClient = Places.createClient(this);

        getLocation();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));


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
                        shownearbyLocations();
                        loadMap();
                    }
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
            }
        }
    }


    public void shownearbyLocations() {
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                Place.Field.LAT_LNG);
//
//        // Use the builder to create a FindCurrentPlaceRequest.
//        FindCurrentPlaceRequest request =
//                FindCurrentPlaceRequest.newInstance(placeFields);
//        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//
//            @SuppressLint("MissingPermission") Task<FindCurrentPlaceResponse> placeResult =
//                    placesClient.findCurrentPlace(request);
//            placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
//                @Override
//                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        FindCurrentPlaceResponse likelyPlaces = task.getResult();
//
//                        // Set the count, handling cases where less than 5 entries are returned.
//                        int count;
//                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                            count = likelyPlaces.getPlaceLikelihoods().size();
//                        } else {
//                            count = M_MAX_ENTRIES;
//                        }
//
//                        int i = 0;
//                        likelyPlaceNames = new String[count];
//                        likelyPlaceAddresses = new String[count];
//                        likelyPlaceAttributions = new List[count];
//                        likelyPlaceLatLngs = new LatLng[count];
//
//                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
//                            // Build a list of likely places to show the user.
//                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
//                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
//                            likelyPlaceAttributions[i] = placeLikelihood.getPlace()
//                                    .getAttributions();
//                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
//
//                            i++;
//                            if (i > (count - 1)) {
//                                break;
//                            }
//                        }
//
//                        // Show a dialog offering the user the list of likely places, and add a
//                        // marker at the selected place.
//                        Toast.makeText(MapsActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MapsActivity.this, likelyPlaceNames[0], Toast.LENGTH_SHORT).show();
//                    } else {
//                        Log.d("TAG", "Exception: %s", task.getException());
//                    }
//                }
//            });
//        }

viewModel.getNearbyPlaces("28.565944,77.250502",1500,BuildConfig.MAPS_API_KEY,"")
        .observe(this, new Observer<NearbySearchResponse>() {
            @Override
            public void onChanged(NearbySearchResponse nearbySearchResponse) {
              if(nearbySearchResponse!=null){
                  if(nearbySearchResponse.getResults()!=null){

                      for (NearbyPlace place: nearbySearchResponse.getResults()) {

                          nearbyPlaces.add(place);


                          LatLng nearbyLocation = new LatLng(place.getGeometry().getLocation().getLat(),
                                  place.getGeometry().getLocation().getLng());
                         Log.d("Loc", String.valueOf(place.getName()+place.getGeometry().getLocation().getLat()+place.getGeometry().getLocation().getLng()));
//                          Toast.makeText(MapsActivity.this,String.valueOf(place.getGeometry().getLocation().getLatitude()), Toast.LENGTH_SHORT).show();
                        MarkerOptions markerOptions = new MarkerOptions().position(nearbyLocation);
                        mMap.addMarker(markerOptions);

                      }

                  }
              }
            }
        });


    }

}
