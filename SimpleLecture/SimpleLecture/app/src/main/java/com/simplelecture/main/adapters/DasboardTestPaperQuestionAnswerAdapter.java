package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.Questions;

import java.util.List;

/**
 * Created by Raos on 8/30/2016.
 */
public class DasboardTestPaperQuestionAnswerAdapter extends RecyclerView.Adapter<DasboardTestPaperQuestionAnswerAdapter.MyViewHolder> {

    private final Activity activity;
    List<Questions> questionsLstArray;

    OnItemClickListener mItemClickListener;

    public DasboardTestPaperQuestionAnswerAdapter(Activity activty, List<Questions> questionsLstAray) {
        this.activity = activty;
        this.questionsLstArray = questionsLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lessonviewrowgroupview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            holder.textViewQuestion.setText(questionsLstArray.get(position).getNumber() + ". " + questionsLstArray.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return questionsLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewQuestion;
        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewQuestion = (TextView) itemView.findViewById(R.id.textViewQuestion);

            //itemLayout = (LinearLayout) itemView.findViewById(item_layout);
           // itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
