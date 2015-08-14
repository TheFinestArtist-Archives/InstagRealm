package com.thefinestartist.realm.instagram.realm;

import io.realm.RealmObject;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class Post extends RealmObject {

    private String title;
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
