package com.simplelecture.main.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.CourseIndexExpandableListAdapter;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.courseTopics;
import com.simplelecture.main.util.SnackBarManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raos on 2/14/2016.
 */
public class CourseIndexFragment extends Fragment {

    CourseIndexExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<courseTopics>> listDataChild;
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

    private List<ChaptersResponseModel> chaptersResponseModel = null;

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
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_course_index, container, false);

        // get the listview
        expListView = (ExpandableListView) convertView.findViewById(R.id.lvExp);

        expListView.setOnChildClickListener(onChildClickListener);

        chaptersResponseModel = courseDetailsResponseModelObj.getChaptersResponseModel();


        // preparing list data
       // prepareListData();

        //listAdapter = new CourseIndexExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        listAdapter = new CourseIndexExpandableListAdapter(getActivity(), chaptersResponseModel, courseDetailsResponseModelObj.isPurchased());

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            private int lastExpandedGroupPosition;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != lastExpandedGroupPosition) {
                    expListView.collapseGroup(lastExpandedGroupPosition);
                }
                lastExpandedGroupPosition = groupPosition;
            }
        });

        return convertView;
    }


    ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
//            ViewManager viewManager = new ViewManager();
//            viewManager.gotoVideoPlayerView(getActivity());

            return true;
        }
    };

    /*
     * Preparing the list data
	 */
    private void prepareListData() {

        try {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<courseTopics>>();

            for (ChaptersResponseModel chaptersResponse : chaptersResponseModel) {
                listDataHeader.add(chaptersResponse.getCcName());
                listDataChild.put(chaptersResponse.getCcName(), chaptersResponse.getCourseTopics());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
