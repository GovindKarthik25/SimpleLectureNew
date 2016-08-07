package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.HomeActivity;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.ComboCoursesAdapter;
import com.simplelecture.main.adapters.HomeComboCoursesAdapter;
import com.simplelecture.main.adapters.HomeCoursesAdapter;
import com.simplelecture.main.adapters.HomeMostViewedAdapter;
import com.simplelecture.main.adapters.HomePromoSlidePagerAdapter;
import com.simplelecture.main.adapters.TestimonialsAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.CourseDetailsController;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.HomeBannersModel;
import com.simplelecture.main.model.viewmodel.HomeCoursesModel;
import com.simplelecture.main.model.viewmodel.HomePageResponseModel;
import com.simplelecture.main.model.viewmodel.HomePopularCoursesModel;
import com.simplelecture.main.model.viewmodel.HomeTestimonialsModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.courseFeatures;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.ViewPagerIndicator;
import com.simplelecture.main.util.ZoomOutPageTransformer;
import com.simplelecture.main.viewManager.ViewManager;

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
public class HomeFragment extends Fragment implements NetworkLayer, View.OnClickListener {
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
    private String param_get_ServiceCallResult = "";
    private AlertMessageManagement alertMessageManagement;
    private SnackBarManagement snack;
    private LinearLayout courses_titleLinearLayout, recomended_titleLinearLayout, most_view_titleLinearLayout, testimonials_titleLinearLayout;
    private LinearLayout cart_stripLinearLayout;
    private TextView cart_CountTextView;
    private HomePageResponseModel homePageResponseModelobj;
    private TextView viewAllCourse;
    private TextView viewAllComboCourse;
    private CoordinatorLayout coordinatorLayout;
    private CourseDetailsResponseModel courseDetailsResponseModel;
    private List<courseFeatures> courseFeaturesLstArray;

    private HomeComboCoursesAdapter homeComboCoursesAdapter;
    private HomeMostViewedAdapter homeMostViewedAdapter;
    private TestimonialsAdapter testimonialsAdapter;
    private HomeCoursesAdapter homeCoursesAdapter;
    private SessionManager sessionManager;


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
            alertMessageManagement = new AlertMessageManagement(getContext(), new AlertDialogClick());
            sessionManager = SessionManager.getInstance();
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

            callHomeDataService();

            setHasOptionsMenu(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_home, container, false);

