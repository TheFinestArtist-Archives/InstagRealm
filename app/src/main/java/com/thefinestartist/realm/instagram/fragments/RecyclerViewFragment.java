package com.thefinestartist.realm.instagram.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout_;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.adapters.RecyclerViewAdapter;
import com.thefinestartist.realm.instagram.models.Post;
import com.thefinestartist.realm.instagram.networks.Api;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class RecyclerViewFragment extends Fragment implements SwipeRefreshLayout_.OnRefreshListener, RealmChangeListener {

    String REALM_NAME;
    Realm realm;

    int layoutRes;

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout_ swipeRefreshLayout;

    private static final int ANIMATION_DURATION = 500;

    public RecyclerViewFragment() {
        REALM_NAME = "RecyclerView.realm";
        layoutRes = android.R.layout.simple_list_item_2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getInstance(new RealmConfiguration.Builder(getActivity()).name(REALM_NAME).build());

        View view = inflater.inflate(R.layout.fragment_recyclerview, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(ANIMATION_DURATION);
        itemAnimator.setRemoveDuration(ANIMATION_DURATION);
        recyclerView.setItemAnimator(itemAnimator);

        swipeRefreshLayout = (SwipeRefreshLayout_) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.grey);

        RealmResults<Post> realmResults = realm.where(Post.class).findAll();
        adapter = new RecyclerViewAdapter(realmResults, layoutRes);
        recyclerView.setAdapter(adapter);
        realm.addChangeListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                checkLoadMore();
            }
        });

        onRefresh();
        return view;
    }

    /**
     * RealmChangeListener
     */
    private int itemCount;

    @Override
    public void onChange() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (itemCount < adapter.getItemCount())
                    adapter.notifyItemRangeInserted(itemCount, adapter.getItemCount() - itemCount);
                else
                    adapter.notifyItemRangeRemoved(itemCount, itemCount - adapter.getItemCount());

                itemCount = adapter.getItemCount();
            }
        });
    }

    /**
     * Check whether to load more data or not
     */
    private static final int THRESHOLD = 3;

    private void checkLoadMore() {
        if (!swipeRefreshLayout.isRefreshing() && adapter.getItemCount() <= layoutManager.findLastVisibleItemPosition() + THRESHOLD) {
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

        page = 0;
        loadMoreData();
    }
}
