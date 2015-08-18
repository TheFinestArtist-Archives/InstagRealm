package com.thefinestartist.realm.instagram.fragments;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.databases.CardViewDatabase;

/**
 * Created by TheFinestArtist on 6/30/15.
 */
public class CardViewFragment extends RecyclerViewFragment {

    public CardViewFragment() {
        clazz = CardViewDatabase.class;
        layoutRes = R.layout.item_card;
    }
}
