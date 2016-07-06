package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.CourseCategoriesAdapter;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CourseCategoriesModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseCategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseCategoriesFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ProgressDialog pd;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private boolean param_get_CourseCategoriesTutorial;
    private RecyclerView courseCategoriesRecycler_view;
    private ArrayList<CourseCategoriesModel> courseCategoriesModelLst;
    private CourseCategoriesAdapter courseCategoriesAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseCategoriesFragment.
     */


    // TODO: Rename and change types and number of parameters
    public static CourseCategoriesFragment newInstance(String param1, String param2) {
        CourseCategoriesFragment fragment = new CourseCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CourseCategoriesFragment() {
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

            loadGetCourseCategoriesTutorial();

            setHasOptionsMenu(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_course_categories, container, false);
        FloatingActionButton fabCourseCategories = (FloatingActionButton) convertView.findViewById(R.id.fabCourseCategories);
        courseCategoriesRecycler_view = (RecyclerView) convertView.findViewById(R.id.courseCategoriesRecycler_view);
        courseCategoriesRecycler_view.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        courseCategoriesRecycler_view.setLayoutManager(gridLayoutManager);

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

    private void loadGetCourseCategoriesTutorial() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_CourseCategoriesTutorial = true;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetCourseCategories(getActivity(), CourseCategoriesFragment.this, "0");
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRecyclerView() {


        if (courseCategoriesModelLst != null) {
            courseCategoriesAdapter = new CourseCategoriesAdapter(getActivity(), courseCategoriesModelLst);
            courseCategoriesRecycler_view.setAdapter(courseCategoriesAdapter);

            courseCategoriesAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                /*myCoursesObj = myCoursesLstArray.get(position);

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    param_get_DemoTutorial = true;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                    //My HomeCoursesModel service
                    ApiService.getApiService().doGetCourseDetails(getActivity(), DemoTutorialFragment.this, myCoursesObj.getcId());
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }*/
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
            if (param_get_CourseCategoriesTutorial) {

                JsonArray jarray = parser.parse(response).getAsJsonArray();

                courseCategoriesModelLst = new ArrayList<CourseCategoriesModel>();
                for (JsonElement obj : jarray) {
                    CourseCategoriesModel courseCategoriesModellObj = gson.fromJson(obj, CourseCategoriesModel.class);
                    courseCategoriesModelLst.add(courseCategoriesModellObj);
                }

                loadRecyclerView();
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

        param_get_CourseCategoriesTutorial = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        menu.findItem(R.id.action_filter).setVisible(true);
        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                SelectYourCoursesFragment selectYourCoursesFragment = new SelectYourCoursesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                selectYourCoursesFragment.show(fragmentManager, "CourseCategoryFragment");

                return true;
            }
        });

    }
}
