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
import com.simplelecture.main.model.viewmodel.CourseCombos;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.simplelecture.main.R.id.item_layout;

/**
 * Created by M1032185 on 1/31/2016.
 */
public class HomeComboCoursesAdapter extends RecyclerView.Adapter<HomeComboCoursesAdapter.MyViewHolder> {

    private final Activity activity;
    List<CourseCombos> courseCombosLstArray;

    OnItemClickListener mItemClickListener;

    public HomeComboCoursesAdapter(Activity activty, List<CourseCombos> courseCombosLstAray) {
        this.activity = activty;
        this.courseCombosLstArray = courseCombosLstAray;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_combocourses_item_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            if (!courseCombosLstArray.get(position).getcIcon().equals("") && courseCombosLstArray.get(position).getcIcon() != null) {

                Picasso.with(activity)
                        .load(courseCombosLstArray.get(position).getcIcon())
                        .placeholder(R.mipmap.loading)   // optional
                        .error(R.mipmap.app_icon)      // optional
                //.resize(250, 200)                        // optional
                //.rotate(90)                             // optional
                .into(holder.courseimageView);
            } else {
                holder.courseimageView.setImageResource(R.mipmap.loading);
            }
            holder.subNameText.setText(courseCombosLstArray.get(position).getcName());
            holder.subNameText.setMovementMethod(new ScrollingMovementMethod());
            holder.text_priceCombo.setText("Rs." + String.valueOf(courseCombosLstArray.get(position).getCdPrice()) +"/-");
            holder.text_desc.setText(courseCombosLstArray.get(position).getCourses());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return courseCombosLstArray.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView subNameText;
        TextView text_priceCombo;
        TextView text_desc;
        ImageView courseimageView;
        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            subNameText = (TextView) itemView.findViewById(R.id.text_subject);
            text_priceCombo = (TextView) itemView.findViewById(R.id.text_priceCombo);
            text_desc = (TextView) itemView.findViewById(R.id.text_Combodesc);
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
