package com.app.grocerybazzar.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.PincodeInfo;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.app.grocerybazzar.validations.ValidateEditText;
import com.app.grocerybazzar.validations.Validations;
import com.app.grocerybazzar.view.ActivityContainer;
import com.google.gson.Gson;

import java.util.HashMap;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddAddress extends Fragment implements CompleteListener {
    int flags;
    Utils util;
    String action;
    Validations validation ;
    ValidateEditText etAddressLine1,etAddressLine2,etlandmark,etPincode,etCity,etState,etCountry;
    Button btnAdd;

    public FragmentAddAddress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_add_address, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText("Add Address");
        ActivityContainer.cartInfoView.setVisibility(View.GONE);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validation = new Validations();
        util=new Utils();
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etAddressLine1=new ValidateEditText((EditText)view.findViewById(R.id.etAddressLine1),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etAddressLine2=new ValidateEditText((EditText)view.findViewById(R.id.etAddressLine2),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etlandmark=new ValidateEditText((EditText)view.findViewById(R.id.etLandmark),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_PINCODE;
        etPincode=new ValidateEditText((EditText)view.findViewById(R.id.etPincode),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etCity=new ValidateEditText((EditText)view.findViewById(R.id.etCity),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etCountry=new ValidateEditText((EditText)view.findViewById(R.id.etCountry),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etState=new ValidateEditText((EditText)view.findViewById(R.id.etState),getActivity(),flags);
        btnAdd=(Button)view.findViewById(R.id.add_button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });

        etPincode.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && etPincode.getString().length()==6){
                    action=C.PINCODE_ADD;
                    ServiceConnection serviceConnection = new ServiceConnection();
                    //  serviceConnection.makeJsonObjectRequest(C.SignUp,json,C.MSG,FragmentRegister.this);
                    HashMap<String, String> hashMap = new HashMap<>();
                    serviceConnection.sendToServerForPincode(C.PINCODE_ADDRESS,etPincode.getString(), hashMap, C.MSG, FragmentAddAddress.this);
                }
            }
        });
        validation.addtoList(etAddressLine1);
        validation.addtoList(etAddressLine2);
        validation.addtoList(etlandmark);
        validation.addtoList(etPincode);
     //   validation.addtoList(etCity);
     //   validation.addtoList(etCountry);
      //  validation.addtoList(etState);
    }

    void addAddress(){
        if(validation.validateAllEditText()) {
            action=C.ADD_ADDRESS;

            ServiceConnection serviceConnection = new ServiceConnection();
            //  serviceConnection.makeJsonObjectRequest(C.SignUp,json,C.MSG,FragmentRegister.this);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(C.ADDRESS_LINE1, etAddressLine1.getString());
            hashMap.put(C.ADDRESS_LINE2, etAddressLine2.getString());
            hashMap.put(C.LANDMARK, etlandmark.getString());
            hashMap.put(C.PINCODE, etPincode.getString());
            hashMap.put(C.CITY, etCity.getString());
            hashMap.put(C.COUNTRY, etCountry.getString());
            hashMap.put(C.STATE, etState.getString());
            hashMap.put(C.USER_ID, SharedPreference.getInstance(getActivity()).getString(C.USER_ID));

            serviceConnection.sendToServer(C.BASE_URL_USERS, C.createAdd, hashMap, C.MSG, FragmentAddAddress.this);
        }
    }

    @Override
    public void done(String response) {
        try {
            Log.e("DEBUG", "Response==" + response);
            if(action.equals(C.ADD_ADDRESS)) {
                Gson gson = new Gson();
                Status status = gson.fromJson(response, Status.class);
                if (status.getStatus().equals(C.SUCSESS)) {
                    getDailogConfirm(status.getMessage(), "");

                } else {

                        getDailogConfirm(status.getMessage(), "1");
                }
            }
            else if(action.equals(C.PINCODE_ADD)){
                Gson gson = new Gson();
                PincodeInfo pincodeInfo = gson.fromJson(response, PincodeInfo.class);
                if (pincodeInfo.getStatus().equals(C.success)) {
                    etCity.getEditText().setText(pincodeInfo.getPostOffice().get(0).getName());
                    etState.getEditText().setText(pincodeInfo.getPostOffice().get(0).getState());
                    etCountry.getEditText().setText(pincodeInfo.getPostOffice().get(0).getCountry());

                }
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


    void getDailogConfirm(String dataText, final String titleText) {
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
                    if(titleText.equals("1")) {
                        dialog.dismiss();
                    }
                    else {
                        dialog.dismiss();
                        getActivity().onBackPressed();
                    }

                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
