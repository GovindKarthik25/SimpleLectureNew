package com.simplelecture.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.CourseMonths;

import java.util.List;

/**
 * Created by M1032185 on 7/24/2016.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    private List<CourseMonths> data;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public CustomSpinnerAdapter(Context context, List<CourseMonths> courseMonths) {
        data = courseMonths;
        mContext = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
