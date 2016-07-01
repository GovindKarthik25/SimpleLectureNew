package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.HomeCoursesModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class HomeCoursesAdapter extends RecyclerView.Adapter<HomeCoursesAdapter.MyViewHolder> {

    private final Activity activity;
    private List<HomeCoursesModel> coursesLstArray;

    OnItemClickListener mItemClickListener;

    public HomeCoursesAdapter(Activity activty, List<HomeCoursesModel> coursesLstAray) {
        this.activity = activty;
        this.coursesLstArray = coursesLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_courses_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            if (!coursesLstArray.get(position).getcIcon().equals("") && coursesLstArray.get(position).getcIcon() != null) {

                Picasso.with(activity)
                        .load(coursesLstArray.get(position).getcIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.courseimageView);
            } else {
                holder.courseimageView.setImageResource(R.mipmap.loading);
            }
            holder.subNameText.setText(coursesLstArray.get(position).getcName());
            holder.subNameText.setMovementMethod(new ScrollingMovementMethod());
            holder.textPrice.setText("Rs." +String.valueOf(coursesLstArray.get(position).getCdPrice()) +"/-");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return coursesLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView subNameText;
        TextView textPrice;
        ImageView courseimageView;
        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            subNameText = (TextView) itemView.findViewById(R.id.text_subject);
            textPrice = (TextView) itemView.findViewById(R.id.text_price);
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
