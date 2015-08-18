package com.thefinestartist.realm.instagram.fragments;

import android.net.Uri;
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

import com.facebook.drawee.view.SimpleDraweeView;
import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.databases.ScrollViewDatabase;
import com.thefinestartist.realm.instagram.events.OnScrollViewUpdateEvent;
import com.thefinestartist.realm.instagram.instagram.networks.InstagramAPI;
import com.thefinestartist.realm.instagram.realm.Post;
import com.thefinestartist.royal.Royal;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

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

    public ScrollViewFragment() {
        clazz = ScrollViewDatabase.class;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Royal.getRealmOf(ScrollViewDatabase.class);

        View view = inflater.inflate(R.layout.fragment_scrollview, null);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.grey);

        swipeRefreshLayout.setOnRefreshListener(this);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(this);

        loadData();
        return view;
    }

    /**
     * OnScrollChangedListener
     */
    private static final int THRESHOLD = 800;

    @Override
    public void onScrollChanged() {
        if (!swipeRefreshLayout.isRefreshing() && linearLayout.getHeight() <= scrollView.getScrollY() + scrollView.getHeight() + THRESHOLD) {
            loadData();
        }
    }

    @Override
    void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        super.loadData();
    }

    /**
     * OnRefreshListener
     */
    @Override
    public void onRefresh() {
        clearViews();

        next = null;

        Realm realm = Royal.getRealmOf(clazz);
        realm.beginTransaction();
        realm.clear(Post.class);
        realm.commitTransaction();

        loadData();

        InstagramAPI.getTag(getActivity(), ScrollViewDatabase.class, null);
    }

    public void onEvent(OnScrollViewUpdateEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        if (event.isFailed())
            return;

        next = event.getNext();
        for (Post post : event.getPosts())
            addItem(post);
    }

    /**
     * LinearLayout
     */
    private void clearViews() {
        linearLayout.removeAllViews();
    }

    private void addItem(Post post) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_post, null);
        SimpleDraweeView profile = (SimpleDraweeView) view.findViewById(R.id.profile);
        TextView username = (TextView) view.findViewById(R.id.username);
        SimpleDraweeView photo = (SimpleDraweeView) view.findViewById(R.id.photo);
        TextView message = (TextView) view.findViewById(R.id.message);

        profile.setImageURI(Uri.parse(post.getUser().getProfile_picture()));
        username.setText(post.getUser().getUsername());
        photo.setImageURI(Uri.parse(post.getImages().getStandard_resolution().getUrl()));
        message.setText(post.getCaption().getText().trim());

        linearLayout.addView(view);
    }
}
