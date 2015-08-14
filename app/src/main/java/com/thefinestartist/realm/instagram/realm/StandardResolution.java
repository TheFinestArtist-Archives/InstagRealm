package com.thefinestartist.realm.instagram.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TheFinestArtist on 8/15/15.
 */
public class StandardResolution extends RealmObject {

    @PrimaryKey
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
