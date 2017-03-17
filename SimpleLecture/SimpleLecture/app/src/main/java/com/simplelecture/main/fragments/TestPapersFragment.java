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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.TestPapersAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.myCourses;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestPapersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestPapersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestPapersFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1 = "0";
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView testPapersRecycler_view;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private ProgressDialog pd;
    private String param_get_ServiceCallResult = "";
    private CoordinatorLayout coordinatorLayout;
    private TestPapersAdapter testPapersAdapter;
    private List<myCourses> myCoursesList;
    private String courseID = "0";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestPapersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestPapersFragment newInstance(String param1, String param2) {
        TestPapersFragment fragment = new TestPapersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TestPapersFragment() {
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
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_test_papers, container, false);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
        testPapersRecycler_view = (RecyclerView) convertView.findViewById(R.id.testPapersRecycler_view);


        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            loadGetDashboardTestPaper();

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            testPapersRecycler_view.setLayoutManager(gridLayoutManager);

            if (testPapersAdapter != null) {

                testPapersAdapter = new TestPapersAdapter(getActivity(), myCoursesList);
                testPapersRecycler_view.setAdapter(testPapersAdapter);

                testPapersAdapter.setOnItemClickListener(onItemClickListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadGetDashboardTestPaper() {
        try {
            Log.i("mParam1--->", mParam1);
            courseID = mParam1;
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_QUIZ_COURSES;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardUSER_Quiz_Courses(getActivity(), TestPapersFragment.this, courseID);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                myCourses myCoursesObj = myCoursesList.get(position);

              //  Toast.makeText(getActivity(), myCoursesObj.getcId(), Toast.LENGTH_SHORT).show();

            new ViewManager().gotoTestPaperChapterActivity(getActivity(), myCoursesObj);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

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


    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray;

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_QUIZ_COURSES)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
                myCoursesList = new ArrayList<myCourses>();
                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");

                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        myCourses myCoursesObj = gson.fromJson(obj, myCourses.class);
                        myCoursesList.add(myCoursesObj);
                    }

                    if(myCoursesList.size() == 0){
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                    testPapersAdapter = new TestPapersAdapter(getActivity(), myCoursesList);
                    testPapersRecycler_view.setAdapter(testPapersAdapter);
                    testPapersAdapter.setOnItemClickListener(onItemClickListener);


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
