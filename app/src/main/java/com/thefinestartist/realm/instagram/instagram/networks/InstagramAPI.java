package com.thefinestartist.realm.instagram.instagram.networks;

import android.app.Activity;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.thefinestartist.realm.instagram.databases.CardViewDatabase;
import com.thefinestartist.realm.instagram.databases.ListViewDatabase;
import com.thefinestartist.realm.instagram.databases.RecyclerViewDatabase;
import com.thefinestartist.realm.instagram.databases.ScrollViewDatabase;
import com.thefinestartist.realm.instagram.events.OnCardViewUpdateEvent;
import com.thefinestartist.realm.instagram.events.OnListViewUpdateEvent;
import com.thefinestartist.realm.instagram.events.OnRecyclerViewUpdateEvent;
import com.thefinestartist.realm.instagram.events.OnScrollViewUpdateEvent;
import com.thefinestartist.realm.instagram.instagram.callbacks.TagsCallback;
import com.thefinestartist.realm.instagram.widgets.SnackBar;
import com.thefinestartist.royal.RoyalDatabase;
import com.thefinestartist.royal.RoyalTransaction;

import de.greenrobot.event.EventBus;
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
                    if (log.startsWith("{"))
                        Logger.json(log);
                    else
                        Logger.d(log);
                }
            })
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(END_POINT)
            .setConverter(new GsonConverter(gson))
            .setRequestInterceptor(requestInterceptor)
            .build();

    private static InstagramService instagramService = restAdapter.create(InstagramService.class);

    public static void getTag(final Activity activity, final Class<? extends RoyalDatabase> clazz, String next) {
        instagramService.getTags("art", accessToken, next, new Callback<TagsCallback>() {
            @Override
            public void success(TagsCallback tagsCallback, Response response) {
                RoyalTransaction.save(clazz, tagsCallback.data);
                if (ScrollViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnScrollViewUpdateEvent(tagsCallback.pagination.next_max_tag_id, tagsCallback.data));
                if (ListViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnListViewUpdateEvent(tagsCallback.pagination.next_max_tag_id));
                if (RecyclerViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnRecyclerViewUpdateEvent(tagsCallback.pagination.next_max_tag_id));
                if (CardViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnCardViewUpdateEvent(tagsCallback.pagination.next_max_tag_id));
            }

            @Override
            public void failure(RetrofitError error) {
                SnackBar.alert(activity, "Please check your network status!");
                if (ScrollViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnScrollViewUpdateEvent());
                if (ListViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnListViewUpdateEvent());
                if (RecyclerViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnRecyclerViewUpdateEvent());
                if (CardViewDatabase.class.equals(clazz))
                    EventBus.getDefault().post(new OnCardViewUpdateEvent());
            }
        });
    }
}