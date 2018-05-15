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
import android.widget.Toast;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterProductInfo;
import com.app.grocerybazzar.pojos.AddToCartInfo;
import com.app.grocerybazzar.pojos.CartList;
import com.app.grocerybazzar.pojos.Category;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.pojos.SubCategoryInfo;
import com.app.grocerybazzar.pojos.Subcategory;
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
public class FragmentProductInfo extends Fragment implements CompleteListener {
    TextView tvNoProduct;
    private RecyclerView recyclerView;
    private AdapterProductInfo adapterSubCategoryDetails;
    LinearLayoutManager mLayoutManager;
    SubCategoryInfo subCategoryInfo;
    Category category;
    String mAction;
    CartList cartList;
    Subcategory subcategory;

    public FragmentProductInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            cartList = (CartList) getActivity().getIntent().getExtras().getSerializable(C.CART_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_info, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText("Products");
        ActivityContainer.cartInfoView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* category = (Category)getActivity().getIntent().getExtras().getSerializable(C.DATA);
        subcategory = (Subcategory)getActivity().getIntent().getExtras().getSerializable(C.SUB_CAT_DETAIL);*/
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category = (Category) bundle.getSerializable(C.DATA);
            subcategory = (Subcategory) bundle.getSerializable(C.SUB_CAT_DETAIL);

        }
        tvNoProduct = (TextView) view.findViewById(R.id.tv_no_product);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        getSubCategoryList();
    }

    void getSubCategoryList() {
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        mAction = C.sub_category;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(C.cat_id, category.getCatId());
        hashMap.put(C.sub_id, subcategory.getSubcatId());
        serviceConnection.sendToServer(C.BASE_URL_PRODUCT, C.sub_category, hashMap, C.MSG, FragmentProductInfo.this);
    }

    public void addTocartProduct(int pos) {
        AddToCartInfo addToCartInfo = adapterSubCategoryDetails.getAddedProduct(pos);

        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(C.USER_ID, addToCartInfo.getUserId());
        hashMap.put(C.product_id, addToCartInfo.getProductId());
        hashMap.put(C.quantity, "" + addToCartInfo.getQuantity());
        mAction = C.add_to_cart;
        serviceConnection.sendToServer(C.BASE_URL_PRODUCT, C.add_to_cart, hashMap, C.MSG, FragmentProductInfo.this);
    }
    void  getCartList(){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        HashMap<String,String> hashMap=new HashMap<>();
        mAction=C.cart;
        hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));
        serviceConnection.sendToServer(C.BASE_URL_PRODUCT,C.cart,hashMap,C.MSG,FragmentProductInfo.this);
    }
    @Override
    public void done(String response) {

        try {
            Log.e("DEBUG", "Response==" + response);
            Gson gson = new Gson();
            if (mAction.equals(C.sub_category)) {
                subCategoryInfo = gson.fromJson(response, SubCategoryInfo.class);
                if (subCategoryInfo.getStatus().equals(C.SUCSESS)) {

                    if (subCategoryInfo.getProduct() != null && subCategoryInfo.getProduct().size() > 0) {
                        tvNoProduct.setVisibility(View.GONE);
                        adapterSubCategoryDetails = new AdapterProductInfo(subCategoryInfo.getProduct(),cartList, getActivity());
                        recyclerView.setAdapter(adapterSubCategoryDetails);
                    } else {
                        tvNoProduct.setVisibility(View.VISIBLE);

                    }
                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    // getActivity().onBackPressed();
                }
            } else if (mAction.equals(C.add_to_cart)) {
                Status status = gson.fromJson(response, Status.class);
                if (status.getStatus().equals(C.SUCSESS)) {
                   // getDailogConfirm(status.getMessage(), "");
                    Toast.makeText(getActivity(),status.getMessage(),Toast.LENGTH_LONG).show();
                    getCartList();
                } else {
                    getDailogConfirm(status.getMessage(), "");
                }

            }
            else if (mAction.equals(C.cart)) {
                cartList = gson.fromJson(response, CartList.class);
                if (cartList.getStatus().equals(C.SUCSESS)) {
                    adapterSubCategoryDetails = new AdapterProductInfo(subCategoryInfo.getProduct(),cartList, getActivity());
                    recyclerView.setAdapter(adapterSubCategoryDetails);
                    if(cartList.getCart()!=null &&cartList.getCart().size()>0 ) {
                        ActivityContainer.tvCartQuantity.setText(""+cartList.getCart().size());
                    }
                    else {
                        ActivityContainer.tvCartQuantity.setText("");
                    }

                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    ActivityContainer.tvCartQuantity.setText("");
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
