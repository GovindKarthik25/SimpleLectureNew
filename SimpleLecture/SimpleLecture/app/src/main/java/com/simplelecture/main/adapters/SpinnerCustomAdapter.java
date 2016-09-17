package com.simplelecture.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.ForumCourseModel;

import java.util.ArrayList;

/**
 * Created by Raos on 9/5/2016.
 */
public class SpinnerCustomAdapter extends BaseAdapter {
    private final LayoutInflater inflter;
    private final Context contexts;
    private final ArrayList<ForumCourseModel> forumCourseModelList;

    /**
     * Constructor
     *
     * @param context             The current context.
     * @param forumCourseModelLst
     */
    public SpinnerCustomAdapter(Context context, ArrayList<ForumCourseModel> forumCourseModelLst) {
        this.contexts = context;
        this.forumCourseModelList = forumCourseModelLst;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return forumCourseModelList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return forumCourseModelList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinner_row_view, null);
        TextView textViewRowFacility = (TextView) convertView.findViewById(R.id.textViewRowFacility);
        textViewRowFacility.setText(forumCourseModelList.get(position).getName());
        return convertView;
    }
}

