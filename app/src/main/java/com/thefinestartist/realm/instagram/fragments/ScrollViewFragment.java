package com.thefinestartist.realm.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout_;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.instagram.networks.Api;
import com.thefinestartist.realm.instagram.instagram.networks.InstagramAPI;
import com.thefinestartist.realm.instagram.realm.Post;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class ScrollViewFragment extends BaseFragment implements ViewTreeObserver.OnScrollChangedListener, SwipeRefreshLayout_.OnRefreshListener {

    Realm realm;

    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout_ swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getInstance(new RealmConfiguration.Builder(getActivity()).name("ScrollView.realm").build());

        View view = inflater.inflate(R.layout.fragment_scrollview, null);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.grey);

        swipeRefreshLayout.setOnRefreshListener(this);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(this);

        onRefresh();
        return view;
    }

    /**
     * OnScrollChangedListener
     */
    private static final int THRESHOLD = 800;

    @Override
    public void onScrollChanged() {
        if (!swipeRefreshLayout.isRefreshing() && linearLayout.getHeight() <= scrollView.getScrollY() + scrollView.getHeight() + THRESHOLD) {
            loadMoreData();
        }
    }

    private int page;

    private void loadMoreData() {
        swipeRefreshLayout.setRefreshing(true);
        Api.getFeed(page++, new Api.OnResponseListener<List<Post>>() {
            @Override
            public void onResponseRetrieved(List<Post> posts, Exception e) {
                swipeRefreshLayout.setRefreshing(false);

                realm.beginTransaction();
                realm.copyToRealm(posts);
                realm.commitTransaction();

                for (Post post : posts)
                    addItem(post);
            }
        });
    }

    /**
     * OnRefreshListener
     */
    @Override
    public void onRefresh() {
        realm.beginTransaction();
        realm.clear(Post.class);
        realm.commitTransaction();

        clearItems();

        page = 0;
        loadMoreData();

        InstagramAPI.getTag(null);
    }

    /**
     * LinearLayout
     */
    private void clearItems() {
        linearLayout.removeAllViews();
    }

    private void addItem(Post post) {
        View view = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_2, null);
        TextView title = (TextView) view.findViewById(android.R.id.text1);
        TextView message = (TextView) view.findViewById(android.R.id.text2);
        title.setText(post.getTitle());
        message.setText(post.getMessage());
        linearLayout.addView(view);
    }
}
