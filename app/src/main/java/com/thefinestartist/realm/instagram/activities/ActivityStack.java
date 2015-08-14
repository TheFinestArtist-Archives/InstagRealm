package com.thefinestartist.realm.instagram.activities;

import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * Created by TheFinestArtist on 7/5/15.
 */
public class ActivityStack {

    private static Stack<AppCompatActivity> classes = new Stack<AppCompatActivity>();

    public static void clear(AppCompatActivity activity) {
        for (final AppCompatActivity act : classes) {
            if (act != null && act != activity) {
                act.finish();
            }
        }
    }

    public static void add(AppCompatActivity activity) {
        classes.push(activity);
    }
}
