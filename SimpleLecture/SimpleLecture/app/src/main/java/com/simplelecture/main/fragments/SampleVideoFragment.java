package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.DemoTutorialAdapter;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.SampleVideoResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SampleVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SampleVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SampleVideoFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private ProgressDialog pd;
    private boolean param_get_SampleVideoTutorial;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView sampleVideoRecycler_view;
    private ArrayList<SampleVideoResponseModel> sampleVideoResponseModelLstArray;
    private DemoTutorialAdapter demoTutorialAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SampleVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SampleVideoFragment newInstance(String param1, String param2) {
        SampleVideoFragment fragment = new SampleVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SampleVideoFragment() {
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

            loadGetSampleVideoTutorial();

            setHasOptionsMenu(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View convertView = inflater.inflate(R.layout.fragment_samplevideo, container, false);
        FloatingActionButton fabSampleVideo = (FloatingActionButton) convertView.findViewById(R.id.fabSampleVideo);
//        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
        sampleVideoRecycler_view = (RecyclerView) convertView.findViewById(R.id.sampleVideoRecycler_view);
        sampleVideoRecycler_view.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        sampleVideoRecycler_view.setLayoutManager(gridLayoutManager);

        loadRecyclerView();

        return convertView;
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


    private void loadGetSampleVideoTutorial() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_SampleVideoTutorial = true;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetVideoSampleTutorial(getActivity(), SampleVideoFragment.this);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRecyclerView() {


        if (sampleVideoResponseModelLstArray != null) {
            demoTutorialAdapter = new DemoTutorialAdapter(getActivity(), sampleVideoResponseModelLstArray);
            sampleVideoRecycler_view.setAdapter(demoTutorialAdapter);

            demoTutorialAdapter.setOnItemClickListener(onItemClickListener);
        }
    }


    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {

                String videoURl = sampleVideoResponseModelLstArray.get(position).getSvUrl();
                new ViewManager().gotoVideoPlayerView(getContext(), "SampleVideoFragment", 0, videoURl);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void parseResponse(String response) {
        try {

            if (pd.isShowing()) {
                pd.cancel();
            }

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            if (param_get_SampleVideoTutorial) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");

                    JsonArray jarray = parser.parse(dataContent).getAsJsonArray();

                    sampleVideoResponseModelLstArray = new ArrayList<SampleVideoResponseModel>();
                    for (JsonElement obj : jarray) {
                        SampleVideoResponseModel sampleVideoResponseModelObj = gson.fromJson(obj, SampleVideoResponseModel.class);
                        sampleVideoResponseModelLstArray.add(sampleVideoResponseModelObj);
                    }

                    loadRecyclerView();

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

        param_get_SampleVideoTutorial = false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.home, menu);
        menu.findItem(R.id.action_filter).setVisible(true);
        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                SelectYourCoursesFragment selectYourCoursesFragment = new SelectYourCoursesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                selectYourCoursesFragment.show(fragmentManager, "SampleVideoFragment");
                return true;
            }
        });

    }
}
