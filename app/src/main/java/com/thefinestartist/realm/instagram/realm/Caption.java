package com.thefinestartist.realm.instagram.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TheFinestArtist on 8/15/15.
 */
public class Caption extends RealmObject {

    @PrimaryKey
    private String id;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
