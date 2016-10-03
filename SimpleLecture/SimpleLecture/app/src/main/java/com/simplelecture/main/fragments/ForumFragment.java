package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.ForumListAdapter;
import com.simplelecture.main.adapters.SpinnerCustomAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.ForumCourseModel;
import com.simplelecture.main.model.viewmodel.ForumGetModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumFragment extends Fragment implements NetworkLayer, AdapterView.OnItemSelectedListener {
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
    private RecyclerView recyclerView_Forum;
    private CoordinatorLayout coordinatorLayout;
    private Spinner spinner_Forum;
    private Toolbar toolbar;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private ForumListAdapter forumListAdapter;
    private List<ForumGetModel> forumGetModelLst;
    private ArrayList<ForumCourseModel> forumCourseModelLst = new ArrayList<ForumCourseModel>();
    private SpinnerCustomAdapter spinnerForumAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForumFragment newInstance(String param1, String param2) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ForumFragment() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View convertView = inflater.inflate(R.layout.fragment_forum, container, false);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);

        recyclerView_Forum = (RecyclerView) convertView.findViewById(R.id.recyclerView_Forum);
        spinner_Forum = (Spinner) convertView.findViewById(R.id.spinner_Forum);

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            loadGetForumCourse();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView_Forum.setLayoutManager(linearLayoutManager);

            if (forumListAdapter != null) {
                forumListAdapter = new ForumListAdapter(getActivity(), forumGetModelLst);
                recyclerView_Forum.setAdapter(forumListAdapter);

                forumListAdapter.setOnItemClickListener(onItemClickListener);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGetForumGet(String courseId) {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_FORUMGET;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardForumCourseGet(getActivity(), ForumFragment.this, courseId);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGetForumCourse() {
        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_FORUMCOURSES;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardForumCourse(getActivity(), ForumFragment.this);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                ForumGetModel forumGetModelObj = forumGetModelLst.get(position);
                new ViewManager().gotoForumWebViewActivity(getActivity(), forumGetModelObj.getPageUrlForumDetails(), forumGetModelObj.getName());
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
            JsonArray jArray;
            JsonParser parser = new JsonParser();

            forumGetModelLst = new ArrayList<ForumGetModel>();


            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_FORUMGET)) {

                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");


                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        ForumGetModel forumGetModelObj = gson.fromJson(obj, ForumGetModel.class);
                        forumGetModelLst.add(forumGetModelObj);
                    }

                    if(forumGetModelLst.size() == 0){
                        Log.i("forumGetModelLst", "No Data Found:-" + forumGetModelLst.size());
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                    forumListAdapter = new ForumListAdapter(getActivity(), forumGetModelLst);
                    recyclerView_Forum.setAdapter(forumListAdapter);
                    forumListAdapter.notifyDataSetChanged();


                    forumListAdapter.setOnItemClickListener(onItemClickListener);

                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_FORUMCOURSES)) {
                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");
                    forumCourseModelLst.clear();
                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        ForumCourseModel forumCourseModelObj = gson.fromJson(obj, ForumCourseModel.class);
                        forumCourseModelLst.add(forumCourseModelObj);
                    }

                    spinnerForumAdapter = new SpinnerCustomAdapter(getActivity(), forumCourseModelLst);
                    spinner_Forum.setAdapter(spinnerForumAdapter);

                    spinner_Forum.setOnItemSelectedListener(this);

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
        if (error.isEmpty()) {
            error = "Error in connection";
        }

        snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p/>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            String item = forumCourseModelLst.get(position).getId();

            Log.i("item--", item);


            loadGetForumGet(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
