package com.example.taha.firebase;

/**
 * Created by taha on 4/21/2017.
 */


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Aylar-HP on 28/09/2015.
 */
public class MyLocListener implements LocationListener {

    static public double longitude;
    static public double latitude;
    @Override
    public void onLocationChanged(Location location)
    {
        if(location != null)
        {
           longitude=location.getLatitude();
            latitude=location.getLongitude();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}

