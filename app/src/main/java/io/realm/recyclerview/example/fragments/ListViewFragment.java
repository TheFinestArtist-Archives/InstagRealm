package io.realm.recyclerview.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout_;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.recyclerview.example.R;
import io.realm.recyclerview.example.adapters.ListViewAdapter;
import io.realm.recyclerview.example.models.Post;
import io.realm.recyclerview.example.networks.Api;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class ListViewFragment extends Fragment implements AbsListView.OnScrollListener, SwipeRefreshLayout_.OnRefreshListener {

    private static final String REALM_NAME = "ListView.realm";
    Realm realm;

    ListView listView;
    ListAdapter adapter;
    SwipeRefreshLayout_ swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getInstance(new RealmConfiguration.Builder(getActivity()).name(REALM_NAME).build());

        View view = inflater.inflate(R.layout.fragment_listview, null);
        listView = (ListView) view.findViewById(android.R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout_) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.realm_red, R.color.realm_blue);

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
