package com.thefinestartist.realm.instagram.events;

/**
 * Created by TheFinestArtist on 8/18/15.
 */
public abstract class OnPostUpdateEvent {

    private String next;

    public OnPostUpdateEvent(String next) {
        this.next = next;
    }

    public String getNext() {
        return next;
    }
}
