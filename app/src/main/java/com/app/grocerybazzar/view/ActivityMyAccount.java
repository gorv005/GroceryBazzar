package com.app.grocerybazzar.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.Status;
import com.app.grocerybazzar.pojos.User;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.app.grocerybazzar.validations.ValidateEditText;
import com.app.grocerybazzar.validations.Validations;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

public class ActivityMyAccount extends AppCompatActivity {
    int flags;
    String action;
    Utils util;
    private DatePickerDialog DatePickerDialogDob;
    Validations validation;
    ValidateEditText etFirstName, etLastName, etDOB, etGender, etPrimaryMobile, etSecondaryMobile, etPassowrd;
    Button btnUpdate;
    private SimpleDateFormat dateFormatter;
    private int year;
    private int month;
    private int day;
    Spinner spnGender;
    boolean isEdit = false;


    String[] gender = new String[]{
            "Gender",
            "Male",
            "Female"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        validation = new Validations();
        util = new Utils();
        dateFormatter = new SimpleDateFormat(C.DATE_FORMAT);

        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etFirstName = new ValidateEditText((EditText) findViewById(R.id.etfirstName), this, flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etLastName = new ValidateEditText((EditText) findViewById(R.id.etLastName), this, flags);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etDOB = new ValidateEditText((EditText) findViewById(R.id.etDOB), this, flags);
        etDOB.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogDob.show();

            }
        });
        Calendar newCalendar = Calendar.getInstance();
        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialogDob = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int y, int monthOfYear, int dayOfMonth) {

                Log.e("DEBUG", year + " " + month + " " + dayOfMonth);
                year = y;
                month = monthOfYear;
                day = dayOfMonth;
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, monthOfYear, dayOfMonth);
                etDOB.getEditText().setText(Utils.getDesiredDate(C.DATE_FORMAT, dateFormatter.format(newDate.getTime()), C.DESIREDFORMAT));
            }

        }, year, month, day);
        DatePickerDialogDob.getDatePicker().setMaxDate(System.currentTimeMillis());
        spnGender = (Spinner) findViewById(R.id.spinner_gender);
        final List<String> genderList = new ArrayList<>(Arrays.asList(gender));
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        etGender = new ValidateEditText((EditText) findViewById(R.id.etGender), this, flags);
        etGender.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnGender.performClick();
            }
        });

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    etGender.getEditText().setText(gender[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item_new, genderList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(spinnerArrayAdapter);
        flags = 0 | Validations.FLAG_NOT_EMPTY;
        flags = flags | Validations.TYPE_MOBILE;
        etPrimaryMobile = new ValidateEditText((EditText) findViewById(R.id.etMobilePrimary), this, flags);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);


        btnUpdate.setOnClickListener(updateClickListner);
        //validation.addtoList(etPassowrd);
        //     validation.addtoList(etSecondaryMobile);
        validation.addtoList(etPrimaryMobile);
        //validation.addtoList(etEmail);
        validation.addtoList(etGender);
        validation.addtoList(etDOB);
        validation.addtoList(etLastName);
        validation.addtoList(etFirstName);
        showData();
        //disableViews();

    }

    View.OnClickListener updateClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isEdit) {
                isEdit = true;
                btnUpdate.setText(getString(R.string.update));
                enableViews();
            } else {
                updateAddress();
            }
        }
    };


    void enableViews() {
        etFirstName.getEditText().setEnabled(true);
        etLastName.getEditText().setEnabled(true);
        etDOB.getEditText().setEnabled(true);
        etGender.getEditText().setEnabled(true);
        //etPassowrd.getEditText().setEnabled(true);
        etPrimaryMobile.getEditText().setEnabled(true);

    }

    void disableViews() {
        isEdit = false;
        btnUpdate.setText(getString(R.string.edit));

        etFirstName.getEditText().setEnabled(false);
        etLastName.getEditText().setEnabled(false);
        etDOB.getEditText().setEnabled(false);
        etGender.getEditText().setEnabled(false);
        //etPassowrd.getEditText().setEnabled(false);
        etPrimaryMobile.getEditText().setEnabled(false);
        etFirstName.getEditText().clearFocus();
        etLastName.getEditText().clearFocus();
        etDOB.getEditText().clearFocus();
        etGender.getEditText().clearFocus();

    }

    void showData() {
        User user = SharedPreference.getInstance(this).getUser(C.USER);
        etFirstName.getEditText().setText(user.getFirstName());
        etLastName.getEditText().setText(user.getLastName());
        etDOB.getEditText().setText(user.getDob());
        etGender.getEditText().setText(user.getGender());
        etPrimaryMobile.getEditText().setText(user.getMobilePrimary());
        disableViews();


    }

    void updateAddress() {
        if (validation.validateAllEditText()) {
            action = C.ADD_ADDRESS;
            HashMap<String, String> hashMap = new HashMap<>();
            User user = SharedPreference.getInstance(ActivityMyAccount.this).getUser(C.USER);
            hashMap.put(C.FIRSTNAME, etFirstName.getString());
            hashMap.put(C.LASTNAME, etLastName.getString());
            hashMap.put(C.DOB, etDOB.getString());
            hashMap.put(C.GENDER, etGender.getString());
            hashMap.put(C.user_id, user.getId());
            //hashMap.put(C.EMAIL, etEmail.getString());
            hashMap.put(C.MOBILE_NO, etPrimaryMobile.getString());

            ServiceConnection serviceConnection = new ServiceConnection();
            serviceConnection.sendToServer(C.BASE_URL_USERS, C.USER_ACCOUNT, hashMap, C.MSG, new CompleteListener() {
                @Override
                public void done(String response) {
                    disableViews();
                    Gson gson = new Gson();
                    Status status = gson.fromJson(response, Status.class);
                    if (status.getStatus().equals(C.SUCSESS)) {

                        User user = SharedPreference.getInstance(ActivityMyAccount.this).getUser(C.USER);
                        user.setFirstName(etFirstName.getString());
                        user.setLastName(etLastName.getString());
                        user.setDob(etDOB.getString());
                        user.setGender(etGender.getString());
                        user.setMobilePrimary(etPrimaryMobile.getString());

                        SharedPreference.getInstance(ActivityMyAccount.this).setUser(C.USER, user);

                        Toast.makeText(ActivityMyAccount.this, status.getMessage(), Toast.LENGTH_LONG).show();
                        showData();
                    }

                }

                @Override
                public Context getApplicationsContext() {
                    return ActivityMyAccount.this;
                }
            });

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.hideKeyBoard(this);
    }
}
