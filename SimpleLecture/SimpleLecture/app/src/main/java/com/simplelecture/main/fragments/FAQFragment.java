package com.simplelecture.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.FAQExpandableListAdapter;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseFaqs;
import com.simplelecture.main.util.SnackBarManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raos on 2/14/2016.
 */
public class FAQFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SnackBarManagement snack;
    private CourseDetailsResponseModel courseDetailsResponseModelObj;
    private ExpandableListView lvExpFAQ;
    private List<CourseFaqs> courseFaqsResponselist;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private FAQExpandableListAdapter listFAQAdapter;
    private ArrayList<String> listChildData;

    public FAQFragment() {
        // Required empty public constructor
    }

    public static FAQFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        FAQFragment fragment = new FAQFragment();
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
        View convertView = inflater.inflate(R.layout.fragment_faq, container, false);

        // get the listview
        lvExpFAQ = (ExpandableListView) convertView.findViewById(R.id.lvExpFAQ);

        courseFaqsResponselist = courseDetailsResponseModelObj.getCourseFaqs();

        Log.i("courseFaqsResponselist", courseFaqsResponselist.toString());

        // preparing list data
        prepareListData();

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        listFAQAdapter = new FAQExpandableListAdapter(getActivity(), courseFaqsResponselist);

        // setting list adapter
        lvExpFAQ.setAdapter(listFAQAdapter);

        lvExpFAQ.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            private int lastExpandedGroupPosition;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != lastExpandedGroupPosition) {
                    lvExpFAQ.collapseGroup(lastExpandedGroupPosition);
                }
                lastExpandedGroupPosition = groupPosition;
            }
        });
    }


    /*
     * Preparing the list data
	 */
    private void prepareListData() {

        try {
            listDataHeader = new ArrayList<String>();
            listChildData = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            for (CourseFaqs courseFaqsResponse : courseFaqsResponselist) {
                listDataHeader.add(courseFaqsResponse.getName());
                listChildData.add(courseFaqsResponse.getName());
                listDataChild.put(courseFaqsResponse.getName(), listChildData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}