package com.example.android.viewpager2;

import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

public class CustomPageTransformer implements ViewPager2.PageTransformer {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void transformPage(@NonNull View page, float position) {

        ViewPager2 viewpager = (ViewPager2) page.getParent().getParent();
        float margin = page.getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float offset = page.getResources().getDimensionPixelOffset(R.dimen.offset);

        float value = position * -(2*offset + margin);

        if(viewpager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(viewpager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.setTranslationX(-value);
            } else {
                page.setTranslationX(value);
            }
        } else {
            page.setTranslationY(value);
        }

    }
}
