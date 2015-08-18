package com.thefinestartist.realm.instagram.widgets;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.thefinestartist.realm.instagram.R;

/**
 * Globee Toast
 *
 * @author The Finest Artist
 */
public class SnackBar {

    private SnackBar() {
    }

    private static int getColor(@NonNull Context context, @ColorRes int color) {
        return context.getResources().getColor(color);
    }

    public static void alert(@NonNull final Activity activity, final String message) {
        final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content == null)
            return;

        Logger.e(message);

        Snackbar snackbar = Snackbar.make(content, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getColor(activity, R.color.black));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getColor(activity, R.color.accent));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getColor(activity, R.color.black));
        textView.setLineSpacing(0, 1.1f);
        textView.setIncludeFontPadding(false);
        snackbar.show();
    }

}//end of class
