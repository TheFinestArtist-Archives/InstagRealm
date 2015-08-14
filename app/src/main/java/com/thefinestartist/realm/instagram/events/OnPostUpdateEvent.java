package com.thefinestartist.realm.instagram.events;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class OnPostUpdateEvent {
    String databaseName;

    public OnPostUpdateEvent(String databaseName) {
        this.databaseName = databaseName;
    }
}
