package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.RelatedCourses;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Raos on 8/25/2016.
 */
public class RelatedCourseAdapter extends RecyclerView.Adapter<RelatedCourseAdapter.MyViewHolder> {

    private final Activity activity;
    List<RelatedCourses> relatedCoursesLstArray;

    OnItemClickListener mItemClickListener;

    public RelatedCourseAdapter(Activity activty, List<RelatedCourses> relatedCoursesLstAray) {
        this.activity = activty;
        this.relatedCoursesLstArray = relatedCoursesLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_relatedcourse, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            if (!relatedCoursesLstArray.get(position).getcIcon().equals("") && relatedCoursesLstArray.get(position).getcIcon() != null) {

                Picasso.with(activity)
                        .load(relatedCoursesLstArray.get(position).getcIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.imageView_Relatedcourse);
            } else {
                holder.imageView_Relatedcourse.setImageResource(R.mipmap.app_icon);
            }

            holder.textView_Relatedcourse.setText(relatedCoursesLstArray.get(position).getcName());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return relatedCoursesLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView_Relatedcourse;
        TextView textView_Relatedcourse;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_Relatedcourse = (TextView) itemView.findViewById(R.id.textView_Relatedcourse);
            imageView_Relatedcourse = (ImageView) itemView.findViewById(R.id.imageView_Relatedcourse);

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
