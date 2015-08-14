package com.thefinestartist.realm.instagram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by TheFinestArtist on 5/8/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.add(this);
    }

}