package com.thefinestartist.realm.instagram.instagram.networks;

import com.thefinestartist.realm.instagram.instagram.callbacks.TagsCallback;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public interface InstagramService {

    @GET("/v1/tags/{tag-name}/media/recent")
    void getTags(@Path("tag-name") String tagName,
                 @Query("access_token") String accessToken,
                 @Query("max_tag_id") String next,
                 Callback<TagsCallback> cb);
}
