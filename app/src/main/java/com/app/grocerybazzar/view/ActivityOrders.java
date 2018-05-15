package com.app.grocerybazzar.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterOrdersList;
import com.app.grocerybazzar.pojos.OrdersResponse;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

public class ActivityOrders extends AppCompatActivity implements CompleteListener {

    private RecyclerView recyclerView;
    private Utils util;
    private Context mContext;
    private AdapterOrdersList adapterOrdersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        util = new Utils();
        mContext = ActivityOrders.this;

        recyclerView = (RecyclerView) findViewById(R.id.lstOrders);
        if (util.isInternetOn(ActivityOrders.this)) {
            {
                String id = SharedPreference.getInstance(ActivityOrders.this).getString(C.USER_ID);

                ServiceConnection serviceConnection = new ServiceConnection();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(C.user_id, id);
                serviceConnection.sendToServer(C.BASE_URL, C.USER_ORDERS, hashMap, C.MSG, ActivityOrders.this);
            }
        } else {
            util.getDailogConfirm(getString(R.string.internet_issue), "Internet Issue", mContext);
        }


    }

    @Override
    public void done(String response) {

        Gson gson = new Gson();
        OrdersResponse ordersResponse = gson.fromJson(response.toString(), OrdersResponse.class);
        adapterOrdersList = new AdapterOrdersList(ordersResponse);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterOrdersList);
        // Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();

    }

    @Override
    public Context getApplicationsContext() {
        return mContext;
    }
}
