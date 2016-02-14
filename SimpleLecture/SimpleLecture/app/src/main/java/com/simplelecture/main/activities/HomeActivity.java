package com.simplelecture.main.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.ComboCoursesFragment;
import com.simplelecture.main.fragments.CourseCategoriesFragment;
import com.simplelecture.main.fragments.DashboardFragment;
import com.simplelecture.main.fragments.DemoFragment;
import com.simplelecture.main.fragments.ExercisesFragment;
import com.simplelecture.main.fragments.ForumFragment;
import com.simplelecture.main.fragments.HomeFragment;
import com.simplelecture.main.fragments.MyCoursesFragment;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        displayView(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_coumbo) {
            displayView(0);
        } else if (id == R.id.nav_course) {
            displayView(1);
        } else if (id == R.id.nav_demo) {
            displayView(2);
        } else if (id == R.id.nav_my_courses) {
            displayView(3);
        } else if (id == R.id.nav_excercises) {
            displayView(4);
        } else if (id == R.id.nav_forum) {
            displayView(5);
        } else if (id == R.id.nav_dashboard) {
            displayView(6);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                setTitle("Combo Courses");
                break;
            case 1:
                fragment = new CourseCategoriesFragment();
                setTitle("Courses Categories");
                break;
            case 2:
                fragment = new DemoFragment();
                setTitle("Demo Videos");

                break;
            case 3:
                fragment = new MyCoursesFragment();
                setTitle("My Courses");

                break;
            case 4:
                fragment = new ExercisesFragment();
                setTitle("Excercies");
                break;

            case 5:
                fragment = new ForumFragment();
                setTitle("Forum");
                break;

            case 6:
                fragment = new DashboardFragment();
                setTitle("Dashboard");
                break;

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
