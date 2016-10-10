package com.simplelecture.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.ChaptersResponseModel;
import com.simplelecture.main.model.viewmodel.courseTopics;
import com.simplelecture.main.viewManager.ViewManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by M1032185 on 2/15/2016.
 */
public class CourseIndexExpandableListAdapter extends BaseExpandableListAdapter {

    private final List<ChaptersResponseModel> chaptersResponseModelList;
    private Context mContext;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<courseTopics>> listDataChild;
    boolean isPurchased;

    public CourseIndexExpandableListAdapter(Context context, List<ChaptersResponseModel> chaptersResponseModel, boolean purchased) {
        this.mContext = context;
        this.chaptersResponseModelList = chaptersResponseModel;
        this.isPurchased = purchased;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.chaptersResponseModelList.get(groupPosition).getCourseTopics().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.chaptersResponseModelList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.chaptersResponseModelList.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.chaptersResponseModelList.get(groupPosition).getCourseTopics().get(childPosititon);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        ChaptersResponseModel chaptersResponseModelObj = (ChaptersResponseModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_courseindexlist_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(chaptersResponseModelObj.getCcName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final courseTopics courseTopics = (courseTopics) getChild(groupPosition, childPosition);

        final String childText = courseTopics.getCtName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_courseindexlist_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(courseTopics.getCtNumber() + ": " + courseTopics.getCtName());
        ImageView play_ImageView = (ImageView) convertView.findViewById(R.id.play_ImageView);

        if (isPurchased) {
            play_ImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.play));
        } else {
            play_ImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.lock));

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPurchased) {
                    Log.i("ctId", "ctId--->" + courseTopics.getCtId());
                    new ViewManager().gotoVideoPlayerView(mContext, "CourseIndexFragment", courseTopics.getCtId(), "");
                } else {
                    Toast.makeText(mContext, "Video is Locked. To unlock Purchase the Product.", Toast.LENGTH_SHORT).show();
                }
              /*  Intent intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra("ctId1", courseTopics.getCtId());
                intent.putExtra("DisplayView", "CourseIndexFragment");
                intent.putExtra("videoURL", "");
                mContext.startActivity(intent);*/
            }
        });

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
}
