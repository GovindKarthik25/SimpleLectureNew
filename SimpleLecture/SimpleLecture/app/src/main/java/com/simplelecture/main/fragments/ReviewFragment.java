package com.simplelecture.main.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

/**
 * Created by Raos on 2/14/2016.
 */
public class ReviewFragment extends Fragment implements View.OnClickListener, NetworkLayer {

    private Button buttonPostReview;
    private EditText editText_Review;
    private TextInputLayout input_layout_review;
    private SnackBarManagement snack;
    private ProgressDialog pd;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CourseDetailsResponseModel courseDetailsResponseModelObj;
    private CoordinatorLayout coordinatorLayout;
    private String param_get_ServiceCallResult;
    private OutputResponseModel outputResponseModel;
    private AlertMessageManagement alertMessageManagement;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, courseDetailsResponseModelObj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        snack = new SnackBarManagement(getActivity());
        alertMessageManagement = new AlertMessageManagement(getActivity());

        if (getArguments() != null) {
            courseDetailsResponseModelObj = (CourseDetailsResponseModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View convertView = inflater.inflate(R.layout.fragment_review, container, false);
        input_layout_review = (TextInputLayout) convertView.findViewById(R.id.input_layout_review);
        editText_Review = (EditText) convertView.findViewById(R.id.editText_Review);
        buttonPostReview = (Button) convertView.findViewById(R.id.buttonPostReview);
        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);
        buttonPostReview.setOnClickListener(this);

        return convertView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        try {
            Util.hideKeyboard(getActivity(), v);
            if (v == buttonPostReview) {
                submitForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitForm() {
        try {
            if (!Validator.validateName(getActivity(), editText_Review, input_layout_review, getString(R.string.entertheReview))) {
                return;
            }

            if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_COURSEPOSTREVIEW;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                String courseID = courseDetailsResponseModelObj.getcId();

                ApiService.getApiService().doGetCoursePostReview(getActivity(), ReviewFragment.this, courseID, editText_Review.getText().toString());
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void parseResponse(String response) {
        try {
            Log.i("parseResponse", response);
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_COURSEPOSTREVIEW)) {
                outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
                if (outputResponseModel.isSuccess()) {
                    Toast.makeText(getActivity(), outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    new ViewManager().gotoLoginView(getActivity());
                } else {
                    alertMessageManagement.alertDialogActivation(getActivity(), 1, "Alert!", outputResponseModel.getMessage(), "OK", "");
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


}
