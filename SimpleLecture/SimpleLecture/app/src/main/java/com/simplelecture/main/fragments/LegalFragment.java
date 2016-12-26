package com.simplelecture.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.HomePageResponseModel;
import com.simplelecture.main.model.viewmodel.LegalSupportAboutusResponse;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LegalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LegalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegalFragment extends Fragment implements NetworkLayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView listView;
  //  private HomePageResponseModel homePageResponseModelObj;
    private String urlLink = "";
    private String namePage;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private LegalSupportAboutusResponse legalSupportAboutusResponseObj;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SupportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LegalFragment newInstance(String param1, String param2) {
        LegalFragment fragment = new LegalFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LegalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        snack = new SnackBarManagement(getContext());
        alertMessageManagement = new AlertMessageManagement(getContext(), null);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        callLegalService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_legalpolicy, container, false);

        if (getArguments() != null) {
            //homePageResponseModelObj = (HomePageResponseModel) getArguments().getSerializable(ARG_PARAM1);
        }


        listView = (ListView) convertView.findViewById(R.id.listViewLegal);


        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] mobileArray = {"Term & Condition","Disclaimer","Privacy Policy","Cancellation & Refund Policy","Shipping & Delivery Policy"};

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.adapter_legalrow, mobileArray);

        listView.setAdapter(adapter);
       // listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    namePage = parent.getItemAtPosition(position).toString();
                    urlLink = legalSupportAboutusResponseObj.getPageUrlTermsAndConditions();
                } else if(position == 1){
                    namePage = parent.getItemAtPosition(position).toString();
                    urlLink = legalSupportAboutusResponseObj.getPageUrlDisclaimer();
                }  else if(position == 2){
                    namePage = parent.getItemAtPosition(position).toString();
                    urlLink = legalSupportAboutusResponseObj.getPageUrlPrivacyPolicy();
                } else if(position == 3){
                    namePage = parent.getItemAtPosition(position).toString();
                    urlLink = legalSupportAboutusResponseObj.getPageUrlCancellationAndRefundPolicy();
                } else if(position == 4){
                    namePage = parent.getItemAtPosition(position).toString();
                    urlLink = legalSupportAboutusResponseObj.getPageUrlShippingAndDeliveryPolicy();
                }

                Log.i("namePage",namePage);

                new ViewManager().gotoPolicyWebview(getActivity(), urlLink, namePage);
            }
        });



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

    private void callLegalService() {

        try {
            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_HOME_FOOTERLINKS;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetLegalSupport(getActivity(), LegalFragment.this);
            } else {
                alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void parseResponse(String response) {

        try {
            Gson gson = new Gson();
            if (pd.isShowing()) {
                pd.cancel();
            }
            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_HOME_FOOTERLINKS)) {
                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");
                    legalSupportAboutusResponseObj = gson.fromJson(dataContent, LegalSupportAboutusResponse.class);


                }
            }
            }catch(Exception e){
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

        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}


