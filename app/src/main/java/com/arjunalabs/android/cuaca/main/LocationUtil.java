package com.arjunalabs.android.cuaca.main;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.google.android.gms.location.LocationRequest;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by bobbyadiprabowo on 2/16/15.
 */
public class LocationUtil {

    private final LocationManager locationManager;

    public LocationUtil(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Observable<Location> getCurrentLocation() {
        return Observable.create(new Observable.OnSubscribe<Location>() {
            @Override
            public void call(final Subscriber<? super Location> subscriber) {

                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        subscriber.onNext(location);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                final Criteria locationCriteria = new Criteria();
                locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
                locationCriteria.setPowerRequirement(Criteria.POWER_LOW);
                final String locationProvider = locationManager
                        .getBestProvider(locationCriteria, true);

                Looper.prepare();

                locationManager.requestSingleUpdate(locationProvider, locationListener, Looper.myLooper());

                Looper.loop();
            }
        });
    }
}
