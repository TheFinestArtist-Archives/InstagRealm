package com.thefinestartist.realm.instagram.instagram.networks;

import com.thefinestartist.realm.instagram.instagram.callbacks.TagsCallback;

import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class InstagramAPI implements InstagramService {

    public static String CLIENT_ID = "8275787745ef444c8ad78eaa89b701f3";
    public static String CLIENT_SECRET = "48df1ecee0dd4745a365ce1c5edb6366";
    public static String REDIRECT_URI = "http://thefinestartist.com";
    public static String END_POINT = "https://api.instagram.com";

    @Override
    public void getTags(@Path("tag-name") String tagName,
                        @Query("access_token") String accessToken,
                        @Query("max_tag_id") String next,
                        Callback<TagsCallback> cb) {
    }
}