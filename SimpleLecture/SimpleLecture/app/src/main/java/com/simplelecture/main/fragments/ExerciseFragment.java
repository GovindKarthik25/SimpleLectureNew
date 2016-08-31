package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.DasboardExerciseListAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.CourseDetailsController;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.fragments.interfaces.OnImageClickListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.ExerciseChapters;
import com.simplelecture.main.model.viewmodel.ExerciseResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment implements NetworkLayer, OnImageClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ExpandableListView exercise_expandableListView;
    private DasboardExerciseListAdapter dasboardExerciseListAdapter;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private List<ExerciseResponseModel> exerciseResponseModelArray;
    private CoordinatorLayout coordinatorLayout;
    private CourseDetailsResponseModel courseDetailsResponseModel;
    private String cID = "";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ExerciseFragment() {
        // Required empty public constructor
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_exercise, container, false);
        exercise_expandableListView = (ExpandableListView) convertView.findViewById(R.id.exercise_expandableListView);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadGetDashboardExercise();

        if (dasboardExerciseListAdapter != null) {
            dasboardExerciseListAdapter = new DasboardExerciseListAdapter(getActivity(), exerciseResponseModelArray, this);
            exercise_expandableListView.setAdapter(dasboardExerciseListAdapter);

        }


        exercise_expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            private int lastExpandedGroupPosition;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != lastExpandedGroupPosition) {
                    exercise_expandableListView.collapseGroup(lastExpandedGroupPosition);
                }
                lastExpandedGroupPosition = groupPosition;
            }
        });

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

    private void loadGetDashboardExercise() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_MYEXERCISES;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardExercise(getActivity(), ExerciseFragment.this);
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
                    ApiService.getApiService().doGetCourseDetails(getActivity(), ExerciseFragment.this, courseId);
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

    private void loadGetDashboardExerciseChapterfile(String courseChapterId) {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_COURSE_CHAPTERFILE;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardExerciseCourseChapterfile(getActivity(), ExerciseFragment.this, courseChapterId);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

            exerciseResponseModelArray = new ArrayList<ExerciseResponseModel>();
            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_MYEXERCISES)) {


                if (outputResponseModel.isSuccess()) {

                    try {
                        JSONObject base = new JSONObject(response);
                        JSONArray dataContent = base.getJSONArray("data");
                        for (int i = 0; i < dataContent.length(); i++) {
                            JSONObject eachData = dataContent.getJSONObject(i);

                            ExerciseResponseModel exerciseResponseModelObj = new ExerciseResponseModel();
                            exerciseResponseModelObj.setCourseId(eachData.getString("CourseId"));
                            exerciseResponseModelObj.setCourseName(eachData.getString("CourseName"));
                            exerciseResponseModelObj.setCourseIcon(eachData.getString("CourseIcon"));
                            exerciseResponseModelObj.setDownloadedCount(eachData.getString("DownloadedCount"));
                            exerciseResponseModelObj.setRemainingCount(eachData.getString("RemainingCount"));

                            exerciseResponseModelArray.add(exerciseResponseModelObj);

                            String courseId = eachData.getString("CourseId");
                            JSONArray exerciseChapters = eachData.getJSONArray("ExerciseChapters");

                            List<ExerciseChapters> exerciseChaptersArray = new ArrayList<ExerciseChapters>();

                            for (int j = 0; j < exerciseChapters.length(); j++) {
                                JSONObject eachDataexerciseChapters = exerciseChapters.getJSONObject(j);
                                ExerciseChapters exerciseChaptersObj = new ExerciseChapters();
                                exerciseChaptersObj.setCourseId(courseId);
                                exerciseChaptersObj.setCourseChapterId(eachDataexerciseChapters.getString("CourseChapterId"));
                                exerciseChaptersObj.setCourseChapterName(eachDataexerciseChapters.getString("CourseChapterName"));
                                exerciseChaptersObj.setCourseChapterNumber(eachDataexerciseChapters.getString("CourseChapterNumber"));
                                exerciseChaptersObj.setIsFileDownloaded(eachDataexerciseChapters.getString("IsFileDownloaded"));
                                exerciseChaptersArray.add(exerciseChaptersObj);

                            }
                            //Log.i("testPaperRespons***", exerciseChaptersArray.toString());

                            for (int k = 0; k < exerciseChaptersArray.size(); k++) {
                                if (exerciseResponseModelArray.get(i).getCourseId() == exerciseChaptersArray.get(k).getCourseId()) {
                                    exerciseResponseModelArray.get(i).setExerciseChapters(exerciseChaptersArray);

                                }
                            }
                        }

                        dasboardExerciseListAdapter = new DasboardExerciseListAdapter(getActivity(), exerciseResponseModelArray, this);
                        exercise_expandableListView.setAdapter(dasboardExerciseListAdapter);
                        exercise_expandableListView.expandGroup(0);


                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_COURSE_CHAPTERFILE)) {
                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");
                    JSONObject jSONObject = new JSONObject(dataContent);
                    String filePath = jSONObject.getString("FilePath");

                    Log.i("filePath--->", filePath);

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

                            ApiService.getApiService().doGetChapters(getActivity(), ExerciseFragment.this, cID);
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


    @Override
    public void ondownloadclick(int position, String courseChapterId) {
        loadGetDashboardExerciseChapterfile(courseChapterId);
    }

    @Override
    public void onViewCourseclick(int position, String courseId) {
        cID = courseId;
        loadGetCourse(cID);
        Toast.makeText(getActivity(), (position + " - " + courseId), Toast.LENGTH_SHORT).show();

    }
}
