package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.MyCoursesAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.CourseDetailsController;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardMyCoursesResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCoursesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCoursesFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView myCourse_recycler_view;
    private CoordinatorLayout coordinatorLayout;
    private MyCoursesAdapter myCoursesAdapter;
    private ProgressDialog pd;
    private String param_get_ServiceCallResult = "";
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private String cID;

    private List<DashboardMyCoursesResponseModel> dashboardMyCoursesResponseModelArray = new ArrayList<DashboardMyCoursesResponseModel>();
    private CourseDetailsResponseModel courseDetailsResponseModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCoursesFragment newInstance(String param1, String param2) {
        MyCoursesFragment fragment = new MyCoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyCoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setRetainInstance(true);
            snack = new SnackBarManagement(getContext());
            alertMessageManagement = new AlertMessageManagement(getContext());

            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_my_courses, container, false);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
        myCourse_recycler_view = (RecyclerView) convertView.findViewById(R.id.myCourse_recycler_view);

        return convertView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {

            loadGetDashboardMyCourse();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            myCourse_recycler_view.setLayoutManager(linearLayoutManager);

            if (dashboardMyCoursesResponseModelArray != null) {
                myCoursesAdapter = new MyCoursesAdapter(getActivity(), dashboardMyCoursesResponseModelArray);
                myCourse_recycler_view.setAdapter(myCoursesAdapter);

                myCoursesAdapter.setOnItemClickListener(onItemClickListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadGetDashboardMyCourse() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_MYCOURSES;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardMyCourse(getActivity(), MyCoursesFragment.this);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGetCourse(String courseId) {
        try {
            try {
                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    param_get_ServiceCallResult = Constants.GET_COURSEDETAILS;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                    ApiService.getApiService().doGetCourseDetails(getActivity(), MyCoursesFragment.this, courseId);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private DashboardMyCoursesResponseModel dashboardMyCoursesResponseModelObj;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                dashboardMyCoursesResponseModelObj = dashboardMyCoursesResponseModelArray.get(position);
                cID = dashboardMyCoursesResponseModelObj.getCourseId();
                loadGetCourse(cID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void parseResponse(String response) {

        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            JsonArray jArray;
            JsonParser parser = new JsonParser();

            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_MYCOURSES)) {


                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");
                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        DashboardMyCoursesResponseModel dashboardMyCoursesResponseModelObj = gson.fromJson(obj, DashboardMyCoursesResponseModel.class);
                        dashboardMyCoursesResponseModelArray.add(dashboardMyCoursesResponseModelObj);
                    }

                    myCoursesAdapter = new MyCoursesAdapter(getActivity(), dashboardMyCoursesResponseModelArray);
                    myCourse_recycler_view.setAdapter(myCoursesAdapter);

                    if(dashboardMyCoursesResponseModelArray.size() == 0){
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                    myCoursesAdapter.setOnItemClickListener(onItemClickListener);
                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSEDETAILS)) {

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject = new JSONObject(response);
                    String dataContent = jSONObject.getString("data");

                    courseDetailsResponseModel = new CourseDetailsController().getCourseDetails(dataContent);

                    if (courseDetailsResponseModel.isCombo()) {
                        new ViewManager().gotoComboCourseView(getActivity(), courseDetailsResponseModel);
                    } else {

                        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                            param_get_ServiceCallResult = Constants.GET_COURSECHAPTERS;

                            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                            ApiService.getApiService().doGetChapters(getActivity(), MyCoursesFragment.this, cID);
                        } else {
                            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                        }

                    }
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));
                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSECHAPTERS)) {

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject = new JSONObject(response);
                    String dataContent = jSONObject.getString("data");

                    List<ChaptersResponseModel> chaptersResponseModelLstArray = new CourseDetailsController().getChaptersResponse(dataContent);

                    courseDetailsResponseModel.setChaptersResponseModel(chaptersResponseModelLstArray);

                    new ViewManager().gotoSingleCourseView(getActivity(), courseDetailsResponseModel);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        if (pd.isShowing()) {
            pd.cancel();
        }
        if (error.isEmpty()) {
            error = "Error in connection";
        }

        snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));
    }
}