        pageIndicator = (ViewPagerIndicator) convertView.findViewById(R.id.page_indicator);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
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
        viewAllCourse = (TextView) convertView.findViewById(R.id.viewAllCourse);
        viewAllComboCourse = (TextView) convertView.findViewById(R.id.viewAllComboCourse);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) convertView.findViewById(R.id.pager);

        //combo courses list
        coursesList = (RecyclerView) convertView.findViewById(R.id.courses_recycler_view);

        //recomended courses list
        recomendedCoursesView = (RecyclerView) convertView.findViewById(R.id.recomended_recycler_view);

        //most Popular list
        mostViewedList = (RecyclerView) convertView.findViewById(R.id.most_viewed_recycler_view);

        //testimonials list
        testimonialsList = (RecyclerView) convertView.findViewById(R.id.testimonials_recycler_view);

        return convertView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (homePageResponseModelobj != null && homePageResponseModelobj.getBannersLst() != null) {
            mPagerAdapter = new HomePromoSlidePagerAdapter(getFragmentManager(), homePageResponseModelobj.getBannersLst());
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        coursesList.setLayoutManager(linearLayoutManager);

        //recomended courses list
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recomendedCoursesView.setLayoutManager(linearLayoutManager1);

        //most Popular list
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mostViewedList.setLayoutManager(linearLayoutManager2);

        //testimonials list
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        testimonialsList.setLayoutManager(linearLayoutManager3);

        viewAllCourse.setOnClickListener(HomeFragment.this);
        viewAllComboCourse.setOnClickListener(HomeFragment.this);
        cart_stripLinearLayout.setOnClickListener(HomeFragment.this);

        updateDisplayAndSetTheItem();

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

    private String cID;
    private String cComboName;
    OnItemClickListener onItemClickListenerCourseCombos = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                if (sessionManager.isLoginStatus()) {

                    CourseCombos myCoursesObj = homePageResponseModelobj.getCourseCombosLst().get(position);

                    cID = myCoursesObj.getcId();
                    cComboName = myCoursesObj.getCourses();

                    getCourseDetails();
                } else {
                    alertMessageManagement.alertDialogActivation(getActivity(), 2, getResources().getString(R.string.alert), getResources().getString(R.string.pleaseLogin), getResources().getString(R.string.no), getResources().getString(R.string.yes));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    OnItemClickListener onItemClickListenerCourse = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                if (sessionManager.isLoginStatus()) {

                    HomeCoursesModel coursesObj = homePageResponseModelobj.getCoursesLst().get(position);
                    cID = String.valueOf(coursesObj.getcId());

                    getCourseDetails();

                } else {
                    alertMessageManagement.alertDialogActivation(getActivity(), 2, getResources().getString(R.string.alert), getResources().getString(R.string.pleaseLogin), getResources().getString(R.string.no), getResources().getString(R.string.yes));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    OnItemClickListener onItemClickListenerMostPopular = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                if (sessionManager.isLoginStatus()) {
                    HomePopularCoursesModel popularCoursesObj = homePageResponseModelobj.getPopularCoursesLst().get(position);
                    cID = String.valueOf(popularCoursesObj.getcId());
                    cComboName = popularCoursesObj.getCatName();
                    getCourseDetails();
                } else {
                    alertMessageManagement.alertDialogActivation(getActivity(), 2, getResources().getString(R.string.alert), getResources().getString(R.string.pleaseLogin), getResources().getString(R.string.no), getResources().getString(R.string.yes));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void getCourseDetails() {

        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            param_get_ServiceCallResult = Constants.GET_COURSEDETAILS;
            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
            ApiService.getApiService().doGetCourseDetails(getActivity(), HomeFragment.this, cID);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }
    }


    private void callHomeDataService() {

        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_HOME_PAGE;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetHomeScreenData(getActivity(), HomeFragment.this, Util.getFromPrefrences(getContext(), "SelectYourCategoryID"));
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDisplayAndSetTheItem() {
        //combo courses list

        if (homePageResponseModelobj != null && homePageResponseModelobj.getCourseCombosLst() != null) {
            homeComboCoursesAdapter = new HomeComboCoursesAdapter(getActivity(), homePageResponseModelobj.getCourseCombosLst());
            coursesList.setAdapter(homeComboCoursesAdapter);
        }

        //recomended courses list
        if (homePageResponseModelobj != null && homePageResponseModelobj.getCoursesLst() != null) {
            homeCoursesAdapter = new HomeCoursesAdapter(getActivity(), homePageResponseModelobj.getCoursesLst());
            recomendedCoursesView.setAdapter(homeCoursesAdapter);
        }

        //most Popular list
        if (homePageResponseModelobj != null && homePageResponseModelobj.getPopularCoursesLst() != null) {
            homeMostViewedAdapter = new HomeMostViewedAdapter(getActivity(), homePageResponseModelobj.getPopularCoursesLst());
            mostViewedList.setAdapter(homeMostViewedAdapter);
        }


    }

    private void displayAndSetTheItem() {
        try {
            if (homePageResponseModelobj.getMyCoursesCount() != 0) {
                cart_stripLinearLayout.setVisibility(View.VISIBLE);
                cart_CountTextView.setText(getResources().getString(R.string.Youhave) + " " + String.valueOf(homePageResponseModelobj.getMyCoursesCount()) + " " + getResources().getString(R.string.coursesinyouraccountchecknow));
            } else {
                cart_stripLinearLayout.setVisibility(View.GONE);
            }

            mPagerAdapter = new HomePromoSlidePagerAdapter(getFragmentManager(), homePageResponseModelobj.getBannersLst());
            mPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mPager.setAdapter(mPagerAdapter);
            pageIndicator.setViewPager(mPager);

            if (homePageResponseModelobj.getCourseCombosLst().size() > 0) {
                courses_titleLinearLayout.setVisibility(View.VISIBLE);
            }
            if (homePageResponseModelobj.getCoursesLst().size() > 0) {
                recomended_titleLinearLayout.setVisibility(View.VISIBLE);
            }
            if (homePageResponseModelobj.getPopularCoursesLst().size() > 0) {
                most_view_titleLinearLayout.setVisibility(View.VISIBLE);
            }

            //combo courses list
            homeComboCoursesAdapter = new HomeComboCoursesAdapter(getActivity(), homePageResponseModelobj.getCourseCombosLst());
            coursesList.setAdapter(homeComboCoursesAdapter);

            if (homeComboCoursesAdapter != null) {
                homeComboCoursesAdapter.setOnItemClickListener(onItemClickListenerCourseCombos);
            }

            //recomended courses list
            homeCoursesAdapter = new HomeCoursesAdapter(getActivity(), homePageResponseModelobj.getCoursesLst());
            recomendedCoursesView.setAdapter(homeCoursesAdapter);

            if (homeCoursesAdapter != null) {
                homeCoursesAdapter.setOnItemClickListener(onItemClickListenerCourse);
            }

            //most Popular list
            homeMostViewedAdapter = new HomeMostViewedAdapter(getActivity(), homePageResponseModelobj.getPopularCoursesLst());
            mostViewedList.setAdapter(homeMostViewedAdapter);

            if (homeMostViewedAdapter != null) {
                homeMostViewedAdapter.setOnItemClickListener(onItemClickListenerMostPopular);
            }

            testimonialsAdapter = new TestimonialsAdapter(getActivity(), homePageResponseModelobj.getHomeTestimonialsModelLst());
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


            List<HomeBannersModel> bannersLstArray = new ArrayList<HomeBannersModel>();
            List<HomeCoursesModel> coursesLstArray = new ArrayList<HomeCoursesModel>();
            List<CourseCombos> courseCombosLstArray = new ArrayList<CourseCombos>();
            List<HomePopularCoursesModel> homePopularCoursesModelLstArray = new ArrayList<HomePopularCoursesModel>();
            List<HomeTestimonialsModel> homeTestimonialsModelLstArray = new ArrayList<HomeTestimonialsModel>();

            JsonArray jArray;
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_HOME_PAGE)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");

                    homePageResponseModelobj = gson.fromJson(dataContent, HomePageResponseModel.class);

                    JSONObject jSONObject = new JSONObject(dataContent);
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
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }

            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSEDETAILS)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    courseDetailsResponseModel = new CourseDetailsController().getCourseDetails(dataContent);

                    // Log.i("courseDetailsResp***", courseDetailsResponseModel.toString() + " ***** ");

                    if (courseDetailsResponseModel.isCombo()) {
                        courseDetailsResponseModel.setcComboName(cComboName);

                        new ViewManager().gotoComboCourseView(getActivity(), courseDetailsResponseModel);
                    } else {

                        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                            param_get_ServiceCallResult = Constants.GET_COURSECHAPTERS;

                            pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                            //My HomeCoursesModel service
                            ApiService.getApiService().doGetChapters(getActivity(), HomeFragment.this, cID);
                        } else {
                            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                        }

                    }
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSECHAPTERS)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject = new JSONObject(response);

                    String dataContent = jSONObject.getString("data");


                    List<ChaptersResponseModel> chaptersResponseModelLstArray = new CourseDetailsController().getChaptersResponse(dataContent);

                    courseDetailsResponseModel.setChaptersResponseModel(chaptersResponseModelLstArray);

                    new ViewManager().gotoSingleCourseView(getActivity(), courseDetailsResponseModel);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }
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

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.home_fragment_menu, menu);
        menu.findItem(R.id.action_filter).setVisible(true);
        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String displayName = "HomeFragment";
                SelectYourCoursesFragment selectYourCoursesFragment = new SelectYourCoursesFragment().newInstance(displayName, "");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                selectYourCoursesFragment.show(fragmentManager, "HomeFragment");

                return true;
            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        try {
            if (sessionManager.isLoginStatus()) {
                if (v == viewAllComboCourse || v == viewAllCourse) {

                    CourseCategoriesFragment courseCategoriesFragment = new CourseCategoriesFragment();
                    ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.navigation_drawer_courseCategories));

                    this.getFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, courseCategoriesFragment, "")
                            .addToBackStack(null)
                            .commit();

                } else if (v == cart_stripLinearLayout) {

                    new ViewManager().gotoCartActivity(getActivity());
                }

            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 2, getResources().getString(R.string.alert), getResources().getString(R.string.pleaseLogin), getResources().getString(R.string.no), getResources().getString(R.string.yes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class AlertDialogClick implements AlertMessageManagement.onCustomAlertDialogListener {
        @Override
        public void onClickResult(DialogInterface dialog, int whichButton) {
            if (whichButton == -2) { // negative Button 2
                dialog.cancel();

            } else if (whichButton == -1) { //Postive -1
                new ViewManager().gotoLoginView(getActivity());
            }
        }
    }
}
