package com.arjunalabs.android.cuaca.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by bobbyadiprabowo on 2/11/15.
 */
public class MainView extends LinearLayout {

    MainPresenter mainPresenter;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainPresenter = new MainPresenter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
