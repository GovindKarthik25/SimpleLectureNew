package com.simplelecture.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.CourseFeatureAdapter;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.courseFeatures;
import com.simplelecture.main.util.SnackBarManagement;

import java.util.List;

/**
 * Created by Raos on 2/14/2016.
 */
public class CourseFeatureFragment extends Fragment {

    private ListView courseFeatureView;
    private CourseFeatureAdapter courseFeatureAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SnackBarManagement snack;
    private CourseDetailsResponseModel courseDetailsResponseModelObj;
    private List<courseFeatures> courseFeaturesResponseList;

    public CourseFeatureFragment() {
        // Required empty public constructor
    }


    public static CourseFeatureFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        CourseFeatureFragment fragment = new CourseFeatureFragment();
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
        View convertView = inflater.inflate(R.layout.fragment_course_feature, container, false);

        courseFeatureView = (ListView) convertView.findViewById(R.id.courseFeatureView);

        courseFeaturesResponseList = courseDetailsResponseModelObj.getCourseFeature();

        return convertView;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (courseFeaturesResponseList != null) {
            courseFeatureAdapter = new CourseFeatureAdapter(getActivity(), courseFeaturesResponseList);
            courseFeatureView.setAdapter(courseFeatureAdapter);
        }


    }
}
