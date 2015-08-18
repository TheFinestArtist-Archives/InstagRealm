package com.thefinestartist.realm.instagram.instagram.callbacks;

import com.thefinestartist.realm.instagram.realm.Post;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class TagsCallback {

    public Pagination pagination;
    public Post[] data;

    public class Pagination {
        public String next_max_tag_id;
    }
}