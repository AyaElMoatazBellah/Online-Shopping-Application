package com.example.online_shopping;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
    private Context mycontext;
    public MyLocationListener(Context c)
    {
        mycontext=c;
    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(mycontext,location.getLatitude()+", "+location.getLongitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(mycontext,"GPS Enabled",Toast.LENGTH_SHORT);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(mycontext,"GPS Disabled",Toast.LENGTH_SHORT); }
}
