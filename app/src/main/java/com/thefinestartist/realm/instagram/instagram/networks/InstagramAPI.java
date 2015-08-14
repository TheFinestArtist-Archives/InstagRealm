package com.thefinestartist.realm.instagram.instagram.networks;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.thefinestartist.realm.instagram.instagram.callbacks.TagsCallback;

import io.realm.RealmObject;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public class InstagramAPI {

    public static final String END_POINT = "https://api.instagram.com";
    public static String accessToken;

    private static RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Platform", "Android");
        }
    };

    private static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    private static RestAdapter restAdapter = new RestAdapter.Builder()
            .setLog(new RestAdapter.Log() {
                @Override
                public void log(String log) {
                    Logger.d(log);
                }
            })
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(END_POINT)
            .setConverter(new GsonConverter(gson))
            .setRequestInterceptor(requestInterceptor)
            .build();

    private static InstagramService instagramService = restAdapter.create(InstagramService.class);

    public static void getTag(String next) {
        instagramService.getTags("Instagram", accessToken, next, new Callback<TagsCallback>() {
            @Override
            public void success(TagsCallback tagsCallback, Response response) {
                Logger.e("getTag: success");
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}