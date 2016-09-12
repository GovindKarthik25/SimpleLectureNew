package com.simplelecture.main.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ViewPagerAdapter;
import com.simplelecture.main.fragments.DashboardFragment;
import com.simplelecture.main.fragments.ExerciseFragment;
import com.simplelecture.main.fragments.ForumFragment;
import com.simplelecture.main.fragments.MyCoursesFragment;
import com.simplelecture.main.fragments.TestPapersFragment;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.util.Util;

public class DashboardActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(DashboardActivity.this);
        setContentView(R.layout.activity_dashboard);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(getSupportActionBar().getTitle().toString()));

        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int tabSelect = bundle.getInt("tabSelect");
            Log.i("tabSelect", String.valueOf(tabSelect));
            TabLayout.Tab tab = tabLayout.getTabAt(tabSelect);
            tab.select();
        }

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return false;
            }
        });

    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DashboardFragment(), getResources().getString(R.string.dashboard));
        adapter.addFrag(new MyCoursesFragment(), getResources().getString(R.string.my_courses));
        adapter.addFrag(new TestPapersFragment(), getResources().getString(R.string.test_papers));
        adapter.addFrag(new ExerciseFragment(), getResources().getString(R.string.excercise));
        adapter.addFrag(new ForumFragment(), getResources().getString(R.string.forum));
        // adapter.addFrag(new SupportFragment(), getResources().getString(R.string.support));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
