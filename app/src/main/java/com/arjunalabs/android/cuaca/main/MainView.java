package com.arjunalabs.android.cuaca.main;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.arjunalabs.android.cuaca.R;

/**
 * Created by bobbyadiprabowo on 2/11/15.
 */
public class MainView extends LinearLayout {

    MainPresenter mainPresenter;

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    Location lastLocation;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainPresenter = new MainPresenter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Button refreshButton = (Button) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.refresh(getContext(), lastLocation);
            }
        });
    }
}
