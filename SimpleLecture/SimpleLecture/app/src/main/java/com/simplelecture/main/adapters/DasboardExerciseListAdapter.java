package com.simplelecture.main.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.fragments.ExerciseFragment;
import com.simplelecture.main.fragments.interfaces.OnImageClickListener;
import com.simplelecture.main.model.viewmodel.ExerciseChapters;
import com.simplelecture.main.model.viewmodel.ExerciseResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Raos on 8/28/2016.
 */
public class DasboardExerciseListAdapter extends BaseExpandableListAdapter {

    private Activity mActivity;
    private List<ExerciseResponseModel> exerciseResponseModelLstArray;
    OnImageClickListener onImageClickListener;

    public DasboardExerciseListAdapter(Activity activity, List<ExerciseResponseModel> exerciseResponseModelArray, ExerciseFragment onImageClickListeners) {
        this.mActivity = activity;
        this.exerciseResponseModelLstArray = exerciseResponseModelArray;
        this.onImageClickListener = onImageClickListeners;
    }


    @Override
    public int getChildrenCount(int groupPosition) {

        int exer = 0;

        if(exerciseResponseModelLstArray.get(groupPosition).getExerciseChapters() != null){
            exer = this.exerciseResponseModelLstArray.get(groupPosition).getExerciseChapters().size();
        }
        return exer;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.exerciseResponseModelLstArray.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.exerciseResponseModelLstArray.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.exerciseResponseModelLstArray.get(groupPosition).getExerciseChapters().get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        try {
            final ExerciseResponseModel exerciseResponseModelObj = (ExerciseResponseModel) getGroup(groupPosition);
            final GroupHolder groupholder;
            if (convertView == null) {
                groupholder = new GroupHolder();
                LayoutInflater infalInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.adapter_dashboardexceriselist_group, null);
                groupholder.exceriseImageView = (ImageView) convertView.findViewById(R.id.exceriseImageView);
                groupholder.text_ChapterName = (TextView) convertView.findViewById(R.id.text_ChapterName);
                groupholder.text_DownloadCount = (TextView) convertView.findViewById(R.id.text_DownloadCount);
                groupholder.viewCourse_button = (Button) convertView.findViewById(R.id.viewCourse_button);
                groupholder.viewCourse_button.setVisibility(View.GONE);

                convertView.setTag(groupholder);
            } else {
                groupholder = (GroupHolder) convertView.getTag();
            }

            if (!exerciseResponseModelObj.getCourseIcon().equals("") && exerciseResponseModelObj.getCourseIcon() != null) {

                Picasso.with(mActivity)
                        .load(exerciseResponseModelObj.getCourseIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(groupholder.exceriseImageView);
            } else {
                groupholder.exceriseImageView.setImageResource(R.mipmap.app_icon);
            }

            groupholder.text_ChapterName.setText(exerciseResponseModelObj.getCourseName());
            groupholder.text_DownloadCount.setText(exerciseResponseModelObj.getDownloadedCount() + " Downloaded, " + exerciseResponseModelObj.getRemainingCount() + " Remaining");

            groupholder.viewCourse_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (onImageClickListener != null) {
                            onImageClickListener.onViewCourseclick(groupPosition, exerciseResponseModelObj.getCourseId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        try {
            final ExerciseChapters exerciseChaptersObj = (ExerciseChapters) getChild(groupPosition, childPosition);

            final ChildHolder childHolder;
            if (convertView == null) {
                childHolder = new ChildHolder();
                LayoutInflater infalInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.adapter_dashboardexceriselist_item, null);
                childHolder.downloadItem_TextView = (TextView) convertView.findViewById(R.id.downloadItem_TextView);
                childHolder.downloadItem_ImageView = (ImageView) convertView.findViewById(R.id.downloadItem_ImageView);


                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }

            childHolder.downloadItem_TextView.setText(exerciseChaptersObj.getCourseChapterNumber() + " : " + exerciseChaptersObj.getCourseChapterName());

            if (Boolean.valueOf(exerciseChaptersObj.getIsFileDownloaded())) {
                childHolder.downloadItem_ImageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.download));
            } else {
                childHolder.downloadItem_ImageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.notdownload));
            }

            childHolder.downloadItem_ImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onImageClickListener != null) {
                        onImageClickListener.ondownloadclick(childPosition, exerciseChaptersObj.getCourseChapterId());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupHolder {
        protected TextView text_ChapterName;
        protected TextView text_DownloadCount;
        protected ImageView exceriseImageView;
        protected Button viewCourse_button;
    }

    static class ChildHolder {
        protected TextView downloadItem_TextView;
        protected ImageView downloadItem_ImageView;

    }
}
