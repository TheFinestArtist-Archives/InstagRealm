package com.thefinestartist.realm.instagram;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.thefinestartist.realm.instagram.databases.ListViewDatabase;
import com.thefinestartist.realm.instagram.databases.RecyclerViewDatabase;
import com.thefinestartist.realm.instagram.databases.ScrollViewDatabase;
import com.thefinestartist.royal.Royal;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Royal.joinWith(this);
        Royal.addDatabase(new ScrollViewDatabase());
        Royal.addDatabase(new ListViewDatabase());
        Royal.addDatabase(new RecyclerViewDatabase());

        Fresco.initialize(this);

        Logger.init("InstagRealm")
                .setMethodCount(3)
                .setLogLevel(LogLevel.FULL)
                .setMethodOffset(2);
    }
}
