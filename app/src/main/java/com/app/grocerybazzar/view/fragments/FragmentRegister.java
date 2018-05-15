package com.app.grocerybazzar.view.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.LoginInfo;
import com.app.grocerybazzar.pojos.SignUp;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.app.grocerybazzar.validations.ValidateEditText;
import com.app.grocerybazzar.validations.Validations;
import com.app.grocerybazzar.view.ActivityHomeWithCategory;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegister extends Fragment implements CompleteListener{
    int flags;
    String action;
    Utils util;
    private DatePickerDialog DatePickerDialogDob;
    Validations validation ;
    ValidateEditText etFirstName,etLastName,etDOB,etGender,etEmail,etPrimaryMobile,etSecondaryMobile,etPassowrd;
    Button btnRegister;
    private SimpleDateFormat dateFormatter;
    private int year;
    private int month;
    private int day;
    Spinner spnGender;
    String[] gender = new String[]{
            "Gender",
            "Male",
            "Female"
    };
    public FragmentRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validation = new Validations();
        util=new Utils();
        dateFormatter = new SimpleDateFormat(C.DATE_FORMAT);

        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etFirstName=new ValidateEditText((EditText)view.findViewById(R.id.etfirstName),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etLastName=new ValidateEditText((EditText)view.findViewById(R.id.etLastName),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etDOB=new ValidateEditText((EditText)view.findViewById(R.id.etDOB),getActivity(),flags);
        etDOB.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogDob.show();

            }
        });
        Calendar newCalendar = Calendar.getInstance();
        year  = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day   = newCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialogDob = new DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int y, int monthOfYear, int dayOfMonth) {

               Log.e("DEBUG",year+ " "+ month+" "+dayOfMonth);
                year  = y;
                month = monthOfYear;
                day   = dayOfMonth;
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, monthOfYear, dayOfMonth);
                etDOB.getEditText().setText(Utils.getDesiredDate(C.DATE_FORMAT,dateFormatter.format(newDate.getTime()),C.DESIREDFORMAT));
            }

        },year, month, day);
        DatePickerDialogDob.getDatePicker().setMaxDate(System.currentTimeMillis());
        spnGender=(Spinner)view.findViewById(R.id.spinner_gender);
        final List<String> genderList = new ArrayList<>(Arrays.asList(gender));
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etGender=new ValidateEditText((EditText)view.findViewById(R.id.etGender),getActivity(),flags);
        etGender.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnGender.performClick();
            }
        });

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                etGender.getEditText().setText(gender[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),R.layout.spinner_item_new,genderList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(spinnerArrayAdapter);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_EMAIL;
        etEmail=new ValidateEditText((EditText)view.findViewById(R.id.etEmail),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_MOBILE;
        etPrimaryMobile=new ValidateEditText((EditText)view.findViewById(R.id.etMobilePrimary),getActivity(),flags);
       // flags = 0 | Validations.FLAG_NOT_EMPTY;
      //  flags = flags | Validations.TYPE_MOBILE;
        etSecondaryMobile=new ValidateEditText((EditText)view.findViewById(R.id.etMobileSecondary),getActivity(),flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_Password;
        etPassowrd=new ValidateEditText((EditText)view.findViewById(R.id.password),getActivity(),flags);

        btnRegister=(Button)view.findViewById(R.id.sign_up_button);
        btnRegister.setOnClickListener(mSignUpClickListner);
        validation.addtoList(etPassowrd);
   //     validation.addtoList(etSecondaryMobile);
        validation.addtoList(etPrimaryMobile);
        validation.addtoList(etEmail);
        validation.addtoList(etGender);
        validation.addtoList(etDOB);
        validation.addtoList(etLastName);
        validation.addtoList(etFirstName);

    }

    View.OnClickListener mSignUpClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(util.isInternetOn(getActivity())) {
                if(validation.validateAllEditText()){
                    SignUp signUp=new SignUp();
                    signUp.setFirstName(etFirstName.getString());
                    signUp.setLastName(etLastName.getString());
                    signUp.setDateOfBirth(etDOB.getString());
                    signUp.setGender(etGender.getString());
                    signUp.setEmail(etEmail.getString());
                    signUp.setMobilePrimary(etPrimaryMobile.getString());
                    signUp.setPassword(etPassowrd.getString());

                    Gson gson = new Gson();
                    String json = gson.toJson(signUp);
                    ServiceConnection serviceConnection = new ServiceConnection();
                  //  serviceConnection.makeJsonObjectRequest(C.SignUp,json,C.MSG,FragmentRegister.this);
                    action=C.SignUp;
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put(C.FIRSTNAME,etFirstName.getString());
                    hashMap.put(C.LASTNAME,etLastName.getString());
                    hashMap.put(C.DOB,etDOB.getString());
                    hashMap.put(C.GENDER,etGender.getString());
                    hashMap.put(C.EMAIL,etEmail.getString());
                    hashMap.put(C.MOBILE_NO,etPrimaryMobile.getString());
                    hashMap.put(C.PASSWORD,etPassowrd.getString());
                    serviceConnection.sendToServer(C.BASE_URL_USERS,C.SignUp,hashMap,C.MSG,FragmentRegister.this);

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
            if(action.equals(C.SignUp)) {
                Gson gson = new Gson();
                Status status = gson.fromJson(response, Status.class);
                if (status.getStatus().equals(C.SUCSESS)) {
           /* Intent intent=new Intent(getActivity(), ActivityHome.class);
            startActivity(intent);*/
                    Toast.makeText(getActivity(), status.getMessage(), Toast.LENGTH_LONG).show();

                    ServiceConnection serviceConnection = new ServiceConnection();
                    //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
                    action=C.Login;
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put(C.EMAIL,etEmail.getString());
                    hashMap.put(C.PASSWORD,etPassowrd.getString());
                    serviceConnection.sendToServer(C.BASE_URL_USERS,C.Login,hashMap,C.MSG,FragmentRegister.this);



                } else {
                        getDailogConfirm(status.getMessage(), "");
                    }

            }
            else if(action.equals(C.Login)){
                Gson gson = new Gson();
                LoginInfo status = gson.fromJson(response, LoginInfo.class);
                if (status.getStatus().equals(C.SUCSESS)) {
                    SharedPreference.getInstance(getActivity()).setString(C.USER_ID,status.getUser().getId());
                    SharedPreference.getInstance(getActivity()).setUser(C.USER, status.getUser());

                    //  Toast.makeText(getActivity(),status.getMessage(),Toast.LENGTH_LONG).show();
                    SharedPreference.getInstance(getActivity()).setBoolean(C.IS_LOGIN,true);
                    Intent intent = new Intent(getActivity(), ActivityHomeWithCategory.class);
                    intent.putExtra("USER_DETAILS",response);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    getDailogConfirm(status.getMessage(), "");
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
}
