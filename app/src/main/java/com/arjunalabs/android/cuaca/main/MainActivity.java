package com.arjunalabs.android.cuaca.main;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.arjunalabs.android.cuaca.R;
import com.arjunalabs.android.cuaca.base.BaseLocationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
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
        Intent i = new Intent(this, CuacaService.class);
        if (mLastLocation != null) {
            // update weather information
            i.putExtra(CuacaService.IntentKey, CuacaService.GET_WEATHER);
            i.putExtra(CuacaService.LAT, mLastLocation.getLatitude());
            i.putExtra(CuacaService.LNG, mLastLocation.getLongitude());
        } else {
            // update location first
            i.putExtra(CuacaService.IntentKey, CuacaService.GET_LOCATION);
        }
        startService(i);
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
