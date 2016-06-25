package com.simplelecture.main.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ComboCoursesAdapter;
import com.simplelecture.main.adapters.CoursesAdapter;
import com.simplelecture.main.adapters.ScreenSlidePagerAdapter;
import com.simplelecture.main.adapters.TestimonialsAdapter;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.util.ViewPagerIndicator;
import com.simplelecture.main.util.ZoomOutPageTransformer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView coursesList;

    RecyclerView recomendedCoursesView;

    RecyclerView mostViewedList;

    RecyclerView testimonialsList;

    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private ViewPagerIndicator pageIndicator;

    ComboCoursesAdapter comboCoursesAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComboCoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_home, container, false);

        pageIndicator = (ViewPagerIndicator) convertView.findViewById(R.id.page_indicator);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) convertView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setAdapter(mPagerAdapter);
        pageIndicator.setViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                pageIndicator.setViewPager(mPager);

                //pageIndicator.notifyDataSetChanged();

            }
        });

        ArrayList<String> data = new ArrayList<>();
        data.add("test1");
        data.add("test2");
        data.add("test3");
        data.add("test4");
        data.add("test5");
        data.add("test6");
        data.add("test7");
        data.add("test8");
        data.add("test9");
        data.add("test7");
        data.add("test8");
        data.add("test9");
        data.add("test7");
        data.add("test8");
        data.add("test9");

        //combo courses list
        coursesList = (RecyclerView) convertView.findViewById(R.id.courses_recycler_view);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        coursesList.setLayoutManager(linearLayoutManager);
        CoursesAdapter coursesAdapter = new CoursesAdapter(getActivity(), data);
        coursesList.setAdapter(coursesAdapter);

        //recomended courses list
        recomendedCoursesView = (RecyclerView) convertView.findViewById(R.id.recomended_recycler_view);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recomendedCoursesView.setLayoutManager(linearLayoutManager1);
        CoursesAdapter coursesAdapter1 = new CoursesAdapter(getActivity(), data);
        recomendedCoursesView.setAdapter(coursesAdapter);

        //most viewed list
        mostViewedList = (RecyclerView) convertView.findViewById(R.id.most_viewed_recycler_view);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mostViewedList.setLayoutManager(linearLayoutManager2);
        CoursesAdapter coursesAdapter2 = new CoursesAdapter(getActivity(), data);
        mostViewedList.setAdapter(coursesAdapter2);

        //testimonials list
        testimonialsList = (RecyclerView) convertView.findViewById(R.id.testimonials_recycler_view);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        testimonialsList.setLayoutManager(linearLayoutManager3);
        TestimonialsAdapter testimonialsAdapter = new TestimonialsAdapter(getActivity(), data);
        testimonialsList.setAdapter(testimonialsAdapter);

        return convertView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApiService.getApiService().doGetHomeScreenData(getActivity(), HomeFragment.this, "261");

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

    @Override
    public void parseResponse(String response) {


        

    }

    @Override
    public void showError(String error) {

    }
}
