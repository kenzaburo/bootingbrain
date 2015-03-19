package com.academic.idiots.bootingbrain;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Thien on 3/18/2015.
 */
public class BootingAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/fonts/fontastique.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

    }
}
