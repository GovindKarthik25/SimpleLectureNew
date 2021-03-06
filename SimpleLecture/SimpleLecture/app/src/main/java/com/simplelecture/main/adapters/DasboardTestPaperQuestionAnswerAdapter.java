package com.simplelecture.main.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioButton lastCheckedRB = null;


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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {

            holder.textViewQuestion.setText(questionsLstArray.get(position).getNumber() + ". " + questionsLstArray.get(position).getName());
            holder.radioAns1.setText(questionsLstArray.get(position).getAnswer1());
            holder.radioAns2.setText(questionsLstArray.get(position).getAnswer2());
            //holder.radioAns3.setText(questionsLstArray.get(position).getAnswer3());
           // holder.radioAns4.setText(questionsLstArray.get(position).getAnswer4());

            if (!questionsLstArray.get(position).getAnswer3().equals("")) {
                holder.radioAns4.setVisibility(View.VISIBLE);
                holder.radioAns3.setText(questionsLstArray.get(position).getAnswer3());
            } else {
                holder.radioAns3.setVisibility(View.GONE);
            }
            if (!questionsLstArray.get(position).getAnswer3().equals("")) {
                holder.radioAns4.setVisibility(View.VISIBLE);
                holder.radioAns4.setText(questionsLstArray.get(position).getAnswer4());

            } else {
                holder.radioAns4.setVisibility(View.GONE);
            }

            holder.answerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup rgp, int checkedId) {
                    questionsLstArray.get(position).setSelectedCheckedId(rgp.getCheckedRadioButtonId());
                    questionsLstArray.get(position).setSelectedAnswer("");

                    switch (checkedId) {
                        case R.id.radio_answer1:
                            questionsLstArray.get(position).setSelectedAnswer("A");

                            break;

                        case R.id.radio_answer2:
                            questionsLstArray.get(position).setSelectedAnswer("B");

                            break;

                        case R.id.radio_answer3:
                            questionsLstArray.get(position).setSelectedAnswer("C");

                            break;
                        case R.id.radio_answer4:
                            questionsLstArray.get(position).setSelectedAnswer("D");

                            break;
                    }


                }
            });

            holder.answerRadioGroup.check(questionsLstArray.get(position).getSelectedCheckedId());
            holder.answerRadioGroup.getTag();


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
