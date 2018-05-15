package com.app.grocerybazzar.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterAddressListForOrders;
import com.app.grocerybazzar.pojos.AddressInfo;
import com.app.grocerybazzar.pojos.CartList;
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
public class FragmentAddressListForOrder extends Fragment implements CompleteListener{

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private AdapterAddressListForOrders adapterAddressList;
    LinearLayoutManager mLayoutManager;
    TextView tvNoAdd;
    CartList cartList;
    public FragmentAddressListForOrder() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            cartList = (CartList) getArguments().getSerializable(C.DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_list_for_order, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        fab = (FloatingActionButton)view.findViewById(R.id.fab_add_address);
        tvNoAdd=(TextView)view.findViewById(R.id.tv_no_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivity(intent);*/
                ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_ADD_ADDRESS,null);
            }
        });
        getAddreses();
    }
    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText("Addresses");
        ActivityContainer.cartInfoView.setVisibility(View.GONE);

    }
    void getAddreses(){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));
        serviceConnection.sendToServer(C.BASE_URL_USERS,C.addresses,hashMap,C.MSG,FragmentAddressListForOrder.this);
    }

    @Override
    public void done(String response) {
        try {
            Log.e("DEBUG", "Response==" + response);
            Gson gson = new Gson();
            AddressInfo addressInfo = gson.fromJson(response, AddressInfo.class);
            if (addressInfo.getStatus().equals(C.SUCSESS)) {
                if(addressInfo.getAddresses()!=null &&addressInfo.getAddresses().size()>0 ) {
                    tvNoAdd.setVisibility(View.GONE);
                    adapterAddressList = new AdapterAddressListForOrders(addressInfo.getAddresses(), getActivity(),cartList);
                    recyclerView.setAdapter(adapterAddressList);
                }
                else {
                    tvNoAdd.setVisibility(View.VISIBLE);

                }
                //  adapterGridCategory.notifyDataSetChanged();
            } else {

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Context getApplicationsContext() {
        return getActivity();
    }
}
