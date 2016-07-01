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
import com.simplelecture.main.model.viewmodel.HomeTestimonialsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsAdapter.MyViewHolder> {

    private final Activity activity;
    List<HomeTestimonialsModel> homeTestimonialsModelLstArray;

    OnItemClickListener mItemClickListener;

    public TestimonialsAdapter(Activity activty, List<HomeTestimonialsModel> homeTestimonialsModelLstAray) {
        this.activity = activty;
        this.homeTestimonialsModelLstArray = homeTestimonialsModelLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testimonial_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            if (!homeTestimonialsModelLstArray.get(position).gettImage().equals("") && homeTestimonialsModelLstArray.get(position).gettImage() != null) {

                Picasso.with(activity)
                        .load(homeTestimonialsModelLstArray.get(position).gettImage())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                        .into(holder.imageViewPhotos);
            } else {
                holder.imageViewPhotos.setImageResource(R.mipmap.loading);
            }

            holder.text_Name.setText(homeTestimonialsModelLstArray.get(position).gettName());
            holder.text_Content.setText(homeTestimonialsModelLstArray.get(position).gettDesc());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return homeTestimonialsModelLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text_Name;
        TextView text_Content;
        ImageView imageViewPhotos;
        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_Name = (TextView) itemView.findViewById(R.id.text_Name);
            text_Content = (TextView) itemView.findViewById(R.id.text_Content);
            imageViewPhotos = (ImageView) itemView.findViewById(R.id.imageViewPhotos);
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
