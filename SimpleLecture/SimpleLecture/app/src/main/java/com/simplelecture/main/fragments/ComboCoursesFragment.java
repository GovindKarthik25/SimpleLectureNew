package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.ComboCoursesAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.CourseDetailsController;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.courseFeatures;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

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
    private String param_get_ServiceCallResult = "";

    private ProgressDialog pd;
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private CourseDetailsResponseModel courseDetailsResponseModel;
    private boolean param_IsCombo;
    private TextView textView_headerUpdateDetails;

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
        textView_headerUpdateDetails = (TextView) convertView.findViewById(R.id.textView_headerUpdateDetails);
        recyclerView = (RecyclerView) convertView.findViewById(R.id.my_recycler_view);


        return convertView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        comboCoursesAdapter = new ComboCoursesAdapter(getActivity(), courseDetailsResponseModelObj.getCourseCombos());
        recyclerView.setAdapter(comboCoursesAdapter);

        comboCoursesAdapter.setOnItemClickListener(onItemClickListener);


        String headingUpdate = courseDetailsResponseModelObj.getcName() + " (" + courseDetailsResponseModelObj.getcComboName() + ")";

        textView_headerUpdateDetails.setText(headingUpdate);
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
                    param_get_ServiceCallResult = Constants.GET_COURSEDETAILS;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                    //My HomeCoursesModel service
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
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            List<courseFeatures> courseFeaturesLstArray;
            List<CourseCombos> courseCombosLstArray;
            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSEDETAILS)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");

                    courseDetailsResponseModel = new CourseDetailsController().getCourseDetails(dataContent);


                    if (param_IsCombo && courseDetailsResponseModel.isCombo()) {
                        param_IsCombo = false;
                        new ViewManager().gotoComboCourseView(getActivity(), courseDetailsResponseModel);

                    } else {

                        if (courseDetailsResponseModel.isCombo()) {
                            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                                param_IsCombo = true;
                                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                                //My HomeCoursesModel service
                                ApiService.getApiService().doGetCourseDetails(getActivity(), ComboCoursesFragment.this, courseCombosObj.getcId());
                            } else {
                                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                            }

                        } else {

                            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                                param_get_ServiceCallResult = Constants.GET_COURSECHAPTERS;
                                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                                //My HomeCoursesModel service
                                ApiService.getApiService().doGetChapters(getActivity(), ComboCoursesFragment.this, courseCombosObj.getcId());
                            } else {
                                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                            }

                        }
                    }
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSECHAPTERS)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");

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
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            if (error.isEmpty()) {
                error = "Error in connection";
            }

            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
