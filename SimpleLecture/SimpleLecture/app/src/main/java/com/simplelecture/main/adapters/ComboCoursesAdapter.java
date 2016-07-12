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
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class ComboCoursesAdapter extends RecyclerView.Adapter<ComboCoursesAdapter.MyViewHolder> {

    private final Activity activity;
    List<CourseCombos> courseCombosList;

    OnItemClickListener mItemClickListener;

    public ComboCoursesAdapter(Activity activty, List<CourseCombos> courseCombos) {
        this.activity = activty;
        this.courseCombosList = courseCombos;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycle_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            if (!courseCombosList.get(position).getcIcon().equals("") && courseCombosList.get(position).getcIcon() != null) {

                Picasso.with(activity)
                        .load(courseCombosList.get(position).getcIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                                //.resize(250, 200)                        // optional
                                //.rotate(90)                             // optional
                        .into(holder.courseimageView);
            } else {
                holder.courseimageView.setImageResource(R.mipmap.app_icon);
            }
            holder.textView_subject.setText(courseCombosList.get(position).getcName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return courseCombosList.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_subject;
        ImageView courseimageView;
        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_subject = (TextView) itemView.findViewById(R.id.textView_subject);
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
