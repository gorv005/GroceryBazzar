package com.app.grocerybazzar.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.Address;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.view.ActivityContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class AdapterAddressList extends RecyclerView
        .Adapter<AdapterAddressList
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Address> mDataset;
    private static ClickListener mClickListener;
    static Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvtext1;
        TextView tvtext2;
        TextView tvtext3;


        public DataObjectHolder(View itemView) {
            super(itemView);
            tvtext1 = (TextView) itemView.findViewById(R.id.tvtext1);
            tvtext2 = (TextView) itemView.findViewById(R.id.tvtext2);
            tvtext3 = (TextView) itemView.findViewById(R.id.tvtext3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(), v);
        }


        public void setOnItemClickListener(ClickListener mClick) {
            mClickListener = mClick;
        }
    }
    public AdapterAddressList(List<Address> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.tvtext1.setText(mDataset.get(position).getAddressLine1());
        holder.tvtext2.setText(mDataset.get(position).getAddressLine2());
        holder.tvtext3.setText(mDataset.get(position).getState());
        holder.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putSerializable(C.DATA,mDataset.get(position));
                ((ActivityContainer)context).fragmnetLoader(C.FRAGMENT_VIEW_EDIT_ADDRESS,bundle);

            }
        });
    }

    public void addItem(Address dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface ClickListener {
        public void onItemClick(int position, View v);
    }
}