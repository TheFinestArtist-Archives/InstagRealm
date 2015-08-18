package com.thefinestartist.realm.instagram.events;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class OnCardViewUpdateEvent extends OnPostUpdateEvent {

    public OnCardViewUpdateEvent() {
        super();
    }

    public OnCardViewUpdateEvent(String next) {
        super(next);
    }
}