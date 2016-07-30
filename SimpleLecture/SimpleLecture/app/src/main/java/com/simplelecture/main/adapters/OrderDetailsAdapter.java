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
import com.simplelecture.main.model.viewmodel.CartDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.OrderSummaryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by M1032185 on 7/28/2016.
 */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {


    Context mContext;

    ArrayList<OrderSummaryModel> cartDetailsResponseModels;

    OnItemClickListener onItemClickListener;

    public OrderDetailsAdapter(Context mContext, ArrayList<OrderSummaryModel> cartDetailsResponseModels, OnItemClickListener onItemClickListener) {

        this.mContext = mContext;
        this.cartDetailsResponseModels = cartDetailsResponseModels;
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

        OrderSummaryModel cartDetailsResponseModel = cartDetailsResponseModels.get(position);

        holder.textCourseName.setText(cartDetailsResponseModel.getCourseName());
        holder.textCoursePrice.setText(cartDetailsResponseModel.getPrice());
        holder.subTotal.setText(cartDetailsResponseModel.getSubTotal());

//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, mContext.getResources().getStringArray(R.array.months));
//        holder.spinnerMonths.setAdapter(stringArrayAdapter);

        holder.textMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(null, -1);
            }
        });

        Picasso.with(mContext).load(cartDetailsResponseModel.getIcon())
                .placeholder(R.mipmap.loading)   // optional
                .error(R.mipmap.app_icon)      // optional
                //.resize(250, 200)                        // optional
                //.rotate(90)                             // optional
                .into(holder.imageIcon);

        holder.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(null, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartDetailsResponseModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textCourseName;
        private TextView textCoursePrice;
        private ImageView imageIcon;
        private ImageView imageClose;
        private TextView printedMatPrice;
        private TextView subTotal;
        private TextView textMonths;
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
//            spinnerMonths = (Spinner) itemView.findViewById(R.id.spinner_months);

        }
    }
}
