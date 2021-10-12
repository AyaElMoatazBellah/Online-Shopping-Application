package com.example.online_shopping;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locmanager;
    MyLocationListener loclistener;
    ImageButton MyLocation;
    Button Proceed;
    static public String address;
    EditText TxtLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        MyLocation=(ImageButton)findViewById(R.id.Btn_MyLocation);
        Proceed=(Button)findViewById(R.id.Btn_Proceed);
        TxtLocation=(EditText) findViewById(R.id.Txt_Location);
        loclistener = new MyLocationListener(getApplicationContext());
        locmanager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,loclistener);
        }
        catch (SecurityException e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong !",Toast.LENGTH_SHORT).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        MyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc=null;
                try {
                    loc = locmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(),"you didn't allow to access the current location",Toast.LENGTH_LONG).show();
                }

                if(loc!=null)
                {
                    LatLng myPosition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try {
                        addressList=coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                                address+=addressList.get(0).getAddressLine(i)+",";
                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My location").snippet(address)).setDraggable(true);
                            TxtLocation.setText(address);
                        }
                    } catch (IOException e) {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                }
                else
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determind",Toast.LENGTH_LONG).show();
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener(){
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        address="";
                        for(int i=0 ; i<=addressList.get(0).getMaxAddressLineIndex();i++)
                            address+=addressList.get(0).getAddressLine(i);
                        TxtLocation.setText(address);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No address for this location", Toast.LENGTH_LONG).show();
                        TxtLocation.getText().clear();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Can't get the address , check your network",Toast.LENGTH_LONG).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList ;
                try {
                    addressList = geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        String address = "";
                        for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++) {
                            address += addressList.get(0).getAddressLine(i) + ", ";
                        }
                        TxtLocation.setText(address);
                    }
                    else
                        {
                            Toast.makeText(getApplicationContext(),"No address for this location !",Toast.LENGTH_SHORT);
                            TxtLocation.getText().clear();
                        }

                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(),"Can't get the address , Check Your Network!",Toast.LENGTH_SHORT);
                }



            }
        });
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(TxtLocation.getText().toString()).equals(""))
                {
                    Intent i = new Intent(LocationActivity.this,CheckoutActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
