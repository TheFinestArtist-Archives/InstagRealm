package io.realm.recyclerview.example.fragments;

import io.realm.recyclerview.example.R;

/**
 * Created by TheFinestArtist on 6/30/15.
 */
public class CardViewFragment extends RecyclerViewFragment {

    public CardViewFragment() {
        REALM_NAME = "CardView.realm";
        layoutRes = R.layout.item_cardview;
    }
}
