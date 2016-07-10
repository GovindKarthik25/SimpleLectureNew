package com.simplelecture.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.util.SnackBarManagement;

/**
 * Created by Raos on 2/14/2016.
 */
public class CourseBenifitsFragment extends Fragment {

    private TextView textView_courseBenifits;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SnackBarManagement snack;
    private CourseDetailsResponseModel courseDetailsResponseModelObj;
    private String courseBenifitsResponse;

    public CourseBenifitsFragment() {
        // Required empty public constructor
    }

    public static CourseBenifitsFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        CourseBenifitsFragment fragment = new CourseBenifitsFragment();
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
        View convertView = inflater.inflate(R.layout.fragment_course_benifits, container, false);

        textView_courseBenifits = (TextView) convertView.findViewById(R.id.textView_courseBenifits);

        courseBenifitsResponse = courseDetailsResponseModelObj.getcBenefits();

        textView_courseBenifits.setText(Html.fromHtml(courseBenifitsResponse.toString()));

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
