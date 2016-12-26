package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.DashboardAdapter;
import com.simplelecture.main.adapters.ForumAdapter;
import com.simplelecture.main.adapters.RelatedCourseAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.CourseDetailsController;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardResponseModel;
import com.simplelecture.main.model.viewmodel.MyCoursesResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.RelatedCourses;
import com.simplelecture.main.model.viewmodel.courseFeatures;
import com.simplelecture.main.util.AlertMessageManagement;
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
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements NetworkLayer, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView dashbord_recycler_view;

    DashboardAdapter dashboardAdapter;
    Activity activity = getActivity();
    private CoordinatorLayout coordinatorLayout, floatingCoordinatorLayout;
    private SnackBarManagement snack;
    private String param_get_ServiceCallResult = "";

    private ProgressDialog pd;
    private List<courseFeatures> courseFeaturesLstArray;
    private List<CourseCombos> courseCombosLstArray;
    private CourseDetailsResponseModel courseDetailsResponseModel;
    private MyCoursesResponseModel myCoursesObj;
    private AlertMessageManagement alertMessageManagement;
    private FloatingActionButton floatingAction;
    private RecyclerView forum_recycler_view;
    private DashboardResponseModel dashboardResponseModelObj;
    private ForumAdapter forumAdapter;
    private RecyclerView relatedCourse_recycler_view;
    private RelatedCourseAdapter relatedCourseAdapter;
    private ImageView dashboardDetails_ImageView;
    private ImageView dashboardExercise_ImageView, dashboardQuizSummary_ImageView;
    private TextView viewallVF_TextView;
    private ViewPager viewPager;
    private String cID;
    private int dashboardPageNo = 0;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardFragment() {
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

//            loadGetMyCourses();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    TextView textViewDownloaded;
    TextView textReadyDownload;
    TextView textPending;
    TextView textAtended;

    BroadcastReceiver refreshBroadCastRec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        textViewDownloaded = (TextView) convertView.findViewById(R.id.tv_downloaded);
        textReadyDownload = (TextView) convertView.findViewById(R.id.tv_ready_download);
        textPending = (TextView) convertView.findViewById(R.id.tv_pending);
        textAtended = (TextView) convertView.findViewById(R.id.tv_attended);

        dashboardDetails_ImageView = (ImageView) convertView.findViewById(R.id.dashboardDetails_ImageView);
        dashboardExercise_ImageView = (ImageView) convertView.findViewById(R.id.dashboardExercise_ImageView);
        dashboardQuizSummary_ImageView = (ImageView) convertView.findViewById(R.id.dashboardQuizSummary_ImageView);
        viewallVF_TextView = (TextView) convertView.findViewById(R.id.viewallVF_TextView);
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);

        dashboardDetails_ImageView.setOnClickListener(this);
        dashboardExercise_ImageView.setOnClickListener(this);
        dashboardQuizSummary_ImageView.setOnClickListener(this);
        viewallVF_TextView.setOnClickListener(this);

        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
