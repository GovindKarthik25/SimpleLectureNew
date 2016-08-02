package com.simplelecture.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.model.viewmodel.CourseListCartModel;
import com.simplelecture.main.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M1032185 on 7/28/2016.
 */
public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.MyViewHolder> {


    Context mContext;

    private List<CourseListCartModel> courseListCartModelArray;

    OnItemClickListener onItemClickListener;

    public CartDetailsAdapter(Context mContext, List<CourseListCartModel> courseListCartModel, OnItemClickListener onItemClickListener) {

        this.mContext = mContext;
        this.courseListCartModelArray = courseListCartModel;
        this.onItemClickListener = onItemClickListener;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CourseListCartModel courseListCartModel = courseListCartModelArray.get(position);

        holder.textCourseName.setText(courseListCartModel.getCourseName());
        holder.textCoursePrice.setText("Rs." + Util.decFormat(Float.valueOf(courseListCartModel.getPrice())));
        holder.subTotal.setText("Rs." + Util.decFormat(Float.valueOf(courseListCartModel.getSubTotalPrice())));
        holder.text_course_printed.setText("Rs." + Util.decFormat(Float.valueOf(courseListCartModel.getCourseMaterialPrices())));

//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, mContext.getResources().getStringArray(R.array.months));
//        holder.spinnerMonths.setAdapter(stringArrayAdapter);

        if (!courseListCartModel.getCourseMaterialNames().equals("")) {
            holder.textViewMaterialName.setVisibility(View.VISIBLE);
            holder.textViewMaterialName.setText("(" + courseListCartModel.getCourseMaterialNames() + ")");
        } else {
            holder.textViewMaterialName.setVisibility(View.GONE);
        }

        holder.textMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(null, -1);
            }
        });


        if (!courseListCartModel.getIcon().equals("") && courseListCartModel.getIcon() != null) {

            Picasso.with(mContext)
                    .load(courseListCartModel.getIcon())
                    .placeholder(R.mipmap.loading)   // optional
                    .error(R.mipmap.app_icon)      // optional
                    //.resize(250, 200)                        // optional
                    //.rotate(90)                             // optional
                    .into(holder.imageIcon);
        } else {
            holder.imageIcon.setImageResource(R.mipmap.app_icon);
        }

        holder.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(null, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseListCartModelArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textCourseName;
        private TextView textCoursePrice;
        private ImageView imageIcon;
        private ImageView imageClose;
        private TextView printedMatPrice;
        private TextView subTotal;
        private TextView textMonths;
        private TextView text_course_printed;
        private TextView textViewMaterialName;

//        private Spinner spinnerMonths;

        public MyViewHolder(View itemView) {
            super(itemView);

            textCourseName = (TextView) itemView.findViewById(R.id.text_course_name);
            textCoursePrice = (TextView) itemView.findViewById(R.id.text_course_price);
            imageIcon = (ImageView) itemView.findViewById(R.id.course_icon);
            printedMatPrice = (TextView) itemView.findViewById(R.id.text_course_printed);
            subTotal = (TextView) itemView.findViewById(R.id.txt_sub_total);
            imageClose = (ImageView) itemView.findViewById(R.id.close_icon);
            textMonths = (TextView) itemView.findViewById(R.id.text_months);
            text_course_printed = (TextView) itemView.findViewById(R.id.text_course_printed);
            textViewMaterialName = (TextView) itemView.findViewById(R.id.textViewMaterialName);
//            spinnerMonths = (Spinner) itemView.findViewById(R.id.spinner_months);

        }
    }
}
