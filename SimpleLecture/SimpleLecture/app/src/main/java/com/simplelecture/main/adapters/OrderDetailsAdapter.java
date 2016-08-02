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
import com.simplelecture.main.model.viewmodel.OrderSummaryListModel;
import com.simplelecture.main.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by M1032185 on 7/28/2016.
 */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {


    Context mContext;

    List<OrderSummaryListModel> orderSummaryListModelArray;

    OnItemClickListener onItemClickListener;

    public OrderDetailsAdapter(Context mContext, List<OrderSummaryListModel> orderSummaryListModel, OnItemClickListener onItemClickListener) {

        this.mContext = mContext;
        this.orderSummaryListModelArray = orderSummaryListModel;
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

        OrderSummaryListModel orderSummaryListModel = orderSummaryListModelArray.get(position);

        holder.textCourseName.setText(orderSummaryListModel.getCourseName());
        holder.textCoursePrice.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryListModel.getPrice())));
        holder.subTotal.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryListModel.getSubTotalPrice())));
        holder.textMonths.setText(orderSummaryListModel.getMonths() + " Month(s)");
        holder.printedMatPrice.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryListModel.getCourseMaterialPrices())));

        if (!orderSummaryListModel.getCourseMaterialNames().equals("")) {
            holder.textViewMaterialName.setVisibility(View.VISIBLE);
            holder.textViewMaterialName.setText("(" + orderSummaryListModel.getCourseMaterialNames() + ")");
        } else {
            holder.textViewMaterialName.setVisibility(View.GONE);
        }


        holder.textMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(null, -1);
            }
        });

        if (!orderSummaryListModel.getIcon().equals("") && orderSummaryListModel.getIcon() != null) {

            Picasso.with(mContext)
                    .load(orderSummaryListModel.getIcon())
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
        return orderSummaryListModelArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMaterialName;
        private TextView textCourseName;
        private TextView textCoursePrice;
        private ImageView imageIcon;
        private ImageView imageClose;
        private TextView printedMatPrice;
        private TextView subTotal;
        private TextView textMonths;

        public MyViewHolder(View itemView) {
            super(itemView);

            textCourseName = (TextView) itemView.findViewById(R.id.text_course_name);
            textCoursePrice = (TextView) itemView.findViewById(R.id.text_course_price);
            imageIcon = (ImageView) itemView.findViewById(R.id.course_icon);
            printedMatPrice = (TextView) itemView.findViewById(R.id.text_course_printed);
            subTotal = (TextView) itemView.findViewById(R.id.txt_sub_total);
            imageClose = (ImageView) itemView.findViewById(R.id.close_icon);
            imageClose.setVisibility(View.GONE);
            textMonths = (TextView) itemView.findViewById(R.id.text_months);
            textViewMaterialName = (TextView) itemView.findViewById(R.id.textViewMaterialName);

        }
    }
}
