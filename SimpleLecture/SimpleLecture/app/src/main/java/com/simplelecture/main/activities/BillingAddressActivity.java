package com.simplelecture.main.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simplelecture.main.R;
import com.simplelecture.main.http.NetworkLayer;

/**
 * Created by Raos on 8/6/2016.
 */
public class BillingAddressActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private Button btn_Save;
    private TextInputLayout input_layout_State, input_layout_email, input_layout_FullName, input_layout_mobile, input_layout_ShippingAddress, input_layout_Pincode;
    private EditText input_FullName, input_ShippingAddress, input_State, input_Pincode, input_mobile, input_email;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billingaddress);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle("Profile");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        input_layout_FullName = (TextInputLayout) findViewById(R.id.input_layout_FullName);
        input_layout_ShippingAddress = (TextInputLayout) findViewById(R.id.input_layout_ShippingAddress);
        input_layout_State = (TextInputLayout) findViewById(R.id.input_layout_State);
        input_layout_Pincode = (TextInputLayout) findViewById(R.id.input_layout_Pincode);
        input_layout_mobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);

        input_FullName = (EditText) findViewById(R.id.input_FullName);
        input_ShippingAddress = (EditText) findViewById(R.id.input_ShippingAddress);
        input_State = (EditText) findViewById(R.id.input_State);
        input_Pincode = (EditText) findViewById(R.id.input_Pincode);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_email = (EditText) findViewById(R.id.input_email);

        btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }


    @Override
    public void parseResponse(String response) {

    }

    @Override
    public void showError(String error) {

    }
}
