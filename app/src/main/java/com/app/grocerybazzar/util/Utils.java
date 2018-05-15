package com.app.grocerybazzar.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.SideMenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GAURAV on 10/17/2017.
 */

public class Utils {
    ProgressDialog progressDialog=null;
    public static boolean isInternetOn(Activity activity) {
        try {
            ConnectivityManager cm = (ConnectivityManager) activity
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    public void showDialog(String msg, Context context){
        try {

            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(msg);
                progressDialog.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void hideDialog(){
        try {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static ArrayList<SideMenuItem> getSideMenuList() {
        ArrayList<SideMenuItem> sideMenuItems = new ArrayList<SideMenuItem>();
      //  sideMenuItems.add(new SideMenuItem(R.string.search, R.drawable.search));
      //  sideMenuItems.add(new SideMenuItem(R.string.profile, R.drawable.user));
        //sideMenuItems.add(new SideMenuItem(R.string.update_profile, R.drawable.user));
         sideMenuItems.add(new SideMenuItem(R.string.Addresses, R.drawable.address));
        sideMenuItems.add(new SideMenuItem(R.string.my_orders, R.drawable.my_orders));
        sideMenuItems.add(new SideMenuItem(R.string.my_account, R.drawable.account));
        sideMenuItems.add(new SideMenuItem(R.string.change_password, R.drawable.change_password));
        sideMenuItems.add(new SideMenuItem(R.string.logout, R.drawable.logout));

        return sideMenuItems;
    }
    public static Map<String, String> getHeader() {
       /* String plainCreds = userName;//"np-loc-rest:internal@123";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encode(plainCredsBytes, Base64.DEFAULT);
        String base64Creds = new String(base64CredsBytes);*/
        HashMap<String, String> headers = new HashMap<String, String>();
        //   headers.put("Authorization", "Basic " + base64Creds);
        //  headers.put("Accept", "application/json");
        //   headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("API-KEY", "ummuhlLUAqkxEINRyKbg7Lzt5sEKzOEg");
        return headers;
    }

    public  static String getDesiredDate(String format, String date, String newformat){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(newformat);
        String newFormat = formatter.format(testDate);
        return newFormat;
    }
    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }
    public void getDailogConfirm(String dataText, String titleText, Context mContext) {
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