//        floatingCoordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.floatingActionButton);
//        floatingAction = (FloatingActionButton) floatingCoordinatorLayout.findViewById(R.id.floatingAction);

        dashbord_recycler_view = (RecyclerView) convertView.findViewById(R.id.dashbord_recycler_view);
        forum_recycler_view = (RecyclerView) convertView.findViewById(R.id.forum_recycler_view);
        relatedCourse_recycler_view = (RecyclerView) convertView.findViewById(R.id.relatedCourse_recycler_view);

        doRegister();
        Log.d("Simple","DashboardFragment-onCreateView");

        return convertView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Simple","DashboardFragment-onResume");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Simple","DashboardFragment-onDestroy");
    }

    private void doRegister() {

        refreshBroadCastRec = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };


        getActivity().registerReceiver(refreshBroadCastRec,new IntentFilter("broadcast"));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("Simple","DashboardFragment-onActivityCreated");

        try {

            loadGetDashboard();

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.HORIZONTAL, false);
            dashbord_recycler_view.setLayoutManager(gridLayoutManager);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            forum_recycler_view.setLayoutManager(linearLayoutManager);

            LinearLayoutManager linearLayoutManagr = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            relatedCourse_recycler_view.setLayoutManager(linearLayoutManagr);


            if (dashboardAdapter != null) {
                dashboardAdapter = new DashboardAdapter(getActivity(), dashboardResponseModelObj.getMyCoursesResponseModel());
                dashbord_recycler_view.setAdapter(dashboardAdapter);

                dashboardAdapter.setOnItemClickListener(onItemClickListener);
            }

            if (forumAdapter != null) {
                forumAdapter = new ForumAdapter(getActivity(), dashboardResponseModelObj.getForumTopics());
                forum_recycler_view.setAdapter(forumAdapter);
            }

            if (relatedCourseAdapter != null) {
                relatedCourseAdapter = new RelatedCourseAdapter(getActivity(), dashboardResponseModelObj.getRelatedCourses());
                relatedCourse_recycler_view.setAdapter(relatedCourseAdapter);

                relatedCourseAdapter.setOnItemClickListener(relatedCourseOnItemClickListener);
            }



           /* floatingAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadGetMyCourses();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGetDashboard() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_DASHBOARD;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardDetails(getActivity(), DashboardFragment.this);
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
                    ApiService.getApiService().doGetCourseDetails(getActivity(), DashboardFragment.this, courseId);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                myCoursesObj = dashboardResponseModelObj.getMyCoursesResponseModel().get(position);
                cID = myCoursesObj.getCourseId();
                dashboardPageNo = 1;

                loadGetCourse(cID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    OnItemClickListener relatedCourseOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                RelatedCourses relatedCoursesObj = dashboardResponseModelObj.getRelatedCourses().get(position);
                cID = relatedCoursesObj.getcId();
                dashboardPageNo = 0;
                loadGetCourse(cID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    pageChangeListener pageChangeListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            pageChangeListener = (DashboardFragment.pageChangeListener) activity;
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


    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            List<MyCoursesResponseModel> myCoursesResponseModelLstArray = new ArrayList<MyCoursesResponseModel>();

            Gson gson = new Gson();
            JsonArray jArray;
            JsonParser parser = new JsonParser();

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_DASHBOARD)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    dashboardResponseModelObj = gson.fromJson(dataContent, DashboardResponseModel.class);

                    JSONObject jSONObject = new JSONObject(dataContent);
                    String myCoursesResponse = jSONObject.getString("MyCourses");
                    jArray = parser.parse(myCoursesResponse).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        MyCoursesResponseModel myCoursesResponseModelObj = gson.fromJson(obj, MyCoursesResponseModel.class);
                        myCoursesResponseModelLstArray.add(myCoursesResponseModelObj);
                    }

                    dashboardResponseModelObj.setMyCoursesResponseModel(myCoursesResponseModelLstArray);

                    if(dashboardResponseModelObj.getMyCoursesResponseModel().size() == 0){
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                    dashboardAdapter = new DashboardAdapter(getActivity(), dashboardResponseModelObj.getMyCoursesResponseModel());
                    dashbord_recycler_view.setAdapter(dashboardAdapter);

                    dashboardAdapter.setOnItemClickListener(onItemClickListener);

                    forumAdapter = new ForumAdapter(getActivity(), dashboardResponseModelObj.getForumTopics());
                    forum_recycler_view.setAdapter(forumAdapter);

                    relatedCourseAdapter = new RelatedCourseAdapter(getActivity(), dashboardResponseModelObj.getRelatedCourses());
                    relatedCourse_recycler_view.setAdapter(relatedCourseAdapter);

                    relatedCourseAdapter.setOnItemClickListener(relatedCourseOnItemClickListener);


                    textViewDownloaded.setText(dashboardResponseModelObj.getExerciseDownloaded());
                    textReadyDownload.setText(dashboardResponseModelObj.getExercisePending());
                    textPending.setText(dashboardResponseModelObj.getQuizPending());
                    textAtended.setText(dashboardResponseModelObj.getQuizAttended());
                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSEDETAILS)) {
                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject = new JSONObject(response);
                    String dataContent = jSONObject.getString("data");

                    courseDetailsResponseModel = new CourseDetailsController().getCourseDetails(dataContent);
                    courseDetailsResponseModel.setPage(dashboardPageNo); // To Check the dashboard page or not

                    if (courseDetailsResponseModel.isCombo()) {
                        new ViewManager().gotoComboCourseView(getActivity(), courseDetailsResponseModel);
                    } else {

                        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                            param_get_ServiceCallResult = Constants.GET_COURSECHAPTERS;

                            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                            ApiService.getApiService().doGetChapters(getActivity(), DashboardFragment.this, cID);
                        } else {
                            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                        }

                    }
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));
                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSECHAPTERS)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject = new JSONObject(response);
                    String dataContent = jSONObject.getString("data");

                    List<ChaptersResponseModel> chaptersResponseModelLstArray = new CourseDetailsController().getChaptersResponse(dataContent);

                    courseDetailsResponseModel.setChaptersResponseModel(chaptersResponseModelLstArray);
                    courseDetailsResponseModel.setPage(1); // To Check the dashboard page or not
                    new ViewManager().gotoSingleCourseView(getActivity(), courseDetailsResponseModel);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));
                }
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

        if (v == dashboardDetails_ImageView) {
            getActivity().finish();
            new ViewManager().gotoHomeView(getActivity());
        } else if (v == dashboardExercise_ImageView) {
            pageChangeListener.onPageChange(2);
        } else if (v == dashboardQuizSummary_ImageView) {
            pageChangeListener.onPageChange(3);
        } else if (v == viewallVF_TextView) {
            pageChangeListener.onPageChange(4);
//            getActivity().finish();
//            new ViewManager().gotoDashboardView(getActivity(), 4);
        }
    }

    public interface pageChangeListener{
        public void onPageChange(int page);
    }
}
