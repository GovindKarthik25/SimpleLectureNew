package com.simplelecture.main.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.VideoPlayerActivity;
import com.simplelecture.main.adapters.ExpandableListAdapter;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raos on 2/14/2016.
 */
public class CourseIndexFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private CourseDetailsResponseModel courseDetailsResponseModelObj;

    public CourseIndexFragment() {
        // Required empty public constructor
    }

    private SnackBarManagement snack;
    private String uId;
    private String mParam2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProgressDialog pd;
    private CoordinatorLayout coordinatorLayout;

    public static CourseIndexFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        CourseIndexFragment fragment = new CourseIndexFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, courseDetailsResponseModelObj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        snack = new SnackBarManagement(getActivity());

        if (getArguments() != null) {
            courseDetailsResponseModelObj = (CourseDetailsResponseModel) getArguments().getSerializable(ARG_PARAM1);
            Toast.makeText(getActivity(), "" + courseDetailsResponseModelObj, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_course_index, container, false);

        // get the listview
        expListView = (ExpandableListView) convertView.findViewById(R.id.lvExp);

        expListView.setOnChildClickListener(onChildClickListener);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return convertView;
    }


    ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

            ViewManager viewManager = new ViewManager();
            viewManager.gotoVideoPlayerView(getActivity());

            return true;
        }
    };

    /*
     * Preparing the list data
	 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Chapter 1");
        listDataHeader.add("Chapter 2");
        listDataHeader.add("Chapter 3");
        listDataHeader.add("Chapter 4");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Electric Charges and Fields");
        top250.add("Electric Charges and Fields");
        top250.add("Electric Charges and Fields");
        top250.add("Electric Charges and Fields");
        top250.add("Electric Charges and Fields");
        top250.add("Electric Charges and Fields");
        top250.add("Electric Charges and Fields");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        listDataChild.put(listDataHeader.get(3), comingSoon);

    }
}
