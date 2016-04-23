package me.kalehv.sunshine;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by harshadkale on 4/22/16.
 */
public class SunshineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
