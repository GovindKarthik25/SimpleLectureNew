package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.ForumTopics;

import java.util.List;

/**
 * Created by Raos on 8/25/2016.
 */
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.MyViewHolder> {

    private final Activity activity;
    List<ForumTopics> forumTopicsLstArray;

    OnItemClickListener mItemClickListener;

    public ForumAdapter(Activity activty, List<ForumTopics> forumTopicsLstAray) {
        this.activity = activty;
        this.forumTopicsLstArray = forumTopicsLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_forum, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            holder.text_Forumtopic.setText(forumTopicsLstArray.get(position).getName());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return forumTopicsLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text_Forumtopic;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_Forumtopic = (TextView) itemView.findViewById(R.id.text_Forumtopic);

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
