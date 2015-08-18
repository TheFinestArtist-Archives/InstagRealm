package com.thefinestartist.realm.instagram.events;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class OnRecyclerViewUpdateEvent extends OnPostUpdateEvent {

    public OnRecyclerViewUpdateEvent() {
        super();
    }

    public OnRecyclerViewUpdateEvent(String next) {
        super(next);
    }
}
