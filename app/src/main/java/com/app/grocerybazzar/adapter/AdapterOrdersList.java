package com.app.grocerybazzar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.Order;
import com.app.grocerybazzar.pojos.OrdersResponse;

/**
 * Created by AbhinavT on 02-02-2018.
 */

public class AdapterOrdersList extends RecyclerView.Adapter<AdapterOrdersList.MyViewHolder> {

    private OrdersResponse ordersResponse;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivOrders;
        public TextView tvOrderId, tvOrderNo, tvDate, tvStatus;

        public MyViewHolder(View view) {
            super(view);
            ivOrders = (ImageView) view.findViewById(R.id.ivOrders);
            tvOrderNo = (TextView) view.findViewById(R.id.tvOrderNo);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvOrderId = (TextView) view.findViewById(R.id.tvOrderId);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);

        }
    }


    public AdapterOrdersList(OrdersResponse ordersResponse) {
        this.ordersResponse = ordersResponse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_orders, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = ordersResponse.getOrder().get(position);
        holder.tvOrderId.setText("Order ID: " + order.getOrderId());
        holder.tvOrderNo.setText("Order No.: " + order.getOrderNumber());
        holder.tvDate.setText("Placed on: " + order.getOrderDate());
        holder.tvStatus.setText("Deliverd");


        holder.tvOrderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

//    @Override
//    public AdapterOrdersList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }

    @Override
    public int getItemCount() {
        return ordersResponse.getOrder().size();
    }
}