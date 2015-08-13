package io.realm.recyclerview.example.networks;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.recyclerview.example.models.Post;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class Api {

    public interface OnResponseListener<E> {
        void onResponseRetrieved(E object, Exception e);
    }

    private static final int POST_PER_PAGE = 15;
    private static final int DELAY = 2000;

    public static void getFeed(final int page, final OnResponseListener<List<Post>> listener) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Post> posts = new ArrayList<Post>();
                for (int i = 0; i < POST_PER_PAGE; i++) {
                    Post post = new Post();
                    post.setTitle("Title #" + (page * POST_PER_PAGE + i + 1));
                    post.setMessage(getRandomString(120));
                    posts.add(post);
                }

                if (listener != null)
                    listener.onResponseRetrieved(posts, null);
            }
        }, DELAY);
    }

    private static final String ALLOWED_CHARACTERS = "qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
