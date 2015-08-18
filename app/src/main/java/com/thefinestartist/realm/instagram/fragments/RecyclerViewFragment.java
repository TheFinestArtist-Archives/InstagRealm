package com.thefinestartist.realm.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout_;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.adapters.RecyclerViewAdapter;
import com.thefinestartist.realm.instagram.databases.RecyclerViewDatabase;
import com.thefinestartist.realm.instagram.events.OnRecyclerViewUpdateEvent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class RecyclerViewFragment extends BaseFragment implements SwipeRefreshLayout_.OnRefreshListener {

    int layoutRes;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout_ swipeRefreshLayout;

    private static final int ANIMATION_DURATION = 500;

    public RecyclerViewFragment() {
        clazz = RecyclerViewDatabase.class;
        layoutRes = android.R.layout.simple_list_item_2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, null);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(ANIMATION_DURATION);
        itemAnimator.setRemoveDuration(ANIMATION_DURATION);
        recyclerView.setItemAnimator(itemAnimator);

        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.grey);

        adapter = new RecyclerViewAdapter(clazz, layoutRes);
        recyclerView.setAdapter(adapter);

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
     * Check whether to load more data or not
     */
    private static final int THRESHOLD = 3;

    private void checkLoadMore() {
        if (!swipeRefreshLayout.isRefreshing() && adapter.getItemCount() <= layoutManager.findLastVisibleItemPosition() + THRESHOLD) {
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
        next = null;
        loadData();
    }

    /**
     * onEvent
     */
    int itemCount = 0;

    public void onEvent(OnRecyclerViewUpdateEvent event) {
        if (itemCount < adapter.getItemCount())
            adapter.notifyItemRangeInserted(itemCount, adapter.getItemCount() - itemCount);
        else
            adapter.notifyItemRangeRemoved(itemCount, itemCount - adapter.getItemCount());

        itemCount = adapter.getItemCount();
    }
}
