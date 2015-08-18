package com.thefinestartist.realm.instagram.events;

import com.thefinestartist.realm.instagram.realm.Post;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class OnScrollViewUpdateEvent extends OnPostUpdateEvent {

    Post[] posts;

    public OnScrollViewUpdateEvent() {
        super();
    }

    public OnScrollViewUpdateEvent(String next, Post[] posts) {
        super(next);
        this.posts = posts;
    }

    public Post[] getPosts() {
        return posts;
    }
}
