package com.arjunalabs.android.cuaca.main;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.TextView;

/**
 * Created by bobbyadiprabowo on 2/11/15.
 */
public class MainPresenter {

    private TextView textView;

    public void refresh(Context context, Location lastLocation) {
        Intent i = new Intent(context, CuacaService.class);
        if (lastLocation != null) {
            // update weather information
            i.putExtra(CuacaService.IntentKey, CuacaService.GET_WEATHER);
            i.putExtra(CuacaService.LAT, lastLocation.getLatitude());
            i.putExtra(CuacaService.LNG, lastLocation.getLongitude());
        } else {
            // update location first
            i.putExtra(CuacaService.IntentKey, CuacaService.GET_LOCATION);
        }
        context.startService(i);
    }

    public void setTodayTextView(TextView textView) {
        this.textView = textView;
    }
}
