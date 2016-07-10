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
import com.simplelecture.main.model.viewmodel.CourseCategoriesModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by Raos on 3/29/2016.
 */
public class CourseCategoriesAdapter extends RecyclerView.Adapter<CourseCategoriesAdapter.MyViewHolder> {

    List<CourseCategoriesModel> courseCategoriesModelList;
    Activity activity;

    OnItemClickListener mItemClickListener;

    public CourseCategoriesAdapter(Activity activty, List<CourseCategoriesModel> courseCategoriesModelLst) {
        this.activity = activty;
        this.courseCategoriesModelList = courseCategoriesModelLst;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_coursecategory, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            if (!courseCategoriesModelList.get(position).getcIcon().equals("") && courseCategoriesModelList.get(position).getcIcon() != null) {
                Picasso.with(activity)
                        .load(courseCategoriesModelList.get(position).getcIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.corCatimageView);
            } else {
                holder.corCatimageView.setImageResource(R.mipmap.loading);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.text_courseName.setText(courseCategoriesModelList.get(position).getcName());
        holder.text_CatgoryDesc.setText("(" +courseCategoriesModelList.get(position).getCatName()+")");
        holder.text_priceCat.setText("RS."+courseCategoriesModelList.get(position).getCdPrice()+"/-" + "\n (per month)");
    }

    @Override
    public int getItemCount() {
        return courseCategoriesModelList.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text_courseName, text_CatgoryDesc, text_priceCat;
        ImageView corCatimageView;

        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_courseName = (TextView) itemView.findViewById(R.id.text_courseName);
            text_CatgoryDesc = (TextView) itemView.findViewById(R.id.text_CatgoryDesc);
            text_priceCat = (TextView) itemView.findViewById(R.id.text_priceCat);

            corCatimageView = (ImageView) itemView.findViewById(R.id.corCatimageView);
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
