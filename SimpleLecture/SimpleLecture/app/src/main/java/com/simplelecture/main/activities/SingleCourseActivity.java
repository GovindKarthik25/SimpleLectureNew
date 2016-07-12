package com.simplelecture.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.CourseBenifitsFragment;
import com.simplelecture.main.fragments.CourseDescriptionFragment;
import com.simplelecture.main.fragments.CourseFeatureFragment;
import com.simplelecture.main.fragments.CourseIndexFragment;
import com.simplelecture.main.fragments.FAQFragment;
import com.simplelecture.main.fragments.ReviewFragment;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SingleCourseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;

    Intent intent;

    String cId;

    CourseDetailsResponseModel courseDetailsResponseModelObj;

   /* @Override
    public void onBackPressed() {

        NavUtils.navigateUpFromSameTask(this);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(SingleCourseActivity.this);
        setContentView(R.layout.activity_single_course);

        intent = getIntent();
        if (intent.hasExtra("courseDetails")) {
            courseDetailsResponseModelObj = (CourseDetailsResponseModel) intent.getSerializableExtra("courseDetails");

            Log.i("courseDetails***", courseDetailsResponseModelObj.toString());
        }

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(courseDetailsResponseModelObj.getcName());
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(getSupportActionBar().getTitle().toString()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(2);
        tab.select();

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });

        /*LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CourseFeatureFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseFeature));
        adapter.addFrag(new CourseDescriptionFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseDescription));
        adapter.addFrag(new CourseIndexFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseIndex));
        adapter.addFrag(new CourseBenifitsFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseBenifits));
        adapter.addFrag(new FAQFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.fAQ));
        adapter.addFrag(new ReviewFragment(), getResources().getString(R.string.review));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
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
