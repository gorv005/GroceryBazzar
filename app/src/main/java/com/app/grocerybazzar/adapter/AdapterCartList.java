package com.app.grocerybazzar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.imagecaching.ImageLoader;
import com.app.grocerybazzar.pojos.Cart;
import com.app.grocerybazzar.view.ActivityContainer;

import java.util.List;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class AdapterCartList extends RecyclerView
        .Adapter<AdapterCartList
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Cart> mDataset;
    private static ClickListener mClickListener;
    static Context context;
    ImageLoader imageLoader;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvSubCatname;
        TextView tvSubPrice;
        TextView tvWeight;
        TextView tvQuanitityNo;

        ImageView imgSubCat,imgDeleteToCart,imgEditProduct;


        public DataObjectHolder(View itemView) {
            super(itemView);
            tvSubCatname = (TextView) itemView.findViewById(R.id.tvsub_cat_name);
            tvSubPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvWeight = (TextView) itemView.findViewById(R.id.tvWeight);
            tvQuanitityNo = (TextView) itemView.findViewById(R.id.tvQuanitityNo);

            imgEditProduct = (ImageView) itemView.findViewById(R.id.ivCartEdit);
            imgDeleteToCart = (ImageView) itemView.findViewById(R.id.ivdeleteCart);

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
    public AdapterCartList(List<Cart> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
        imageLoader=new ImageLoader(context);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.tvSubCatname.setText(mDataset.get(position).getProductName());
        holder.tvSubPrice.setText(context.getString(R.string.rs)+" " +mDataset.get(position).getProductPrice());
        holder.tvQuanitityNo.setText(mDataset.get(position).getQuantity());
        holder.tvWeight.setText(mDataset.get(position).getWeight());

        imageLoader.DisplayImage(mDataset.get(position).getProductImage(),holder.imgSubCat);
            holder.imgDeleteToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityContainer)context).deleteCartItem(position);
                }
            });



            holder.imgEditProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityContainer)context).editCartItem(position);

                }
            });
        holder.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
              /*  Intent intent=new Intent(context, ActivityContainer.this);
                Bundle bundle=new Bundle();
                bundle.putSerializable(C.DATA,mDataset.get(position));
                bundle.putSerializable(C.SUB_CAT_DETAIL,mDataset.get(position));

                ((ActivityContainer)context).fragmnetLoader(C.FRAGMENT_VIEW_EDIT_ADDRESS,bundle);
*/
            }
        });
    }

    public void addItem(Cart dataObj, int index) {
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
    public Cart getItem(int pos){
        return mDataset.get(pos);
    }
    public interface ClickListener {
        public void onItemClick(int position, View v);
    }
}