package com.thefinestartist.realm.instagram.fragments;

import android.support.v4.app.Fragment;

import com.thefinestartist.realm.instagram.instagram.networks.InstagramAPI;
import com.thefinestartist.royal.RoyalDatabase;

import de.greenrobot.event.EventBus;

/**
 * Created by TheFinestArtist on 8/14/15.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    Class<? extends RoyalDatabase> clazz;
    String next;

    void loadData() {
        InstagramAPI.getTag(getActivity(), clazz, next);
    }
}
