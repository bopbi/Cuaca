package com.arjunalabs.android.cuaca.main;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.arjunalabs.android.cuaca.R;
import com.arjunalabs.android.cuaca.base.BaseLocationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by bobbyadiprabowo on 2/8/15.
 */
public class MainActivity extends BaseLocationActivity {

    int isGooglePlayAvailable = ConnectionResult.SERVICE_MISSING;
    Location mLastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkGooglePlayServicesAvailable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGooglePlayAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        GooglePlayServicesUtil.getErrorDialog(isGooglePlayAvailable, this, 400);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()) + " , "+ String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Location is null", Toast.LENGTH_SHORT).show();
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    protected void onGooglePlayRecoverCanceled() {

    }

    @Override
    protected void onGooglePlayAvailable() {
        buildGoogleApiClient();
    }
}
