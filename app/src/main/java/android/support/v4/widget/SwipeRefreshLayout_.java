package android.support.v4.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by TheFinestArtist on 6/30/15.
 *
 * This Class has been made to cover bug on SwipeRefreshLayout
 * setRefreshing not working if SwipeRefreshLayout is not measured
 */
public class SwipeRefreshLayout_ extends SwipeRefreshLayout {
    public SwipeRefreshLayout_(Context context) {
        super(context);
    }

    public SwipeRefreshLayout_(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean measured;
    private boolean refreshing;

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!measured) {
            measured = true;
            super.setRefreshing(refreshing);
        }
    }


    @Override
    public void setRefreshing(boolean refreshing) {
        if (measured)
            super.setRefreshing(refreshing);
        else
            this.refreshing = refreshing;
    }
}
