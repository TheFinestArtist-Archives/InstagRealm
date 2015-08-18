package com.thefinestartist.realm.instagram.events;

/**
 * Created by TheFinestArtist on 8/18/15.
 */
public abstract class OnPostUpdateEvent {

    private String next;

    private boolean failed;

    public OnPostUpdateEvent(String next) {
        this.next = next;
        this.failed = false;
    }

    public OnPostUpdateEvent() {
        this.failed = true;
    }

    public String getNext() {
        return next;
    }

    public boolean isFailed() {
        return failed;
    }
}
