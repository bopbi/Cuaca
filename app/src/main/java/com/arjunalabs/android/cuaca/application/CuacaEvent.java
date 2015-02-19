package com.arjunalabs.android.cuaca.application;

import com.arjunalabs.android.cuaca.main.CurrentWeather;

/**
 * Created by bobbyadiprabowo on 2/19/15.
 */
public class CuacaEvent {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    private CurrentWeather currentWeather;


}
