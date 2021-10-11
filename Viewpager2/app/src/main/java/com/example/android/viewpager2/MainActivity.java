package com.example.android.viewpager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    private static final int ACTIVITY_NUM = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
        setupViewPager();

    }

    private void setupViewPager()
    {
        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(this);
        pagerAdapter.addFragment(new Fragment1());
        pagerAdapter.addFragment(new Fragment2());

        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.main_viewpager2);
        viewPager2.setAdapter(pagerAdapter);
    }

    private void setupBottomNavigation()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNaviagtionHelper.enableNavigation(mContext,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}