package com.simplelecture.main.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.CourseCategoriesFragment;
import com.simplelecture.main.fragments.ExerciseFragment;
import com.simplelecture.main.fragments.ForumFragment;
import com.simplelecture.main.fragments.HomeFragment;
import com.simplelecture.main.fragments.LegalFragment;
import com.simplelecture.main.fragments.MyCoursesFragment;
import com.simplelecture.main.fragments.SampleVideoFragment;
import com.simplelecture.main.fragments.SupportFragment;
import com.simplelecture.main.fragments.interfaces.MainObjectReceiver;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.model.viewmodel.HomePageResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, MainObjectReceiver {

    private AlertMessageManagement alertMessageManagement;
    private SessionManager sessionManager;
    private int alertID = 0;
    private TextView textView_EmailId;
    private HomePageResponseModel homePageResponseModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(HomeActivity.this);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = SessionManager.getInstance();
        String email = Util.getFromPrefrences(getApplicationContext(), "email");
        alertMessageManagement = new AlertMessageManagement(HomeActivity.this, new AlertDialogClick());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Home"));

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawerHeader_view);
        View header = navigationView.getHeaderView(0);
        textView_EmailId = (TextView) header.findViewById(R.id.text_email);
        textView_EmailId.setText(email.isEmpty() ? "Guest" : email);

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
            alertID = 1;
            alertMessageManagement.alertDialogActivation(HomeActivity.this, 2, getResources().getString(R.string.alert), getResources().getString(R.string.AreyousurewanttocloseApp), getResources().getString(R.string.no), getResources().getString(R.string.yes));

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
            new ViewManager().gotoBillingAddressActivityView(this, false);
        } else if (id == R.id.action_Signin) {
            new ViewManager().gotoSigninView(this);
        } else if (id == R.id.action_logout) {
            return true;
        } else if (id == R.id.action_cart) {
            new ViewManager().gotoCartActivity(this);
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
            onDisplayViewMethod(1);
        } else if (id == R.id.nav_SampleVideos) {
            displayView(2);
        } else if (id == R.id.nav_dashboard) {
            onDisplayViewMethod(3);
        } /*else if (id == R.id.nav_my_courses) {
            onDisplayViewMethod(4);
        } else if (id == R.id.nav_excercises) {
            onDisplayViewMethod(5);
        } else if (id == R.id.nav_forum) {
            displayView(6);
        }*/ else if (id == R.id.nav_Support) {
            displayView(7);
        } else if (id == R.id.nav_LeagalPolicy) {
            displayView(8);
        } else if (id == R.id.nav_AboutUs) {
            displayView(9);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        //  textView_EmailId

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onDisplayViewMethod(int tag) {

        if (sessionManager.isLoginStatus()) {
            displayView(tag);
        } else {
            onShowAlert();
        }
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
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//                fragment = new DashboardFragment();
//                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_dashboard));
                break;
            case 4:
                fragment = new MyCoursesFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_mycourses));
                break;
            case 5:
                fragment = new ExerciseFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_excercies));
                break;
            case 6:
                fragment = new ForumFragment();
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_forum));
                break;
            case 7:
                if (homePageResponseModelObj != null && homePageResponseModelObj.getPageUrlSupport() != null) {
                    fragment = new SupportFragment().newInstance(homePageResponseModelObj.getPageUrlSupport(), "");
                    getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_Support));
                } else {
                    Toast.makeText(this, "Try again after some time", Toast.LENGTH_SHORT).show();
                }

                break;
            case 8:
                if (homePageResponseModelObj != null) {
                    fragment = new LegalFragment().newInstance(homePageResponseModelObj, "");
                    getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_LeagalPolicy));
                } else {
                    Toast.makeText(this, "Try again after some time", Toast.LENGTH_SHORT).show();
                }
                break;
            case 9:
                if (homePageResponseModelObj != null && homePageResponseModelObj.getPageUrlAboutUs() != null) {
                    fragment = new SupportFragment().newInstance(homePageResponseModelObj.getPageUrlAboutUs(), "");
                    getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_AboutUs));
                } else {
                    Toast.makeText(this, "Try again after some time", Toast.LENGTH_SHORT).show();
                }
                break;

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }
    }

    private void onShowAlert() {
        alertMessageManagement.alertDialogActivation(HomeActivity.this, 2, getResources().getString(R.string.alert), getResources().getString(R.string.pleaseLogin), getResources().getString(R.string.no), getResources().getString(R.string.yes));

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void onCloseApp() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public void onObjectReceived(HomePageResponseModel homePageResponseModel) {

        homePageResponseModelObj = homePageResponseModel;

    }

    private class AlertDialogClick implements AlertMessageManagement.onCustomAlertDialogListener {
        @Override
        public void onClickResult(DialogInterface dialog, int whichButton) {
            if (whichButton == -2) { // negative Button 2
                dialog.cancel();

            } else if (whichButton == -1) { //Postive -1
                if (alertID == 1) {

                    onCloseApp();
                    alertID = 0;
                } else {
                    new ViewManager().gotoLoginView(HomeActivity.this);
                }

            }
        }
    }

}
