package com.simplelecture.main.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.HomeActivity;
import com.simplelecture.main.adapters.SelectYourCourseAdapter;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.SelectMyCourseResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raos on 6/25/2016.
 */
public class SelectYourCoursesFragment extends DialogFragment implements View.OnClickListener, NetworkLayer {

    private ListView myCourseListView;
    private ListAdapter listcourseAdapter;

    private GoogleApiClient client;
    private AlertMessageManagement alertMessageManagement;
    private boolean param_get_selectCourses = false;


    private SnackBarManagement snack;
    private String uId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProgressDialog pd;
    private CoordinatorLayout coordinatorLayout;
    private String mParam1;
    private String mParam2;

    private List<SelectMyCourseResponseModel> selectMyCourseLstArray = null;
    private Button submitButton;
    private LinearLayout linerlayoutCourse;


    public SelectYourCoursesFragment() {
        // Required empty public constructor
    }

    public static SelectYourCoursesFragment newInstance(String param1, String param2) {
        SelectYourCoursesFragment fragment = new SelectYourCoursesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            snack = new SnackBarManagement(getContext());
            alertMessageManagement = new AlertMessageManagement(getContext());

            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

            loadGetMyCourses();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.activity_select_your_courses, container, false);
        linerlayoutCourse = (LinearLayout) convertView.findViewById(R.id.linerlayoutCourse);

        myCourseListView = (ListView) convertView.findViewById(R.id.myCourseListView);
        submitButton = (Button) convertView.findViewById(R.id.submitButton);
        linerlayoutCourse.setVisibility(LinearLayout.GONE);


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        submitButton.setOnClickListener(this);

        return convertView;
    }


    private void loadGetMyCourses() {
        try {
            if (selectMyCourseLstArray == null) {
                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    param_get_selectCourses = true;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                    ApiService.getApiService().doGetSelectYourCourse(getActivity(), SelectYourCoursesFragment.this);
                } else {
                    alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
                }
            } else {
                linerlayoutCourse.setVisibility(LinearLayout.VISIBLE);
                listcourseAdapter = new SelectYourCourseAdapter(getActivity(), selectMyCourseLstArray);
                myCourseListView.setAdapter(listcourseAdapter);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        try {
            if (v == submitButton) {
                boolean courseSelected = false;
                for (SelectMyCourseResponseModel selectMyCourseResponseModelObj : selectMyCourseLstArray) {
                    if (selectMyCourseResponseModelObj != null && selectMyCourseResponseModelObj.isSelected()) {
                        courseSelected = true;

                        String selectedID = selectMyCourseResponseModelObj.getId();

                       // getDialog().dismiss();

                        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(mParam1);
                        if (prev != null) {
                            DialogFragment df = (DialogFragment) prev;
                            df.dismiss();
                        }

                        Log.i("mParam1-->", mParam1);

                        Fragment fragment = null;

                        switch (mParam1) {
                            case "SplashActivity":
                                Util.storeToPrefrences(getActivity(), "SelectYourCategoryID", selectedID.toString());
                                new ViewManager().gotoHomeView(getActivity());
                                break;
                            case "HomeFragment":
                                Util.storeToPrefrences(getActivity(), "SelectYourCategoryID", selectedID.toString());

                                fragment = new HomeFragment();
                                ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.navigation_Home));
                                break;
                            case "CourseCategoryFragment":
                                Util.storeToPrefrences(getActivity(), "CourseCategoryCategoryID", selectedID.toString());

                                fragment = new CourseCategoriesFragment();
                                ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_courseCategories));
                                break;
                            case "SampleVideoFragment":
                                fragment = new SampleVideoFragment();
                                ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_demo));
                                break;

                        }

                        if (!mParam1.equalsIgnoreCase("SplashActivity")) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_container, fragment);
                            fragmentTransaction.commit();
                        }

                    }
                }

                if (!courseSelected) {
                    alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", "Please select the Course", "OK", "");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void parseResponse(String response) {

        pd.cancel();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        if (param_get_selectCourses) {

            JsonArray jarray = parser.parse(response).getAsJsonArray();

            selectMyCourseLstArray = new ArrayList<SelectMyCourseResponseModel>();
            for (JsonElement obj : jarray) {
                SelectMyCourseResponseModel selectMyCourseResponseModelObj = gson.fromJson(obj, SelectMyCourseResponseModel.class);
                selectMyCourseLstArray.add(selectMyCourseResponseModelObj);
            }

            linerlayoutCourse.setVisibility(LinearLayout.VISIBLE);

            listcourseAdapter = new SelectYourCourseAdapter(getActivity(), selectMyCourseLstArray);
            myCourseListView.setAdapter(listcourseAdapter);
        }

    }

    @Override
    public void showError(String error) {
        if (pd.isShowing()) {
            pd.cancel();
        }


    }
}
