package com.simplelecture.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.simplelecture.main.fragments.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M1032185 on 2/14/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    FragmentManager manager;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);

        this.manager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    DashboardFragment dashboardFragment;

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
//        // save the appropriate reference depending on position
//        switch (position) {
//            case 0:
//                dashboardFragment = (DashboardFragment) createdFragment;
//                break;
//        }
//        return createdFragment;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//
//        if (position >= getCount()) {
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
//
//    }

    public void removeAllfragments()
    {
        if ( mFragmentList != null ) {
            for ( Fragment fragment : mFragmentList ) {
                manager.beginTransaction().remove(fragment).commit();
            }
            mFragmentList.clear();
            notifyDataSetChanged();
        }
    }
}