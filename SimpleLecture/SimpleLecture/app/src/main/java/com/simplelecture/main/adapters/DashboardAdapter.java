package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.myCourses;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    List<myCourses> myCoursesListArray;
    Activity activity;

    OnItemClickListener mItemClickListener;

    public DashboardAdapter(Activity activty, List<myCourses> myCoursesLstArray) {
        this.activity = activty;
        this.myCoursesListArray = myCoursesLstArray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Picasso.with(activity)
                .load(myCoursesListArray.get(position).getcIcon())
                .placeholder(R.mipmap.ic_launcher)   // optional
                .error(R.mipmap.ic_launcher)      // optional
                //.resize(250, 200)                        // optional
                //.rotate(90)                             // optional
                .into(holder.courseimageView);
        holder.textView.setText(myCoursesListArray.get(position).getcName());

    }

    @Override
    public int getItemCount() {
        return myCoursesListArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView courseimageView;

        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_subject);
            courseimageView = (ImageView) itemView.findViewById(R.id.courseimageView);
            itemLayout = (LinearLayout) itemView.findViewById(item_layout);
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
