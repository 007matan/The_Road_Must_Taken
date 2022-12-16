package com.mygame.theroadmusttaken;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {



    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.activity_fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapFragment.this.googleMap = googleMap;
                ///*
                LatLng sydney = new LatLng(-34, 151);
                // Initialize marker options
                MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                markerOptions.position(sydney);
                // Set title of marker
                markerOptions.title("Marker in Sydney");
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,5));
                // Add marker on map
                googleMap.addMarker(markerOptions);
                //*/
            }
        });
        return view;
    }
    public void markerLocation(double lat, double log, int place){
        LatLng location = new LatLng(lat, log);
        //mMap.addMarker(new MarkerOptions().position(location).title("Place number: "+place ));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        // Initialize marker options
        MarkerOptions markerOptions=new MarkerOptions();
        // Set position of marker
        markerOptions.position(location);
        // Set title of marker
        markerOptions.title("Place number: "+place);
        // Animating to zoom the marker
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,5));
        // Add marker on map
        googleMap.addMarker(markerOptions);
    }


}
