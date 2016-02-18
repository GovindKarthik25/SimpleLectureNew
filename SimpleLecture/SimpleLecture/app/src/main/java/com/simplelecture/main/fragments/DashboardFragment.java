package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.simplelecture.main.activities.LoginActivity;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.ComboCoursesAdapter;
import com.simplelecture.main.adapters.DashboardAdapter;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.MyCoursesResponseModel;
import com.simplelecture.main.model.viewmodel.myCourses;
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
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private List<myCourses> myCoursesLstArray = new ArrayList<myCourses>();
    private MyCoursesResponseModel myCoursesResponseModelObj;
    private boolean param_get_MyCourses = false;
    private ProgressDialog pd;


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

        snack = new SnackBarManagement(getActivity());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            param_get_MyCourses = true;
            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
            pd.setCanceledOnTouchOutside(false);
            //My Courses service
            ApiService.getApiService().doGetMyCourses(getActivity(), Util.getFromPrefrences(getActivity(), "uId"), DashboardFragment.this);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) convertView.findViewById(R.id.my_recycler_view);

        return convertView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (dashboardAdapter != null) {
            dashboardAdapter = new DashboardAdapter(getActivity(), myCoursesLstArray);
            recyclerView.setAdapter(dashboardAdapter);

            dashboardAdapter.setOnItemClickListener(onItemClickListener);
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
            myCourses myCoursesObj = myCoursesLstArray.get(position);

            ViewManager viewManager = new ViewManager();
            viewManager.gotoSingleCourseView(getActivity(), myCoursesObj.getcId());
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

            if (param_get_MyCourses) {
                JSONObject jSONObject = new JSONObject(response);
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
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
                Log.i("myCoursesLstArray**->", myCoursesLstArray.size() + "");

                //        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


                dashboardAdapter = new DashboardAdapter(getActivity(), myCoursesLstArray);
                recyclerView.setAdapter(dashboardAdapter);
                dashboardAdapter.setOnItemClickListener(onItemClickListener);

                dashboardAdapter.notifyDataSetChanged();

                Log.i("myCoursesResponse**->", myCoursesResponseModelObj.toString() + "");
                param_get_MyCourses = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        Log.v("myCoursesLstArray", "error");
        param_get_MyCourses = false;
    }
}
