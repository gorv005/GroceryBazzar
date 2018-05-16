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
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterOrderConfirmation;
import com.app.grocerybazzar.pojos.Address;
import com.app.grocerybazzar.pojos.CartList;
import com.app.grocerybazzar.pojos.ProductOrder;
import com.app.grocerybazzar.pojos.ProductOrderConfirmation;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.pojos.User;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.view.ActivityContainer;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrderConfirmationScreen extends Fragment implements CompleteListener{
    TextView tvAmount,tvName,tvAdd;

    View btnCheckOut;
    private RecyclerView recyclerView;
    private AdapterOrderConfirmation adapterCartList;
    LinearLayoutManager mLayoutManager;
    String mAction="";
    CartList cartList;
    Address address;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            cartList = (CartList) getArguments().getSerializable(C.CART_LIST);
            address = (Address) getArguments().getSerializable(C.addresses);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public FragmentOrderConfirmationScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_confirmation_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvAmount=(TextView)view.findViewById(R.id.tvAmount);
        tvName=(TextView)view.findViewById(R.id.tvName);
        tvAdd=(TextView)view.findViewById(R.id.tvAdd);

        btnCheckOut=(View)view.findViewById(R.id.btnConfirm);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle bundle=new Bundle();
                bundle.putSerializable(C.DATA,cartList);

                ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_ADDRESS_LIST_FOR_ORDER,bundle);*/
                orderPlace();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapterCartList = new AdapterOrderConfirmation(cartList.getCart(), getActivity());
        recyclerView.setAdapter(adapterCartList);
        double total=0;
        for (int i=0;i<cartList.getCart().size();i++){
            total =total+(Double.parseDouble(cartList.getCart().get(i).getProductPrice()) * Double.parseDouble(cartList.getCart().get(i).getQuantity()));
        }
        tvAmount.setText(getString(R.string.rs)+" " +total);
        tvAmount.setText("Total Amount " +getString(R.string.rs)+" " +total);
        User user = SharedPreference.getInstance(getActivity()).getUser(C.USER);
        tvName.setText(user.getFirstName()+" "+user.getLastName());
        if(address.getAddressLine1()!=null) {
            tvAdd.setText(address.getAddressLine1()+", "+address.getCity()+", "+address.getCountry()+", "+address.getPincode());
        }
        else {
            tvAdd.setText(address.getCity()+", "+address.getCountry()+", "+address.getPincode());

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText("Confirm Order");
        ActivityContainer.cartInfoView.setVisibility(View.GONE);

    }
    void  orderPlace(){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        HashMap<String,String> hashMap=new HashMap<>();
        mAction=C.order_item;
        hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));
        hashMap.put(C.ADDRESS_ID,address.getAddressId());
        hashMap.put(C.PAYMENT_TYPE,"COD");
        double total=0;
        List<ProductOrder> orders=new ArrayList<>();
        if(cartList!=null && cartList.getCart()!=null){
            for(int i=0;i<cartList.getCart().size();i++){
                total =total+(Double.parseDouble(cartList.getCart().get(i).getProductPrice()) * Double.parseDouble(cartList.getCart().get(i).getQuantity()));
                ProductOrder productOrder=new ProductOrder();
                productOrder.setProductId(cartList.getCart().get(i).getProductId());
                productOrder.setProductQuantity(cartList.getCart().get(i).getQuantity());
                productOrder.setOfferCode("");
                orders.add(productOrder);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        hashMap.put(C.PRODUCT,json);
        hashMap.put(C.OFFER_CODE,"OFFER");
        hashMap.put(C.TOTAL_AMOUNT,""+total);

        ProductOrderConfirmation productOrderConfirmation=new ProductOrderConfirmation();
        productOrderConfirmation.setAddressId(address.getAddressId());
        productOrderConfirmation.setOfferCode("OFFER");
        productOrderConfirmation.setTotalAmount(""+total);
        productOrderConfirmation.setProduct(orders);
        productOrderConfirmation.setPaymentType("COD");
        productOrderConfirmation.setUserId(SharedPreference.getInstance(getActivity()).getString(C.USER_ID));

         json = gson.toJson(productOrderConfirmation);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        serviceConnection.makeJsonObjectRequest(C.order_item,obj.toString(),C.MSG,FragmentOrderConfirmationScreen.this);
    //    serviceConnection.sendToServer(C.BASE_URL_ORDERS,C.order_item,hashMap,C.MSG,FragmentOrderConfirmationScreen.this);

    }
    @Override
    public void done(String response) {
        try {
            Log.e("DEBUG", "Response==" + response);
            Gson gson = new Gson();
            if (mAction.equals(C.order_item)) {
                Status status = gson.fromJson(response, Status.class);
                if (status.getStatus().equals(C.SUCSESS)) {

                    ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_THANKU_SCREEN,null);

                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    getDailogConfirm(status.getMessage()
                            , "");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Context getApplicationsContext() {
        return getActivity();
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
