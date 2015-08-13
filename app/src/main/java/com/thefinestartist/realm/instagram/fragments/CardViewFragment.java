package com.thefinestartist.realm.instagram.fragments;

import com.thefinestartist.realm.instagram.R;

/**
 * Created by TheFinestArtist on 6/30/15.
 */
public class CardViewFragment extends RecyclerViewFragment {

    public CardViewFragment() {
        REALM_NAME = "CardView.realm";
        layoutRes = R.layout.item_cardview;
    }
}
