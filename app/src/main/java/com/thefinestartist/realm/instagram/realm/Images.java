package com.thefinestartist.realm.instagram.realm;

import io.realm.RealmObject;

/**
 * Created by TheFinestArtist on 8/15/15.
 */
public class Images extends RealmObject {

    private StandardResolution standard_resolution;

    public StandardResolution getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(StandardResolution standard_resolution) {
        this.standard_resolution = standard_resolution;
    }
}
