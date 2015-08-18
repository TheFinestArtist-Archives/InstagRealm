package com.thefinestartist.realm.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout_;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.adapters.ListViewAdapter;
import com.thefinestartist.realm.instagram.databases.ListViewDatabase;
import com.thefinestartist.realm.instagram.events.OnListViewUpdateEvent;
import com.thefinestartist.realm.instagram.realm.Post;
import com.thefinestartist.royal.Royal;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class ListViewFragment extends BaseFragment implements AbsListView.OnScrollListener, SwipeRefreshLayout_.OnRefreshListener {

    Realm realm;

    @Bind(android.R.id.list)
    ListView listView;
    ListAdapter adapter;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout_ swipeRefreshLayout;

    public ListViewFragment() {
        clazz = ListViewDatabase.class;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Royal.getRealmOf(ListViewDatabase.class);

        View view = inflater.inflate(R.layout.fragment_listview, null);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.grey);

        RealmResults<Post> realmResults = realm.where(Post.class).findAll();
        adapter = new ListViewAdapter(getActivity(), realmResults);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnScrollListener(this);

        onRefresh();
        return view;
    }

    /**
     * OnScrollListener
     */
    private static final int THRESHOLD = 3;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!swipeRefreshLayout.isRefreshing() && (totalItemCount - visibleItemCount) <= (firstVisibleItem + THRESHOLD)) {
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
        Realm realm = Royal.getRealmOf(clazz);
        realm.beginTransaction();
        realm.clear(Post.class);
        realm.commitTransaction();
        loadData();
    }

    public void onEvent(OnListViewUpdateEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        next = event.getNext();
    }
}
