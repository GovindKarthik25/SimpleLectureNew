package com.simplelecture.main.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.Answer;
import com.simplelecture.main.model.viewmodel.CourseFaqs;

import java.util.List;

/**
 * Created by M1032185 on 2/15/2016.
 */
public class FAQExpandableListAdapter extends BaseExpandableListAdapter {

   // private final HashMap<String, List<String>> listDataChild;
    private final List<CourseFaqs> courseFaqsResponselist;
    private Context mContext;
   // private List<String> listDataHeader; // header titles
    // child data in format of header title, child title

    public FAQExpandableListAdapter(Context context, List<CourseFaqs> courseFaqsResponselst) {
        this.mContext = context;
        // this.listDataHeader = listDataHeader;
        this.courseFaqsResponselist = courseFaqsResponselst;

    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.courseFaqsResponselist.get(groupPosition).getAnswer().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.courseFaqsResponselist.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.courseFaqsResponselist.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.courseFaqsResponselist.get(groupPosition).getAnswer().get(childPosititon);
                //listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
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
        CourseFaqs CourseFaqsObj = (CourseFaqs) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_faqlist_group, null);
        }

        TextView lblListHeaderFaq = (TextView) convertView.findViewById(R.id.lblListHeaderFaq);
        lblListHeaderFaq.setText(CourseFaqsObj.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        //String childTitle = (String) getChild(groupPosition, childPosition);
        Answer answerObj = (Answer) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_faqlist_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItemFaq);

        txtListChild.setText(Html.fromHtml(answerObj.getName()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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