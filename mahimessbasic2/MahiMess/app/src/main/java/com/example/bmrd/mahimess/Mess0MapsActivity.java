package com.example.bmrd.mahimess;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mess0MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static String[] latt22=new String[10];
    private static String[] lngg22=new String[10];
private int messNo;
private double lat1;
    private double lng1;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess0_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            //The key argument here must match that used in the other activity

            messNo = Integer.parseInt(value);
        }

        latt22 = MessList.sendLatt22();         //of mess
        lngg22=MessList.sendLngg22();


        lat1=MapsActivity.sendLat();
        lng1=MapsActivity.sendLng();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng yourLoc = new LatLng(lat1, lng1);
        mMap.addMarker(new MarkerOptions().position(yourLoc).title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(latt22[messNo]), Double.parseDouble(lngg22[messNo]));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Here is the mess"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sydney, 14);
        mMap.animateCamera(update);


    }
}
