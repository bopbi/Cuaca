package com.arjunalabs.android.cuaca.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.arjunalabs.android.cuaca.R;
import com.arjunalabs.android.cuaca.application.CuacaApplication;
import com.arjunalabs.android.cuaca.application.CuacaEvent;
import com.arjunalabs.android.cuaca.base.BaseLocationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationServices;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bobbyadiprabowo on 2/8/15.
 */
public class MainActivity extends BaseLocationActivity {

    int isGooglePlayAvailable = ConnectionResult.SERVICE_MISSING;
    private Subscription subscription;
    private MainView mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainView = (MainView) findViewById(R.id.main_view);
        checkGooglePlayServicesAvailable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGooglePlayAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        GooglePlayServicesUtil.getErrorDialog(isGooglePlayAvailable, this, 400);
        subscription = ((CuacaApplication)getApplication()).getSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CuacaEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(CuacaEvent cuacaEvent) {
                        mainView.setTodayWeather(cuacaEvent.getCurrentWeather().getWeather().get(0).getDescription());
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
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
        mainView.setLastLocation(LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient));

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
