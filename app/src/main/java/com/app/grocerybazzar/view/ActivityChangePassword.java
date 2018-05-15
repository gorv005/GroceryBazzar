package com.app.grocerybazzar.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.ChangePassword;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

import static com.app.grocerybazzar.util.C.ChangePassword;

public class ActivityChangePassword extends AppCompatActivity implements CompleteListener {

    private Button btnChangePassword;
    private EditText etNewPassword, etCnfNewPassword, etCurrentPassword;
    private Utils util;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mContext = ActivityChangePassword.this;
        util = new Utils();
        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etCnfNewPassword = (EditText) findViewById(R.id.etCnfNewPassword);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNewPassword.getText().toString().equals(etCnfNewPassword.getText().toString())) {
                    Toast.makeText(ActivityChangePassword.this, "Password mismatch", Toast.LENGTH_LONG).show();;
                } /*else if(password validation){

                }*/
                else{
                    if(util.isInternetOn(ActivityChangePassword.this)) {
                        {
                            ChangePassword changePassword = new ChangePassword();
                            String id = SharedPreference.getInstance(ActivityChangePassword.this).getString(C.USER_ID);
                            changePassword.setUser_id(id);
                            changePassword.setOld_password(etCurrentPassword.getText().toString());
                            changePassword.setPassword(etNewPassword.getText().toString());
                            changePassword.setConfirm_password(etCnfNewPassword.getText().toString());

                            Gson gson = new Gson();
                            String json = gson.toJson(changePassword);
                            ServiceConnection serviceConnection = new ServiceConnection();

                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put(C.user_id,id);
                            hashMap.put(C.old_password,etCurrentPassword.getText().toString());
                            hashMap.put(C.password,etNewPassword.getText().toString());
                            hashMap.put(C.confirm_password,etCnfNewPassword.getText().toString());
                            serviceConnection.sendToServer(C.BASE_URL_USERS, ChangePassword,hashMap, C.MSG,ActivityChangePassword.this);
                        }
                    }
                    else {
                        getDailogConfirm(getString(R.string.internet_issue), "Internet Issue");
                    }
                }
            }
        });
    }

    @Override
    public void done(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String msg = object.optString("message");
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Context getApplicationsContext() {
        return mContext;
    }

    void getDailogConfirm(String dataText, String titleText) {
        try {
            final Dialog dialog = new Dialog(mContext);
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
