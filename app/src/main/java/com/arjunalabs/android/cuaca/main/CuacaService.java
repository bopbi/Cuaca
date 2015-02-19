package com.arjunalabs.android.cuaca.main;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 2/19/15.
 */
public class CuacaService extends Service {


    public static final int GET_LOCATION = 1;
    public static final int GET_WEATHER = 2;
    public static final String IntentKey = "CUACA";
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";

    private static final long LOCATION_TIMEOUT_SECONDS = 20;
    private RestAdapter restAdapter;
    private OpenWeatherApi openWeatherApi;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org")
                    .setClient(new OkClient())
                    .build();

            openWeatherApi = restAdapter.create(OpenWeatherApi.class);
        }

        int command = intent.getIntExtra(IntentKey, -1);
        switch (command) {
            case GET_LOCATION:
                updateLocation();
                break;
            case GET_WEATHER:
                updateWeather(intent.getDoubleExtra(LAT, 0), intent.getDoubleExtra(LNG, 0));
                break;

        }
        return START_STICKY;
    }

    private void updateLocation() {
        final LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        LocationUtil locationUtil = new LocationUtil(locationManager);
        locationUtil.getCurrentLocation().timeout(LOCATION_TIMEOUT_SECONDS, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Location>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Location location) {
                updateWeather(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void updateWeather(double latitude, double longitude) {
       openWeatherApi.getCurrentWeather(latitude, longitude)
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<CurrentWeather>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.e("Cuaca", "Error");
                   }

                   @Override
                   public void onNext(CurrentWeather currentWeather) {
                        if (currentWeather.getWeather().size() > 0) {
                            Log.i("Result",currentWeather.getWeather().get(0).getDescription());
                        }
                   }
               });
    }

}
