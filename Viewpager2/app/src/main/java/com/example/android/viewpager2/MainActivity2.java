package com.example.android.viewpager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    private Context mContext = MainActivity2.this;

    private List<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setupBottomNavigation();
        setupViewPager();

    }

    private void setupViewPager()
    {
        items.add("AAAA");
        items.add("BBBB");
        items.add("CCCC");

        CustomCardViewAdapter cardViewAdapter = new CustomCardViewAdapter(items);

        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.main2_viewpager2);
        viewPager2.setPadding(130,0,130,0);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setPageTransformer(new CustomPageTransformer());
        viewPager2.setAdapter(cardViewAdapter);

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