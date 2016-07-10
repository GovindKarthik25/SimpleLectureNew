package com.simplelecture.main.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.CourseCategoriesFragment;
import com.simplelecture.main.fragments.DashboardFragment;
import com.simplelecture.main.fragments.ExercisesFragment;
import com.simplelecture.main.fragments.ForumFragment;
import com.simplelecture.main.fragments.HomeFragment;
import com.simplelecture.main.fragments.MyCoursesFragment;
import com.simplelecture.main.fragments.SampleVideoFragment;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(HomeActivity.this);
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

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Home"));
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
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        menu.findItem(R.id.action_filter).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            new ViewManager().gotoLoginView(this);
        } else if (id == R.id.action_myProfile) {
            return true;
        } else if (id == R.id.action_Signin) {
            new ViewManager().gotoSigninView(this);
        } else if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            displayView(0);
        } else if (id == R.id.nav_course) {
            displayView(1);
        } else if (id == R.id.nav_SampleVideos) {
            displayView(2);
        } else if (id == R.id.nav_dashboard) {
            displayView(3);
        } else if (id == R.id.nav_my_courses) {
            displayView(4);
        } else if (id == R.id.nav_excercises) {
            displayView(5);
        } else if (id == R.id.nav_forum) {
            displayView(6);
        } else if (id == R.id.nav_Support) {
            displayView(6);
        } else if (id == R.id.nav_LeagalPolicy) {
            displayView(6);
        } else if (id == R.id.nav_AboutUs) {
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
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_Home));
                break;
            case 1:
                fragment = new CourseCategoriesFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_courseCategories));
                break;
            case 2:
                fragment = new SampleVideoFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_demo));
                break;
            case 3:
                fragment = new DashboardFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_dashboard));
                break;
            case 4:
                fragment = new MyCoursesFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_mycourses));
                break;
            case 5:
                fragment = new ExercisesFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_excercies));
                break;
            case 6:
                fragment = new ForumFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_forum));
                break;
            case 7:
                fragment = new DashboardFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_Support));
                break;
            case 8:
                fragment = new DashboardFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_LeagalPolicy));
                break;
            case 9:
                fragment = new DashboardFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_AboutUs));
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
