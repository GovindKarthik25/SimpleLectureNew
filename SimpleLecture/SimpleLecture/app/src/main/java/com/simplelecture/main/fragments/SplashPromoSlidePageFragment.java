/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplelecture.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.SplashResponseModel;
import com.simplelecture.main.splash.SplashActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 * <p/>
 * <p>This class is used by the and {@link
 * SplashActivity} samples.</p>
 */
public class SplashPromoSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private List<SplashResponseModel> splashResponseModelLstArray;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SplashPromoSlidePageFragment create(int pageNumber, List<SplashResponseModel> splashResponseModelLstAray) {
        SplashPromoSlidePageFragment fragment = new SplashPromoSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putSerializable("splashResponseModelLstArray", (Serializable) splashResponseModelLstAray);
        fragment.setArguments(args);
        return fragment;
    }

    public SplashPromoSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        splashResponseModelLstArray = (ArrayList<SplashResponseModel>) getArguments().getSerializable("splashResponseModelLstArray");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);

        if (!splashResponseModelLstArray.get(mPageNumber).getImage().equals("") && splashResponseModelLstArray.get(mPageNumber).getImage() != null) {

            Picasso.with(getActivity())
                    .load(splashResponseModelLstArray.get(mPageNumber).getImage())
                    .placeholder(R.mipmap.loading)   // optional
                    .error(R.mipmap.app_icon)      // optional
                    //.resize(250, 200)                        // optional
                    //.rotate(90)                             // optional
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.app_icon);
        }

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return splashResponseModelLstArray.size();
    }
}
