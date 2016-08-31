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

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by Raos on 3/29/2016.
 */
public class TestPapersAdapter extends RecyclerView.Adapter<TestPapersAdapter.MyViewHolder> {

    List<myCourses> myCoursesLstArray;
    Activity activity;

    OnItemClickListener mItemClickListener;

    public TestPapersAdapter(Activity activty, List<myCourses> myCoursesLstAray) {
        this.activity = activty;
        this.myCoursesLstArray = myCoursesLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_samplevideo_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            if (!myCoursesLstArray.get(position).getcIcon().equals("") && myCoursesLstArray.get(position).getcIcon() != null) {
                Picasso.with(activity)
                        .load(myCoursesLstArray.get(position).getcIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.sampleImageView);
            } else {
                holder.sampleImageView.setImageResource(R.mipmap.loading);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.sampleTextView.setText(myCoursesLstArray.get(position).getcName());

    }

    @Override
    public int getItemCount() {
        return myCoursesLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView sampleTextView;
        ImageView sampleImageView;

        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            sampleTextView = (TextView) itemView.findViewById(R.id.sampleTextView);
            sampleImageView = (ImageView) itemView.findViewById(R.id.sampleImageView);
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
