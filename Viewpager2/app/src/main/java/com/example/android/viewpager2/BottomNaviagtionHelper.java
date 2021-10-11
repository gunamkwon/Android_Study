package com.example.android.viewpager2;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNaviagtionHelper{

    public static void enableNavigation(final Context context, BottomNavigationView view) {
        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.first:
                        Intent intent1 = new Intent(context, MainActivity.class );
                        context.startActivity(intent1);
                        break;
                    case R.id.second:
                        Intent intent2 = new Intent(context, MainActivity2.class);
                        context.startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }
}
