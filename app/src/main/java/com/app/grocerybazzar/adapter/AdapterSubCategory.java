package com.app.grocerybazzar.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.imagecaching.ImageLoader;
import com.app.grocerybazzar.pojos.Category;
import com.app.grocerybazzar.pojos.Subcategory;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.view.ActivityContainer;

import java.util.List;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Subcategory> mDataset;
    private static ClickListener mClickListener;
    static Context context;
    ImageLoader imageLoader;
    Category category;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvSubCatname;
        ImageView imgSubCat;


        public DataObjectHolder(View itemView) {
            super(itemView);
            tvSubCatname = (TextView) itemView.findViewById(R.id.tvcatname);
            imgSubCat = (ImageView) itemView.findViewById(R.id.img_cat);
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
    public AdapterSubCategory(Category category,List<Subcategory> myDataset, Context context) {
        mDataset = myDataset;
        this.category=category;
        this.context = context;
        imageLoader=new ImageLoader(context);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_detail_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.tvSubCatname.setText(mDataset.get(position).getTitle());
        imageLoader.DisplayImage(mDataset.get(position).getSubcatIcon(),holder.imgSubCat);

        holder.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putSerializable(C.DATA,category);
                bundle.putSerializable(C.SUB_CAT_DETAIL,mDataset.get(position));

                ((ActivityContainer)context).fragmnetLoader(C.FRAGMENT_SUB_CAT_DETAILS,bundle);


            }
        });
    }

    public void addItem(Subcategory dataObj, int index) {
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