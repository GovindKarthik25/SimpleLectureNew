package com.simplelecture.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplelecture.main.R;

import java.util.ArrayList;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class ComboCoursesAdapter extends RecyclerView.Adapter<ComboCoursesAdapter.MyViewHolder> {

    ArrayList<String> myData;

    public ComboCoursesAdapter(ArrayList<String> data) {

        myData = data;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(myData.get(position));

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_subject);

        }
    }
}
