package com.simplelecture.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ExpandableListAdapter;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.http.TransactionProcessor;
import com.simplelecture.main.transactions.GetCoursesIndexTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raos on 2/14/2016.
 */
public class CourseIndexFragment extends Fragment implements NetworkLayer {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public CourseIndexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        GetCoursesIndexTransaction getCoursesIndexTransaction = new GetCoursesIndexTransaction(null, getActivity());
//        TransactionProcessor transactionProcessor = new TransactionProcessor(this);
//        transactionProcessor.execute(getCoursesIndexTransaction);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_course_index, container, false);

        // get the listview
        expListView = (ExpandableListView) convertView.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return convertView;
    }

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
    }

    @Override
    public void parseResponse(String response) {

//        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

        try {
            JSONObject jsonObject = new JSONObject(response);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showError(String error) {

    }
}
