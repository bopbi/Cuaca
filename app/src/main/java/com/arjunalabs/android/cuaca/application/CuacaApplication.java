package com.arjunalabs.android.cuaca.application;

import android.app.Application;

import rx.subjects.PublishSubject;

/**
 * Created by bobbyadiprabowo on 2/19/15.
 */
public class CuacaApplication extends Application {

    private PublishSubject<CuacaEvent> subject;
    @Override
    public void onCreate() {
        subject = PublishSubject.create();
        super.onCreate();
    }

    public PublishSubject<CuacaEvent> getSubject() {
        return subject;
    }
}
