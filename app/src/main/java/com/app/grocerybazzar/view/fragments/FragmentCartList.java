package com.app.grocerybazzar.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterCartList;
import com.app.grocerybazzar.pojos.Cart;
import com.app.grocerybazzar.pojos.CartList;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.view.ActivityContainer;
import com.google.gson.Gson;

import java.util.HashMap;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCartList extends Fragment implements CompleteListener {

    TextView tvNoProduct,tvAmount;
    View btnCheckOut;
    private RecyclerView recyclerView;
    private AdapterCartList adapterCartList;
    LinearLayoutManager mLayoutManager;
    String mAction="";
    CartList cartList;
    public FragmentCartList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.cartInfoView.setVisibility(View.GONE);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNoProduct=(TextView)view.findViewById(R.id.tv_no_product);
        tvAmount=(TextView)view.findViewById(R.id.tvAmount);
        btnCheckOut=(View)view.findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable(C.DATA,cartList);

                ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_ADDRESS_LIST_FOR_ORDER,bundle);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        getCartList();
    }

    public void deleteItemFromCart(int pos){
        Cart cart=adapterCartList.getItem(pos);
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        HashMap<String,String> hashMap=new HashMap<>();
        mAction=C.delete_cart;
        hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));
        hashMap.put(C.PRODUCT_CART_ID, cart.getProductCartId());

        serviceConnection.sendToServer(C.BASE_URL_PRODUCT,C.delete_cart,hashMap,C.MSG,FragmentCartList.this);
    }

    public void editCartItem(int pos){
        Cart cart=adapterCartList.getItem(pos);
        editProduct(cart);
    }
    void  getCartList(){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        HashMap<String,String> hashMap=new HashMap<>();
        mAction=C.cart;
        hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));
        serviceConnection.sendToServer(C.BASE_URL_PRODUCT,C.cart,hashMap,C.MSG,FragmentCartList.this);
    }

    void  updateCartList(Cart cart){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        HashMap<String,String> hashMap=new HashMap<>();
        mAction=C.update_cart;
        hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));
        hashMap.put(C.PRODUCT_CART_ID,cart.getProductCartId());
        hashMap.put(C.product_variant_id,cart.getProduct_variant_id());

        hashMap.put(C.quantity, cart.getQuantity());

        serviceConnection.sendToServer(C.BASE_URL_PRODUCT,C.update_cart,hashMap,C.MSG,FragmentCartList.this);
    }
    @Override
    public void done(String response) {
        try {
            Log.e("DEBUG", "Response==" + response);
            Gson gson = new Gson();
            if(mAction.equals(C.cart)){
                cartList = gson.fromJson(response, CartList.class);
                if (cartList.getStatus().equals(C.SUCSESS)) {

                    if(cartList.getCart()!=null &&cartList.getCart().size()>0 ) {
                        tvNoProduct.setVisibility(View.GONE);
                        adapterCartList = new AdapterCartList(cartList.getCart(), getActivity());
                        recyclerView.setAdapter(adapterCartList);
                        ActivityContainer.tvTitle.setText("Cart ("+cartList.getCart().size()+" items)");
                        double total=0;
                        for (int i=0;i<cartList.getCart().size();i++){
                           total =total+Double.parseDouble(cartList.getCart().get(i).getProductPrice());
                        }
                        tvAmount.setText(getString(R.string.rs)+" " +total);

                    }
                    else {
                        tvNoProduct.setVisibility(View.VISIBLE);
                        ActivityContainer.tvTitle.setText("Cart (0 item)");

                    }
                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    // getActivity().onBackPressed();
                }
            }
           else if(mAction.equals(C.delete_cart)){
                Status status = gson.fromJson(response, Status.class);
                if (status.getStatus().equals(C.SUCSESS)) {

                   getCartList();
                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    // getActivity().onBackPressed();
                }
            }
          else   if(mAction.equals(C.update_cart)){
                Status status = gson.fromJson(response, Status.class);
                if (status.getStatus().equals(C.SUCSESS)) {

                    getCartList();
                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    // getActivity().onBackPressed();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    long quan;
    @Override
    public Context getApplicationsContext() {
        return getActivity();
    }

    void editProduct(final Cart cart){


            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_for_edit_product);

            ImageView ivCartSub = (ImageView) dialog.findViewById(R.id.ivCartSub);
            ImageView ivCardAdd = (ImageView) dialog.findViewById(R.id.ivCartAdd);
            Button mDone = (Button) dialog.findViewById(R.id.btnDone);
            TextView mProductName = (TextView) dialog.findViewById(R.id.tvProductName);

            final TextView tvStock = (TextView) dialog.findViewById(R.id.tvstock);
        tvStock.setText(cart.getQuantity());
        mProductName.setText(cart.getProductName());

            dialog.setCancelable(true);
            dialog.show();

             quan=Long.parseLong(cart.getQuantity());
        ivCartSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quan>0){
                    quan=quan-1;
                    cart.setQuantity(""+quan);
                    tvStock.setText(""+quan);
                }
            }
        });
        ivCardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quan>0){
                    if(quan<=Long.parseLong(cart.getStock())) {
                        quan = quan + 1;
                        cart.setQuantity("" + quan);
                        tvStock.setText("" + quan);
                    }
                    else {
                        getDailogConfirm(getString(R.string.product_limit_exceed),"");
                    }
                }
            }
        });
        mDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                updateCartList(cart);
                }
            });


        }

    void getDailogConfirm(String dataText, String titleText) {
        try {
            final Dialog dialog = new Dialog(getActivity());
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
