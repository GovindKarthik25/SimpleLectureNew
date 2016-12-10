package com.simplelecture.main.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ViewPagerAdapter;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardMainFragment extends Fragment implements NetworkLayer, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;
    private int page;

    public DashboardMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardMainFragment newInstance(String param1, String param2) {
        DashboardMainFragment fragment = new DashboardMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View convertView = inflater.inflate(R.layout.fragment_dashboard_main, container, false);

        Util.doCreateDir();

        viewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) convertView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


//        if (bundle != null) {
//            int tabSelect = bundle.getInt("tabSelect");
//            page = bundle.getInt("Page");
//            Log.i("tabSelect", String.valueOf(tabSelect));
//            TabLayout.Tab tab = tabLayout.getTabAt(tabSelect);
//            tab.select();
//        }

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return false;
            }
        });



        return convertView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new DashboardFragment(), getResources().getString(R.string.dashboard));
        adapter.addFrag(new MyCoursesFragment(), getResources().getString(R.string.my_courses));
        adapter.addFrag(new TestPapersFragment(), getResources().getString(R.string.test_papers));
        adapter.addFrag(new ExerciseFragment(), getResources().getString(R.string.excercise));
        adapter.addFrag(new ForumFragment(), getResources().getString(R.string.forum));
        // adapter.addFrag(new SupportFragment(), getResources().getString(R.string.support));

        viewPager.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        
    }

    @Override
    public void parseResponse(String response) {

    }

    @Override
    public void showError(String error) {

    }

}
