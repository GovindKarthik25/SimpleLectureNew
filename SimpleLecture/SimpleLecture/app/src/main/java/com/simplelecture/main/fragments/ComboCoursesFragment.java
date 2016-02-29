package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.simplelecture.main.adapters.ComboCoursesAdapter;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.courseFeatures;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComboCoursesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComboCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComboCoursesFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;

    ComboCoursesAdapter comboCoursesAdapter;

    CourseDetailsResponseModel courseDetailsResponseModelObj;
    private CourseCombos courseCombosObj;
    private boolean param_get_MyCoursesDetails = false;
    private boolean param_get_Details = false;

    private ProgressDialog pd;
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private CourseDetailsResponseModel courseDetailsResponseModel;
    private boolean param_IsCombo;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComboCoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComboCoursesFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        ComboCoursesFragment fragment = new ComboCoursesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, courseDetailsResponseModelObj);
        fragment.setArguments(args);
        return fragment;
    }

    public ComboCoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        snack = new SnackBarManagement(getContext());
        if (getArguments() != null) {
            courseDetailsResponseModelObj = (CourseDetailsResponseModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_combo_courses, container, false);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) convertView.findViewById(R.id.my_recycler_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        comboCoursesAdapter = new ComboCoursesAdapter(getActivity(), courseDetailsResponseModelObj.getCourseCombos());
        recyclerView.setAdapter(comboCoursesAdapter);

        comboCoursesAdapter.setOnItemClickListener(onItemClickListener);


        return convertView;
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
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                courseCombosObj = courseDetailsResponseModelObj.getCourseCombos().get(position);

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    param_get_MyCoursesDetails = true;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                    //My Courses service
                    ApiService.getApiService().doGetCourseDetails(getActivity(), ComboCoursesFragment.this, courseCombosObj.getcId());
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    @Override
    public void parseResponse(String response) {

        try {
            pd.cancel();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            List<courseFeatures> courseFeaturesLstArray;
            List<CourseCombos> courseCombosLstArray;
            if (param_get_MyCoursesDetails) {
                courseDetailsResponseModel = gson.fromJson(response, CourseDetailsResponseModel.class);
                JSONObject jSONObject = new JSONObject(response);

                String myCoursesContent = jSONObject.getString("courseFeatures");
                JsonArray jarray = parser.parse(myCoursesContent).getAsJsonArray();

                courseFeaturesLstArray = new ArrayList<courseFeatures>();
                for (JsonElement obj : jarray) {
                    courseFeatures courseFeaturesObj = gson.fromJson(obj, courseFeatures.class);
                    courseFeaturesLstArray.add(courseFeaturesObj);
                }

                String courseCombosContent = jSONObject.getString("courseCombos");
                Log.i("courseCombosContent", courseCombosContent.toString());
                if (courseCombosContent != null && !courseCombosContent.equals("null")) {
                    JsonArray jarrray = parser.parse(courseCombosContent).getAsJsonArray();

                    courseCombosLstArray = new ArrayList<CourseCombos>();
                    for (JsonElement obj : jarrray) {

                        CourseCombos courseCombosObj = gson.fromJson(obj, CourseCombos.class);
                        courseCombosLstArray.add(courseCombosObj);
                    }
                    courseDetailsResponseModel.setCourseCombos(courseCombosLstArray);

                }
                courseDetailsResponseModel.setCourseFeature(courseFeaturesLstArray);

                param_get_MyCoursesDetails = false;

                Log.i("courseDetailsResp***", courseDetailsResponseModel.toString() + " ***** ");


                if (param_IsCombo && courseDetailsResponseModel.isCombo()) {
                    param_IsCombo = false;
                    new ViewManager().gotoComboCourseView(getActivity(), courseDetailsResponseModel);

                } else {

                    if (courseDetailsResponseModel.isCombo()) {
                        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                            param_get_MyCoursesDetails = true;
                            param_IsCombo = true;
                            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                            //My Courses service
                            ApiService.getApiService().doGetCourseDetails(getActivity(), ComboCoursesFragment.this, courseCombosObj.getcId());
                        } else {
                            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                        }

                    } else {

                        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                            param_get_Details = true;

                            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                            //My Courses service
                            ApiService.getApiService().doGetChapters(getActivity(), ComboCoursesFragment.this, courseCombosObj.getcId());
                        } else {
                            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                        }

                    }
                }
            } else if (param_get_Details) {

                JsonArray jArray = parser.parse(response).getAsJsonArray();

                ArrayList<ChaptersResponseModel> chaptersResponseModelLstArray = new ArrayList<ChaptersResponseModel>();
                for (JsonElement obj : jArray) {
                    ChaptersResponseModel chaptersResponseModelobj = gson.fromJson(obj, ChaptersResponseModel.class);
                    chaptersResponseModelLstArray.add(chaptersResponseModelobj);
                }

                courseDetailsResponseModel.setChaptersResponseModel(chaptersResponseModelLstArray);

                // Log.i("chaptersResponseMo**", " * * * * " + courseDetailsResponseModel.toString());

                param_get_Details = false;
                new ViewManager().gotoSingleCourseView(getActivity(), courseDetailsResponseModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            if (error.isEmpty()) {
                error = "Error in connection";
            }

            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));
            param_get_MyCoursesDetails = false;
            param_get_Details = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
