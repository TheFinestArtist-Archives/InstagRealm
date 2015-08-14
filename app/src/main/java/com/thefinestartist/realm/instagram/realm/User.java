package com.thefinestartist.realm.instagram.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TheFinestArtist on 8/15/15.
 */
public class User extends RealmObject {

    @PrimaryKey
    private String username;
    private String profile_picture;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
