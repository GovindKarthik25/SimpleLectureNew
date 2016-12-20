package com.simplelecture.main.adapters;

/**
 * Created by karthik.rao on 04-02-2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.simplelecture.main.fragments.ScreenSlidePageFragment;
import com.simplelecture.main.fragments.SplashPromoSlidePageFragment;
import com.simplelecture.main.model.viewmodel.SplashResponseModel;

import java.util.List;

/**
 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
 * sequence.
 */
public class SplashPromoSlidePagerAdapter extends FragmentStatePagerAdapter {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private final List<SplashResponseModel> splashResponseModelLstAray;

    public SplashPromoSlidePagerAdapter(FragmentManager fm, List<SplashResponseModel> splashResponseModelLstArray) {
        super(fm);

        splashResponseModelLstAray = splashResponseModelLstArray;
    }

    @Override
    public Fragment getItem(int position) {

        return SplashPromoSlidePageFragment.create(position, splashResponseModelLstAray);

    }

    @Override
    public int getCount() {
        return splashResponseModelLstAray.size();
    }
}
