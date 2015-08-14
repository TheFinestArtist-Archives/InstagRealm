package com.thefinestartist.realm.instagram;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("RecyclerView Example")
                .setMethodCount(3)
                .setLogLevel(LogLevel.FULL)
                .setMethodOffset(2);
    }
}
