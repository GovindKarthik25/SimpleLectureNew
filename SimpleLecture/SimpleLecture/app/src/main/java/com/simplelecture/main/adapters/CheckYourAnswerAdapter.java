package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.CheckYourAnswerResponseModel;

import java.util.List;

/**
 * Created by Raos on 8/30/2016.
 */
public class CheckYourAnswerAdapter extends RecyclerView.Adapter<CheckYourAnswerAdapter.MyViewHolder> {

    private final Activity activity;
    List<CheckYourAnswerResponseModel> checkYourAnswerResponseModelLst;
    private RadioButton lastCheckedRB = null;


    OnItemClickListener mItemClickListener;

    public CheckYourAnswerAdapter(Activity activty, List<CheckYourAnswerResponseModel> checkYourAnswerResponseModelList) {
        this.activity = activty;
        this.checkYourAnswerResponseModelLst = checkYourAnswerResponseModelList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lessonviewrowgroupview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {

            holder.textViewQuestion.setText((position + 1) + ". " + checkYourAnswerResponseModelLst.get(position).getQuestionName());
            holder.radioAns1.setText(Html.fromHtml(checkYourAnswerResponseModelLst.get(position).getAnswer1()));

            holder.radioAns2.setText(Html.fromHtml(checkYourAnswerResponseModelLst.get(position).getAnswer2()));

            if(!checkYourAnswerResponseModelLst.get(position).getAnswer3().equals("")){
                holder.radioAns4.setVisibility(View.VISIBLE);
                holder.radioAns3.setText(Html.fromHtml(checkYourAnswerResponseModelLst.get(position).getAnswer3()));
            } else {
                holder.radioAns3.setVisibility(View.GONE);
            }
            if(!checkYourAnswerResponseModelLst.get(position).getAnswer3().equals("")){
                holder.radioAns4.setVisibility(View.VISIBLE);
                holder.radioAns4.setText(Html.fromHtml(checkYourAnswerResponseModelLst.get(position).getAnswer4()));

            } else {
                holder.radioAns4.setVisibility(View.GONE);
            }



            holder.radioAns1.setChecked(false);
            holder.radioAns1.setClickable(false);
            holder.radioAns2.setChecked(false);
            holder.radioAns2.setClickable(false);
            holder.radioAns3.setChecked(false);
            holder.radioAns3.setClickable(false);
            holder.radioAns4.setChecked(false);
            holder.radioAns4.setClickable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return checkYourAnswerResponseModelLst.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewQuestion;
        RadioGroup answerRadioGroup;
        RadioButton radioAns1;
        RadioButton radioAns2;
        RadioButton radioAns3;
        RadioButton radioAns4;

        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewQuestion = (TextView) itemView.findViewById(R.id.textViewQuestion);
            answerRadioGroup = (RadioGroup) itemView.findViewById(R.id.answerRadioGroup);
            radioAns1 = (RadioButton) itemView.findViewById(R.id.radio_answer1);

            radioAns2 = (RadioButton) itemView.findViewById(R.id.radio_answer2);
            radioAns3 = (RadioButton) itemView.findViewById(R.id.radio_answer3);
            radioAns4 = (RadioButton) itemView.findViewById(R.id.radio_answer4);

        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
