package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ComboCoursesAdapter;
import com.simplelecture.main.adapters.HomeComboCoursesAdapter;
import com.simplelecture.main.adapters.HomeCoursesAdapter;
import com.simplelecture.main.adapters.HomeMostViewedAdapter;
import com.simplelecture.main.adapters.HomePromoSlidePagerAdapter;
import com.simplelecture.main.adapters.TestimonialsAdapter;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.HomeBannersModel;
import com.simplelecture.main.model.viewmodel.HomeCoursesModel;
import com.simplelecture.main.model.viewmodel.HomePageResponseModel;
import com.simplelecture.main.model.viewmodel.HomePopularCoursesModel;
import com.simplelecture.main.model.viewmodel.HomeTestimonialsModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.ViewPagerIndicator;
import com.simplelecture.main.util.ZoomOutPageTransformer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private ProgressDialog pd;
    private boolean param_get_HomeScreenData = false;
    private AlertMessageManagement alertMessageManagement;
    private SnackBarManagement snack;
    private ArrayList<HomePageResponseModel> homePageResponseModelLstArray;
    private List<HomeBannersModel> bannersLstArray;
    private List<CourseCombos> courseCombosLstArray;
    private List<HomeCoursesModel> coursesLstArray;
    private List<HomePopularCoursesModel> homePopularCoursesModelLstArray;
    private List<HomeTestimonialsModel> homeTestimonialsModelLstArray;
    private LinearLayout courses_titleLinearLayout, recomended_titleLinearLayout, most_view_titleLinearLayout, testimonials_titleLinearLayout;
    private LinearLayout cart_stripLinearLayout;
    private TextView cart_CountTextView;
    private HomePageResponseModel homePageResponseModelobj;


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
        try {

            snack = new SnackBarManagement(getContext());
            alertMessageManagement = new AlertMessageManagement(getContext());

            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

            callHomeDataService();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_home, container, false);

        pageIndicator = (ViewPagerIndicator) convertView.findViewById(R.id.page_indicator);

        courses_titleLinearLayout = (LinearLayout) convertView.findViewById(R.id.courses_titleLinearLayout);
        courses_titleLinearLayout.setVisibility(View.GONE);
        recomended_titleLinearLayout = (LinearLayout) convertView.findViewById(R.id.recomended_titleLinearLayout);
        recomended_titleLinearLayout.setVisibility(View.GONE);
        most_view_titleLinearLayout = (LinearLayout) convertView.findViewById(R.id.most_view_titleLinearLayout);
        most_view_titleLinearLayout.setVisibility(View.GONE);
        testimonials_titleLinearLayout = (LinearLayout) convertView.findViewById(R.id.testimonials_titleLinearLayout);
        cart_stripLinearLayout = (LinearLayout) convertView.findViewById(R.id.cart_stripLinearLayout);
        cart_stripLinearLayout.setVisibility(View.GONE);
        cart_CountTextView = (TextView) convertView.findViewById(R.id.cart_CountTextView);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) convertView.findViewById(R.id.pager);
        if (bannersLstArray != null) {
            mPagerAdapter = new HomePromoSlidePagerAdapter(getFragmentManager(), bannersLstArray);
            mPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mPager.setAdapter(mPagerAdapter);
            pageIndicator.setViewPager(mPager);
        }
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

        //combo courses list
        coursesList = (RecyclerView) convertView.findViewById(R.id.courses_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        coursesList.setLayoutManager(linearLayoutManager);
        if (courseCombosLstArray != null) {
            HomeComboCoursesAdapter homeComboCoursesAdapter = new HomeComboCoursesAdapter(getActivity(), courseCombosLstArray);
            coursesList.setAdapter(homeComboCoursesAdapter);
        }

        //recomended courses list
        recomendedCoursesView = (RecyclerView) convertView.findViewById(R.id.recomended_recycler_view);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recomendedCoursesView.setLayoutManager(linearLayoutManager1);
        if (coursesLstArray != null) {
            HomeCoursesAdapter homeCoursesAdapter = new HomeCoursesAdapter(getActivity(), coursesLstArray);
            recomendedCoursesView.setAdapter(homeCoursesAdapter);
        }

        //most Popular list
        mostViewedList = (RecyclerView) convertView.findViewById(R.id.most_viewed_recycler_view);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mostViewedList.setLayoutManager(linearLayoutManager2);
        if (homePopularCoursesModelLstArray != null) {
            HomeMostViewedAdapter homeMostViewedAdapter = new HomeMostViewedAdapter(getActivity(), homePopularCoursesModelLstArray);
            mostViewedList.setAdapter(homeMostViewedAdapter);
        }

        //testimonials list
        testimonialsList = (RecyclerView) convertView.findViewById(R.id.testimonials_recycler_view);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        testimonialsList.setLayoutManager(linearLayoutManager3);
        if (homeTestimonialsModelLstArray != null) {
            TestimonialsAdapter testimonialsAdapter = new TestimonialsAdapter(getActivity(), homeTestimonialsModelLstArray);
            testimonialsList.setAdapter(testimonialsAdapter);
        }

        return convertView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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


    private void callHomeDataService() {

        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_HomeScreenData = true;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetHomeScreenData(getActivity(), HomeFragment.this, Util.getFromPrefrences(getContext(), "SelectYourCategoryID"));
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void displayAndSetTheItem() {
        try {

            if (homePageResponseModelobj.getMyCoursesCount() == 0) {
                cart_stripLinearLayout.setVisibility(View.VISIBLE);
                cart_CountTextView.setText(getResources().getString(R.string.Youhave) + " "+ homePageResponseModelobj.getMyCoursesCount() + " " + getResources().getString(R.string.coursesinyouraccountchecknow));
            } else {
                cart_stripLinearLayout.setVisibility(View.VISIBLE);
            }

            mPagerAdapter = new HomePromoSlidePagerAdapter(getFragmentManager(), bannersLstArray);
            mPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mPager.setAdapter(mPagerAdapter);
            pageIndicator.setViewPager(mPager);

            if (courseCombosLstArray.size() > 0) {
                courses_titleLinearLayout.setVisibility(View.VISIBLE);
            }
            if (coursesLstArray.size() > 0) {
                recomended_titleLinearLayout.setVisibility(View.VISIBLE);
            }
            if (homePopularCoursesModelLstArray.size() > 0) {
                most_view_titleLinearLayout.setVisibility(View.VISIBLE);
            }

            HomeComboCoursesAdapter homeComboCoursesAdapter = new HomeComboCoursesAdapter(getActivity(), courseCombosLstArray);
            coursesList.setAdapter(homeComboCoursesAdapter);

            HomeCoursesAdapter homeCoursesAdapter = new HomeCoursesAdapter(getActivity(), coursesLstArray);
            recomendedCoursesView.setAdapter(homeCoursesAdapter);

            HomeMostViewedAdapter homeMostViewedAdapter = new HomeMostViewedAdapter(getActivity(), homePopularCoursesModelLstArray);
            mostViewedList.setAdapter(homeMostViewedAdapter);

            TestimonialsAdapter testimonialsAdapter = new TestimonialsAdapter(getActivity(), homeTestimonialsModelLstArray);
            testimonialsList.setAdapter(testimonialsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void parseResponse(String response) {
        /*Log.i("parseResponse--***", response.toString());*/
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            homePageResponseModelLstArray = new ArrayList<HomePageResponseModel>();
            bannersLstArray = new ArrayList<HomeBannersModel>();
            coursesLstArray = new ArrayList<HomeCoursesModel>();
            courseCombosLstArray = new ArrayList<CourseCombos>();
            homePopularCoursesModelLstArray = new ArrayList<HomePopularCoursesModel>();
            homeTestimonialsModelLstArray = new ArrayList<HomeTestimonialsModel>();

            JsonArray jArray;
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            if (param_get_HomeScreenData) {


                homePageResponseModelobj = gson.fromJson(response, HomePageResponseModel.class);
                JSONObject jSONObject = new JSONObject(response);

                String bannersLstResponse = jSONObject.getString("Banners");
                jArray = parser.parse(bannersLstResponse).getAsJsonArray();
                for (JsonElement obj : jArray) {
                    HomeBannersModel bannersobj = gson.fromJson(obj, HomeBannersModel.class);
                    bannersLstArray.add(bannersobj);
                }

                String coursesLstResponse = jSONObject.getString("Courses");

                if (coursesLstResponse != null && !coursesLstResponse.equals("null")) {
                    jArray = parser.parse(coursesLstResponse).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        HomeCoursesModel homeCoursesModelobj = gson.fromJson(obj, HomeCoursesModel.class);
                        coursesLstArray.add(homeCoursesModelobj);
                    }
                }

                String comboCoursesLstResponse = jSONObject.getString("ComboCourses");
                jArray = parser.parse(comboCoursesLstResponse).getAsJsonArray();
                for (JsonElement obj : jArray) {
                    CourseCombos courseCombosModelobj = gson.fromJson(obj, CourseCombos.class);
                    courseCombosLstArray.add(courseCombosModelobj);
                }

                String popularCoursesModelLstResponse = jSONObject.getString("PopularCourses");
                jArray = parser.parse(popularCoursesModelLstResponse).getAsJsonArray();
                for (JsonElement obj : jArray) {
                    HomePopularCoursesModel homePopularCoursesModelobj = gson.fromJson(obj, HomePopularCoursesModel.class);
                    homePopularCoursesModelLstArray.add(homePopularCoursesModelobj);
                }

                String homeTestimonialsModelLstResponse = jSONObject.getString("Testimonials");
                jArray = parser.parse(homeTestimonialsModelLstResponse).getAsJsonArray();
                for (JsonElement obj : jArray) {
                    HomeTestimonialsModel homeTestimonialsModelobj = gson.fromJson(obj, HomeTestimonialsModel.class);
                    homeTestimonialsModelLstArray.add(homeTestimonialsModelobj);
                }

                homePageResponseModelobj.setBannersLst(bannersLstArray);
                homePageResponseModelobj.setCoursesLst(coursesLstArray);
                homePageResponseModelobj.setCourseCombosLst(courseCombosLstArray);
                homePageResponseModelobj.setPopularCoursesLst(homePopularCoursesModelLstArray);
                homePageResponseModelobj.setHomeTestimonialsModelLst(homeTestimonialsModelLstArray);

                displayAndSetTheItem();

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

        param_get_HomeScreenData = false;
    }


}
