package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.ForumGetModel;

import java.util.List;


/**
 * Created by Raos on 09/04/2016.
 */
public class ForumListAdapter extends RecyclerView.Adapter<ForumListAdapter.MyViewHolder> {

    private final List<ForumGetModel> forumGetModelLst;
    Activity activity;

    OnItemClickListener mItemClickListener;

    public ForumListAdapter(Activity activty, List<ForumGetModel> forumGetModelList) {
        this.activity = activty;
        this.forumGetModelLst = forumGetModelList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_forum_row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            holder.textView_SubName.setText(forumGetModelLst.get(position).getName());
            holder.textView_SubDesp.setText(forumGetModelLst.get(position).getDetails());
            holder.textView_Answered.setText(forumGetModelLst.get(position).getReplyCount() + " Answered");
            holder.textView_AdminPost.setText(forumGetModelLst.get(position).getPostedBy() + " on " + forumGetModelLst.get(position).getPostedDate());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return forumGetModelLst.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_SubName;
        TextView textView_SubDesp;
        TextView textView_Answered;
        TextView textView_AdminPost;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_SubName = (TextView) itemView.findViewById(R.id.textView_SubName);
            textView_SubDesp = (TextView) itemView.findViewById(R.id.textView_SubDesp);
            textView_Answered = (TextView) itemView.findViewById(R.id.textView_Answered);
            textView_AdminPost = (TextView) itemView.findViewById(R.id.textView_AdminPost);

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
