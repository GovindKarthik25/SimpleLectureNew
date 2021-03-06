package com.simplelecture.main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.viewManager.ViewManager;
import com.squareup.picasso.Picasso;

/**
 * Created by Raos on 2/14/2016.
 */
public class CourseDescriptionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SnackBarManagement snack;
    private CourseDetailsResponseModel courseDetailsResponseModelObj;
    private ImageView imageViewVideoUrl;
    private TextView textView_Desc;

    public CourseDescriptionFragment() {
        // Required empty public constructor
    }

    public static CourseDescriptionFragment newInstance(CourseDetailsResponseModel courseDetailsResponseModelObj) {
        CourseDescriptionFragment fragment = new CourseDescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, courseDetailsResponseModelObj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        snack = new SnackBarManagement(getActivity());

        if (getArguments() != null) {
            courseDetailsResponseModelObj = (CourseDetailsResponseModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_course_description, container, false);
        imageViewVideoUrl = (ImageView) convertView.findViewById(R.id.imageViewVideoUrl);
        textView_Desc = (TextView) convertView.findViewById(R.id.textView_Desc);

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        try {
            if (!courseDetailsResponseModelObj.getVideoImage().equals("") && courseDetailsResponseModelObj.getVideoImage() != null) {

                Picasso.with(getActivity())
                        .load(courseDetailsResponseModelObj.getVideoImage())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(imageViewVideoUrl);
            } else {
                imageViewVideoUrl.setImageResource(R.mipmap.app_icon);
            }

            textView_Desc.setMovementMethod(new ScrollingMovementMethod());
            textView_Desc.setText(Html.fromHtml(courseDetailsResponseModelObj.getcDesc()));


            imageViewVideoUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new ViewManager().gotoVideoPlayerView(getContext(), "CourseDescriptionFragment", Integer.valueOf(courseDetailsResponseModelObj.getcId()), "");

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
