package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.DashboardMyCoursesResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyViewHolder> {

    List<DashboardMyCoursesResponseModel> dashboardMyCoursesResponseModelListArray;
    Activity activity;

    OnItemClickListener mItemClickListener;

    public MyCoursesAdapter(Activity activty, List<DashboardMyCoursesResponseModel> dashboardMyCoursesResponseModelLstArray) {
        this.activity = activty;
        this.dashboardMyCoursesResponseModelListArray = dashboardMyCoursesResponseModelLstArray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycourse_row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            if (!dashboardMyCoursesResponseModelListArray.get(position).getCourseIcon().equals("") && dashboardMyCoursesResponseModelListArray.get(position).getCourseIcon() != null) {
                Picasso.with(activity)
                        .load(dashboardMyCoursesResponseModelListArray.get(position).getCourseIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.courseimageView);
            } else {
                holder.courseimageView.setImageResource(R.mipmap.app_icon);
            }


            holder.text_name.setText(dashboardMyCoursesResponseModelListArray.get(position).getCourseName());

            SpannableStringBuilder span1 = new SpannableStringBuilder("No. of chapters : ");
            ForegroundColorSpan color1 = new ForegroundColorSpan(activity.getResources().getColor(R.color.listTextColor));
            span1.setSpan(color1, 0, span1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            SpannableStringBuilder span2 = new SpannableStringBuilder(dashboardMyCoursesResponseModelListArray.get(position).getTotalChapters());
            ForegroundColorSpan color2 = new ForegroundColorSpan(activity.getResources().getColor(R.color.cardview_dark_background));
            span2.setSpan(color2, 0, span2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            Spanned concatenated = (Spanned) TextUtils.concat(span1, span2);

            SpannableStringBuilder resultNoOfChapters = new SpannableStringBuilder(concatenated);

            holder.textNoOfChapters.setText(resultNoOfChapters, TextView.BufferType.SPANNABLE);

            SpannableStringBuilder span12 = new SpannableStringBuilder("Exercise Covered : ");
            ForegroundColorSpan color12 = new ForegroundColorSpan(activity.getResources().getColor(R.color.listTextColor));
            span12.setSpan(color12, 0, span12.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            SpannableStringBuilder span22 = new SpannableStringBuilder(dashboardMyCoursesResponseModelListArray.get(position).getExerciseDownloaded() + "/" + dashboardMyCoursesResponseModelListArray.get(position).getTotalExercises());
            ForegroundColorSpan color22 = new ForegroundColorSpan(activity.getResources().getColor(R.color.cardview_dark_background));
            span22.setSpan(color22, 0, span22.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            Spanned concatenated2 = (Spanned) TextUtils.concat(span12, span22);

            SpannableStringBuilder resultExerciseCovered = new SpannableStringBuilder(concatenated2);

            holder.textExeCovered.setText(resultExerciseCovered, TextView.BufferType.SPANNABLE);

            holder.myRating.setRating(Integer.valueOf(dashboardMyCoursesResponseModelListArray.get(position).getCourseRating()));
            holder.textView_coursedetails.setText(Html.fromHtml(dashboardMyCoursesResponseModelListArray.get(position).getCourseDesc()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dashboardMyCoursesResponseModelListArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text_name;
        TextView textNoOfChapters;
        TextView textExeCovered;
        ImageView courseimageView;
        RatingBar myRating;
        TextView textView_coursedetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            courseimageView = (ImageView) itemView.findViewById(R.id.courseimageView);
            textNoOfChapters = (TextView) itemView.findViewById(R.id.text_no_chapters);
            textExeCovered = (TextView) itemView.findViewById(R.id.text_exer_covered);
            myRating = (RatingBar) itemView.findViewById(R.id.myRating);
            textView_coursedetails = (TextView) itemView.findViewById(R.id.textView_coursedetails);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
