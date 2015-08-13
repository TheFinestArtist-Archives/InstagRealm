package io.realm.recyclerview.example;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import io.realm.recyclerview.example.fragments.CardViewFragment;
import io.realm.recyclerview.example.fragments.ListViewFragment;
import io.realm.recyclerview.example.fragments.RecyclerViewFragment;
import io.realm.recyclerview.example.fragments.ScrollViewFragment;

public class MainActivity extends AppCompatActivity  {

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
    }

    MainFragmentAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(ITEM_COUNT - 1);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("ScrollView"));
        tabLayout.addTab(tabLayout.newTab().setText("ListView"));
        tabLayout.addTab(tabLayout.newTab().setText("RecyclerView"));
        tabLayout.addTab(tabLayout.newTab().setText("CardView"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}