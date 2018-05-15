package com.app.grocerybazzar.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.app.grocerybazzar.pojos.Login;
import com.app.grocerybazzar.pojos.LoginInfo;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.app.grocerybazzar.validations.ValidateEditText;
import com.app.grocerybazzar.validations.Validations;
import com.app.grocerybazzar.view.ActivityHomeWithCategory;
import com.app.grocerybazzar.view.MainActivity;
import com.google.gson.Gson;

import java.util.HashMap;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment implements CompleteListener {
    int flags;
    Utils util;
    Validations validation ;
    ValidateEditText etEmail,etPassword;
    Button btnLogin,btnSignUp;
    TextView tvForgotpassword;
    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validation = new Validations();
        util=new Utils();
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_EMAIL;
        etEmail=new ValidateEditText((EditText)view.findViewById(R.id.email),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_Password;
        etPassword=new ValidateEditText((EditText)view.findViewById(R.id.password),getActivity(),flags);
        validation.addtoList(etPassword);
        validation.addtoList(etEmail);
        btnLogin=(Button)view.findViewById(R.id.sign_in_button);
        btnLogin.setOnClickListener(mLoginClickListner);
        btnSignUp=(Button)view.findViewById(R.id.sign_up_button);
        btnSignUp.setOnClickListener(mSignUpClickListner);
        tvForgotpassword=(TextView)view.findViewById(R.id.btn_reset_password);
        tvForgotpassword.setOnClickListener(mForgotPasswordClickListner);
    }

    View.OnClickListener mForgotPasswordClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).fragmnetLoader(C.FRAGMENT_FORGOT,null);

        }
    };

    View.OnClickListener mSignUpClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).fragmnetLoader(C.FRAGMENT_SIGNUP,null);
        }
    };
    View.OnClickListener mLoginClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(util.isInternetOn(getActivity())) {
                if(validation.validateAllEditText()){
                    Login login=new Login();
                    login.setEmail(etEmail.getString());
                    login.setPassword(etPassword.getString());
                    Gson gson = new Gson();
                    String json = gson.toJson(login);
                    ServiceConnection serviceConnection = new ServiceConnection();
                //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put(C.EMAIL,etEmail.getString());
                    hashMap.put(C.PASSWORD,etPassword.getString());
                    serviceConnection.sendToServer(C.BASE_URL_USERS,C.Login,hashMap,C.MSG,FragmentLogin.this);
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
        Log.e("DEBUG","Response="+response);
        try {
            Log.e("DEBUG", "Response==" + response);
            Gson gson = new Gson();
            LoginInfo status = gson.fromJson(response, LoginInfo.class);
            if (status.getStatus().equals(C.SUCSESS)) {
           /* Intent intent=new Intent(getActivity(), ActivityHome.class);
            startActivity(intent);*/
                SharedPreference.getInstance(getActivity()).setString(C.USER_ID,status.getUser().getId());
                SharedPreference.getInstance(getActivity()).setUser(C.USER, status.getUser());

                Toast.makeText(getActivity(),status.getMessage(),Toast.LENGTH_LONG).show();
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_LOGIN,true);
                Intent intent = new Intent(getActivity(), ActivityHomeWithCategory.class);
                intent.putExtra("USER_DETAILS",response);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

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
