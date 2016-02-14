package com.simplelecture.main.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.ComboCoursesFragment;
import com.simplelecture.main.fragments.CourseBenifitsFragment;
import com.simplelecture.main.fragments.CourseDescriptionFragment;
import com.simplelecture.main.fragments.CourseFeatureFragment;
import com.simplelecture.main.fragments.FAQFragment;
import com.simplelecture.main.fragments.ReviewFragment;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class ComboCourseActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_course);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ComboCoursesFragment(), getResources().getString(R.string.comboCourse));
        adapter.addFrag(new CourseFeatureFragment(), getResources().getString(R.string.courseFeature));
        adapter.addFrag(new CourseDescriptionFragment(), getResources().getString(R.string.courseDescription));
        adapter.addFrag(new CourseBenifitsFragment(), getResources().getString(R.string.courseBenifits));
        adapter.addFrag(new FAQFragment(), getResources().getString(R.string.fAQ));
        adapter.addFrag(new ReviewFragment(), getResources().getString(R.string.review));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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
    }
}
