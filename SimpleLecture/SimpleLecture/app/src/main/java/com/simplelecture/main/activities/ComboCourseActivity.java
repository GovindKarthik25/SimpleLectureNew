package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ViewPagerAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.fragments.ComboCoursesFragment;
import com.simplelecture.main.fragments.CourseBenifitsFragment;
import com.simplelecture.main.fragments.CourseDescriptionFragment;
import com.simplelecture.main.fragments.CourseFeatureFragment;
import com.simplelecture.main.fragments.FAQFragment;
import com.simplelecture.main.fragments.ReviewFragment;
import com.simplelecture.main.fragments.interfaces.OnFragmentInteractionListener;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.CartModel;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseMonths;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ComboCourseActivity extends AppCompatActivity implements OnFragmentInteractionListener, NetworkLayer {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;
    private CourseDetailsResponseModel courseDetailsResponseModelObj;

    Intent intent;

    Spinner spinnerMonths;
    TextView textViewCourseAmount;
    CustomSpinnerAdapter customSpinnerAdapter;
    ArrayList<CharSequence> courseMaterials = new ArrayList<>();
    CheckBox chekInclude;
    TextView textViewLabelMaterial;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;

    private Button btnBuy;

    private AlertMessageManagement alertMessageManagement;

    private int selectedMonthId;
    private OutputResponseModel outputResponseModel;
    private CartModel cartModel;
    private SnackBarManagement snack;

    HashMap<String, String> courseHashMap;
    private LinearLayout bottom_BarLayout;

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(ComboCourseActivity.this);
        setContentView(R.layout.activity_combo_course);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        intent = getIntent();
        if (intent.hasExtra("courseDetails")) {
            courseDetailsResponseModelObj = (CourseDetailsResponseModel) intent.getSerializableExtra("courseDetails");
        }

        courseHashMap = new HashMap<>();
        courseHashMap = Util.prepareMap(courseDetailsResponseModelObj.getCourseMaterials());

        courseMaterials = Util.convertToStringArray(courseDetailsResponseModelObj.getCourseMaterials());

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        getSupportActionBar().setTitle(Util.setActionBarText(courseDetailsResponseModelObj.getcName()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Changing the action bar color
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        btnBuy = (Button) findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(onClickListener);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        spinnerMonths = (Spinner) findViewById(R.id.spinner_months);
        customSpinnerAdapter = new CustomSpinnerAdapter(this, courseDetailsResponseModelObj.getCourseMonths());
        spinnerMonths.setAdapter(customSpinnerAdapter);
        spinnerMonths.setOnItemSelectedListener(onItemSelectedListener);
        textViewCourseAmount = (TextView) findViewById(R.id.textViewCourseAmount);
        textViewCourseAmount.setText(courseDetailsResponseModelObj.getCoursePrice() + " X ");
        chekInclude = (CheckBox) findViewById(R.id.checkBox);
        chekInclude.setOnCheckedChangeListener(onCheckedChangeListener);
        textViewLabelMaterial = (TextView) findViewById(R.id.textView_labelMaterial);
        bottom_BarLayout =(LinearLayout) findViewById(R.id.bottom_BarLayout);

        cartModel = new CartModel();

        if(courseDetailsResponseModelObj.getPage() == 1){
            bottom_BarLayout.setVisibility(View.GONE);
        } else {
            bottom_BarLayout.setVisibility(View.VISIBLE);
        }

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return false;
            }
        });
    }


    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            List<CourseMonths> courseMonths = courseDetailsResponseModelObj.getCourseMonths();
            selectedMonthId = position + 1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (isChecked) {
                showMaterialsDialog();
                courseMaterialBuilder = new StringBuilder();
            } else {
                textViewLabelMaterial.setText("");
                courseMaterialBuilder = null;
                textViewLabelMaterial.setVisibility(View.GONE);
            }
        }
    };

    private String courseMaterialSelected;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                if (new ConnectionDetector(ComboCourseActivity.this).isConnectingToInternet()) {
                    param_get_ServiceCallResult = Constants.GET_CART_ADD;
                    pd = new Util().waitingMessage(ComboCourseActivity.this, "", getResources().getString(R.string.loading));

                    cartModel.setCourseID(courseDetailsResponseModelObj.getcId());
                    cartModel.setMonths(String.valueOf(selectedMonthId));
                    if (courseMaterialBuilder != null && courseMaterialBuilder.length() > 0) {
                        courseMaterialSelected = courseMaterialBuilder.toString();
                    } else {
                        courseMaterialSelected = "";
                    }
                    cartModel.setCourseMaterials(courseMaterialSelected); // jsonArray
                    Log.d("cartModel-->", "" + cartModel);

                    ApiService.getApiService().doAddToCart(ComboCourseActivity.this, cartModel);

                } else {
                    alertMessageManagement.alertDialogActivation(ComboCourseActivity.this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ComboCoursesFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.comboCourse));
        adapter.addFrag(new CourseFeatureFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseFeature));
        adapter.addFrag(new CourseDescriptionFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseDescription));
        adapter.addFrag(new CourseBenifitsFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.courseBenifits));
        adapter.addFrag(new FAQFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.fAQ));
        adapter.addFrag(new ReviewFragment().newInstance(courseDetailsResponseModelObj), getResources().getString(R.string.review));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    JSONArray jsonArray;
    StringBuilder courseMaterialBuilder;

    private void showMaterialsDialog() {
        final CharSequence[] dialogList = courseMaterials.toArray(new CharSequence[courseMaterials.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Item");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];
        builder.setMultiChoiceItems(dialogList, is_checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                // Do something when click positive button

                ListView list = ((AlertDialog) dialog).getListView();
                // make selected item in the comma seprated string
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    jsonArray = new JSONArray();

                    for (int i = 0; i < list.getCount(); i++) {
                        boolean checked = list.isItemChecked(i);

                        if (checked) {
                            if (stringBuilder.length() > 0) stringBuilder.append(",");
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("Id", Util.getMaterialData(courseHashMap,String.valueOf(list.getItemAtPosition(i))));
//                            jsonArray.put(jsonObject);
                            if (courseMaterialBuilder.length() > 0)
                                courseMaterialBuilder.append(",");
                            courseMaterialBuilder.append(Util.getMaterialData(courseHashMap, String.valueOf(list.getItemAtPosition(i).toString().split("-")[0].trim())));


                            stringBuilder.append(String.valueOf(list.getItemAtPosition(i).toString().split("-")[0].trim()));
                            textViewLabelMaterial.setVisibility(View.VISIBLE);
                            textViewLabelMaterial.setText(stringBuilder.toString());

                        }
                    }

                    Log.d("JSON", "" + jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    @Override
    public void parseResponse(String response) {

        try {

            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_ADD)) {

                outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
                if (outputResponseModel.isSuccess()) {
                    Toast.makeText(ComboCourseActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();

                    new ViewManager().gotoCartActivity(ComboCourseActivity.this);

                } else {
                    alertMessageManagement.alertDialogActivation(ComboCourseActivity.this, 1, "Alert!", outputResponseModel.getMessage(), "OK", "");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showError(String error) {
        Log.d("Failure Resposne", error);

        if (pd.isShowing()) {
            pd.cancel();
        }
    }

    private class CustomSpinnerAdapter extends BaseAdapter {

        private List<CourseMonths> data;
        private Context mContext;
        private LayoutInflater layoutInflater;

        public CustomSpinnerAdapter(Context context, List<CourseMonths> courseMonths) {
            data = courseMonths;
            mContext = context;
            layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = layoutInflater.inflate(R.layout.spinner_layout, parent, false);
            TextView listItem = (TextView) row.findViewById(R.id.textview);
            listItem.setText(data.get(position).getName());
            return row;
        }
    }
}
