package com.app.grocerybazzar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.imagecaching.ImageLoader;
import com.app.grocerybazzar.pojos.AddToCartInfo;
import com.app.grocerybazzar.pojos.Cart;
import com.app.grocerybazzar.pojos.CartList;
import com.app.grocerybazzar.pojos.Product;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.view.ActivityContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class AdapterProductInfo extends RecyclerView
        .Adapter<AdapterProductInfo
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Product> mDataset;
    private static ClickListener mClickListener;
    static Context context;
    ImageLoader imageLoader;
    List<AddToCartInfo> addToCartInfos;
    AddToCartInfo addToCartInfo1;
    CartList cartList;
    List<Cart> carts;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvSubCatname;
        TextView tvSubPrice;
        TextView tvSubStock;
        TextView tvWeight;

        ImageView imgSubCat,imgAddToCart,imgMinusProduct,imgAddProduct;


        public DataObjectHolder(View itemView) {
            super(itemView);
            tvSubCatname = (TextView) itemView.findViewById(R.id.tvsub_cat_name);
            tvSubPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvWeight = (TextView) itemView.findViewById(R.id.tvWeight);

            tvSubStock = (TextView) itemView.findViewById(R.id.tvstock);
            imgMinusProduct = (ImageView) itemView.findViewById(R.id.ivCartSub);
            imgAddProduct = (ImageView) itemView.findViewById(R.id.ivCartAdd);
            imgAddToCart = (ImageView) itemView.findViewById(R.id.ivCart);

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
    public AdapterProductInfo(List<Product> myDataset, CartList cartList,Context context) {
        mDataset = myDataset;
        this.context = context;
        this.cartList=cartList;
        imageLoader=new ImageLoader(context);
        addToCartInfos=new ArrayList<>();
        carts=new ArrayList<>();
        if(cartList!=null && cartList.getCart()!=null&& cartList.getCart().size()>0){
            carts=cartList.getCart();
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_detail_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.tvSubCatname.setText(mDataset.get(position).getProductName());
        holder.tvSubPrice.setText(context.getString(R.string.rs)+" " +mDataset.get(position).getProductPrice());
        holder.tvWeight.setText(mDataset.get(position).getWeight());
        AddToCartInfo addToCartInfo=new AddToCartInfo();
        addToCartInfo.setProductId(mDataset.get(position).getProductId());
        addToCartInfo.setQuantity(0);
        addToCartInfo.setProductVariantId(mDataset.get(position).getVariant_id());
        addToCartInfo.setUserId(SharedPreference.getInstance(context).getString(C.USER_ID));
        if(carts!=null &&carts.size()>0){
            for(int i=0;i<carts.size();i++){
                if(mDataset.get(position).getProductId().equals(carts.get(i).getProductId())){
                    holder.imgAddToCart.setImageResource(R.drawable.cart);
                    addToCartInfo.setQuantity(Long.parseLong(carts.get(i).getQuantity()));
                    holder.tvSubStock.setText(carts.get(i).getQuantity());
                }
            }
        }

        addToCartInfos.add(position,addToCartInfo);
       // holder.tvSubStock.setText("In stock: "+mDataset.get(position).getStock());
        imageLoader.DisplayImage(mDataset.get(position).getProductImage(),holder.imgSubCat);
            holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCartInfo1=addToCartInfos.get(position);
                    if(addToCartInfo1.getQuantity()>0){
                        ((ActivityContainer)context).getAddToCartMethod(position);
                    }
                    else {
                        //TODO Please add product
                    }
                }
            });

            holder.imgMinusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   addToCartInfo1=addToCartInfos.get(position);
                  if(addToCartInfo1.getQuantity()>0){
                      addToCartInfo1.setQuantity(addToCartInfo1.getQuantity()-1);
                      addToCartInfos.set(position,addToCartInfo1);
                      holder.tvSubStock.setText(""+addToCartInfo1.getQuantity());
                      holder.imgAddToCart.setImageResource(R.drawable.add_to_cart);
                  }
                }
            });

            holder.imgAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCartInfo1=addToCartInfos.get(position);
                    if(addToCartInfo1.getQuantity()>=0) {
                        if (addToCartInfo1.getQuantity() + 1 <= Integer.parseInt(mDataset.get(position).getStock())) {
                            addToCartInfo1.setQuantity(addToCartInfo1.getQuantity() + 1);
                            addToCartInfos.set(position, addToCartInfo1);
                            holder.tvSubStock.setText(""+addToCartInfo1.getQuantity());
                            holder.imgAddToCart.setImageResource(R.drawable.add_to_cart);
                        }
                        else {
                            getDailogConfirm(context.getString(R.string.product_limit_exceed),"");
                        }
                    }
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

    public void addItem(Product dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    public  AddToCartInfo getAddedProduct(int pos){
      return   addToCartInfos.get(pos);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface ClickListener {
        public void onItemClick(int position, View v);
    }

    void getDailogConfirm(String dataText, String titleText) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //tell the Dialog to use the dialog.xml as it's layout description
            dialog.setContentView(R.layout.dialog_with_two_button);
            // dialog.setTitle("Android Custom Dialog Box");
            dialog.setCancelable(false);
            TextView dataTextTv = (TextView) dialog.findViewById(R.id.dialog_data_text);
            TextView titleTextTv = (TextView) dialog.findViewById(R.id.dialog_title_text);
            TextView cancelTv = (TextView) dialog.findViewById(R.id.dialog_cancel_text);
            TextView okTextTv = (TextView) dialog.findViewById(R.id.dialog_ok_text);

            cancelTv.setVisibility(View.GONE);
            dataTextTv.setText(dataText);
            titleTextTv.setText(titleText);

            cancelTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            okTextTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}