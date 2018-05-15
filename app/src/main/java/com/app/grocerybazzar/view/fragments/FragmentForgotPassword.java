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
import android.widget.Toast;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.Utils;
import com.app.grocerybazzar.validations.ValidateEditText;
import com.app.grocerybazzar.validations.Validations;
import com.google.gson.Gson;

import java.util.HashMap;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForgotPassword extends Fragment implements CompleteListener{

    int flags;
    Utils util;
    Validations validation ;
    ValidateEditText etEmail;
    Button btnSubmit;
    public FragmentForgotPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validation = new Validations();
        util=new Utils();
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_EMAIL;
        etEmail=new ValidateEditText((EditText)view.findViewById(R.id.email),getActivity(),flags);
        validation.addtoList(etEmail);
        btnSubmit=(Button)view.findViewById(R.id.submit_button);
        btnSubmit.setOnClickListener(mSubmitClickListner);
    }


    View.OnClickListener mSubmitClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(util.isInternetOn(getActivity())) {
                if(validation.validateAllEditText()){



                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put(C.EMAIL,etEmail.getString());
                    ServiceConnection serviceConnection = new ServiceConnection();
                    serviceConnection.sendToServer(C.BASE_URL_USERS,C.FORGOT,hashMap,C.MSG,FragmentForgotPassword.this);
                }
            }
            else {
                getDailogConfirm(getString(R.string.internet_issue)
                        , "Internet Issue");
            }
        }
    };


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
    @Override
    public void done(String response) {
        try {
            Log.e("DEBUG", "Response==" + response);
            Gson gson = new Gson();
            Status status = gson.fromJson(response, Status.class);
            if (status.getStatus().equals(C.SUCSESS)) {
           /* Intent intent=new Intent(getActivity(), ActivityHome.class);
            startActivity(intent);*/

                Toast.makeText(getActivity(),status.getMessage(),Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();

            } else {
                getDailogConfirm(status.getMessage(), "");
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
