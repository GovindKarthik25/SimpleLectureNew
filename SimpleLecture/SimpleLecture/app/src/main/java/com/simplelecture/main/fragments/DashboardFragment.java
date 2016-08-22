package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.DashboardAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.courseFeatures;
import com.simplelecture.main.model.viewmodel.myCourses;
import com.simplelecture.main.model.viewmodel.MyCoursesResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class DashboardFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;

    DashboardAdapter dashboardAdapter;
    Activity activity = getActivity();
    private CoordinatorLayout coordinatorLayout, floatingCoordinatorLayout;
    private SnackBarManagement snack;
    private List<myCourses> myCoursesLstArray;
    private MyCoursesResponseModel myCoursesResponseModelObj;
    private boolean param_get_MyCourses = false;
    private boolean param_get_MyCoursesDetails = false;
    private boolean param_get_Chapters = false;

    private ProgressDialog pd;
    private List<courseFeatures> courseFeaturesLstArray;
    private List<CourseCombos> courseCombosLstArray;
    private CourseDetailsResponseModel courseDetailsResponseModel;
    private myCourses myCoursesObj;
    private AlertMessageManagement alertMessageManagement;
    private FloatingActionButton floatingAction;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        textViewDownloaded = (TextView) convertView.findViewById(R.id.tv_downloaded);
        textReadyDownload = (TextView) convertView.findViewById(R.id.tv_ready_download);
        textPending = (TextView) convertView.findViewById(R.id.tv_pending);
        textAtended = (TextView) convertView.findViewById(R.id.tv_attended);


//        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
//        floatingCoordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.floatingActionButton);
//        floatingAction = (FloatingActionButton) floatingCoordinatorLayout.findViewById(R.id.floatingAction);

        recyclerView = (RecyclerView) convertView.findViewById(R.id.my_recycler_view);

        readFileFromAssets("dashboard.json");


        return convertView;
    }

    private String readFileFromAssets(String fileName) {
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(fileName), "UTF-8"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            parseData(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    private void parseData(String response) {

        try {
            JsonArray jArray;
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

//            JSONObject jsonObject = new JSONObject(response);
//            JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");

            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (outputResponseModel.isSuccess()) {
                JSONObject jSONObject1 = new JSONObject(response);
                String dataContent = jSONObject1.getString("data");
                DashboardResponseModel dashboardResponseModel = gson.fromJson(dataContent, DashboardResponseModel.class);

                Log.d("Response", "" + dashboardResponseModel);


                textViewDownloaded.setText(dashboardResponseModel.getExerciseDownloaded());
                textReadyDownload.setText(dashboardResponseModel.getExercisePending());
                textPending.setText(dashboardResponseModel.getQuizPending());
                textAtended.setText(dashboardResponseModel.getQuizAttended());

            } else {

            }

//            JSONObject jsonObject = new JSONObject(data);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void loadTempData() {


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);

            if (dashboardAdapter != null) {
                dashboardAdapter = new DashboardAdapter(getActivity(), myCoursesLstArray);
                recyclerView.setAdapter(dashboardAdapter);

                dashboardAdapter.setOnItemClickListener(onItemClickListener);
            }

            floatingAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadGetMyCourses();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadGetMyCourses() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_MyCourses = true;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                //My HomeCoursesModel service
                ApiService.getApiService().doGetMyCourses(getActivity(), Util.getFromPrefrences(getActivity(), "uId"), DashboardFragment.this);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
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
                myCoursesObj = myCoursesLstArray.get(position);

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    param_get_MyCoursesDetails = true;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                    //My HomeCoursesModel service
                    ApiService.getApiService().doGetCourseDetails(getActivity(), DashboardFragment.this, myCoursesObj.getcId());
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

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


    @Override
    public void parseResponse(String response) {
        try {
            pd.cancel();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            // Log.i("response*******", "response**"+response.toString());
            if (param_get_MyCourses) {
                JSONObject jSONObject = new JSONObject(response);
                String myCoursesContent = jSONObject.getString("myCourses");
                JsonArray jarray = parser.parse(myCoursesContent).getAsJsonArray();

                myCoursesLstArray = new ArrayList<myCourses>();
                for (JsonElement obj : jarray) {
                    myCourses myCoursesObj = gson.fromJson(obj, myCourses.class);
                    myCoursesLstArray.add(myCoursesObj);
                }

            /*Setting data to main arraylist*/
                myCoursesResponseModelObj = new MyCoursesResponseModel();
                myCoursesResponseModelObj.setMycourses(myCoursesLstArray);

                dashboardAdapter = new DashboardAdapter(getActivity(), myCoursesLstArray);
                recyclerView.setAdapter(dashboardAdapter);
                dashboardAdapter.setOnItemClickListener(onItemClickListener);

                dashboardAdapter.notifyDataSetChanged();

                // Log.i("myCoursesResponse**->", myCoursesResponseModelObj.toString() + "");
                param_get_MyCourses = false;
            } else if (param_get_MyCoursesDetails) {
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
                //Log.i("courseCombosContent", courseCombosContent.toString());
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

                //  Log.i("courseDetailsResp***", courseDetailsResponseModel.toString() + " ***** ");


                if (courseDetailsResponseModel.isCombo()) {
                    new ViewManager().gotoComboCourseView(getActivity(), courseDetailsResponseModel);
                } else {

                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                        param_get_Chapters = true;

                        pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                        //My HomeCoursesModel service
                        ApiService.getApiService().doGetChapters(getActivity(), DashboardFragment.this, myCoursesObj.getcId());
                    } else {
                        snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                    }

                }
            } else if (param_get_Chapters) {

                JsonArray jArray = parser.parse(response).getAsJsonArray();

                ArrayList<ChaptersResponseModel> chaptersResponseModelLstArray = new ArrayList<ChaptersResponseModel>();
                for (JsonElement obj : jArray) {
                    ChaptersResponseModel chaptersResponseModelobj = gson.fromJson(obj, ChaptersResponseModel.class);
                    chaptersResponseModelLstArray.add(chaptersResponseModelobj);
                }

                courseDetailsResponseModel.setChaptersResponseModel(chaptersResponseModelLstArray);

                // Log.i("chaptersResponseMo**", " * * * * " + courseDetailsResponseModel.toString());

                param_get_Chapters = false;
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
            Log.v("myCoursesLstArray", "error");
            param_get_MyCourses = false;
            param_get_MyCoursesDetails = false;
            param_get_Chapters = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
