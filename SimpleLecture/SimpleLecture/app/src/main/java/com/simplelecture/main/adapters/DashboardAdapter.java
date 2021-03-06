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
import com.simplelecture.main.model.viewmodel.MyCoursesResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

//import com.squareup.picasso.Picasso;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    List<MyCoursesResponseModel> myCoursesListArray;
    Activity activity;

    OnItemClickListener mItemClickListener;

    public DashboardAdapter(Activity activty, List<MyCoursesResponseModel> myCoursesLstArray) {
        this.activity = activty;
        this.myCoursesListArray = myCoursesLstArray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            if (!myCoursesListArray.get(position).getCourseIcon().equals("") && myCoursesListArray.get(position).getCourseIcon() != null) {
                Picasso.with(activity)
                        .load(myCoursesListArray.get(position).getCourseIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.courseimageView);
            } else {
                holder.courseimageView.setImageResource(R.mipmap.app_icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.textView.setText(myCoursesListArray.get(position).getCourseName());
        holder.text_no_chapters.setText("This Course will Expire in " + myCoursesListArray.get(position).getPendingDays() + "Days");
        holder.text_exer_completed.setText(myCoursesListArray.get(position).getCompletePer()+" % Complete");

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
        TextView text_no_chapters;
        TextView text_exer_completed;
        ImageView courseimageView;

        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_name);
            text_exer_completed = (TextView) itemView.findViewById(R.id.text_exer_completed);
            text_no_chapters = (TextView) itemView.findViewById(R.id.text_no_chapters);
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
