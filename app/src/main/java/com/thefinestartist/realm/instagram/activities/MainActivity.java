package com.thefinestartist.realm.instagram.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.fragments.CardViewFragment;
import com.thefinestartist.realm.instagram.fragments.ListViewFragment;
import com.thefinestartist.realm.instagram.fragments.RecyclerViewFragment;
import com.thefinestartist.realm.instagram.fragments.ScrollViewFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int ITEM_COUNT = 4;

    /**
     * Fragment Adapter
     */
    public static class MainFragmentAdapter extends FragmentPagerAdapter {
        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return ITEM_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ScrollViewFragment();
                case 1:
                    return new ListViewFragment();
                case 2:
                    return new RecyclerViewFragment();
                case 3:
                default:
                    return new CardViewFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ScrollView";
                case 1:
                    return "ListView";
                case 2:
                    return "RecyclerView";
                case 3:
                default:
                    return "CardView";
            }
        }
    }

    MainFragmentAdapter adapter;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.clear(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(ITEM_COUNT - 1);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}