package com.app.grocerybazzar.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.view.fragments.FragmentForgotPassword;
import com.app.grocerybazzar.view.fragments.FragmentLogin;
import com.app.grocerybazzar.view.fragments.FragmentRegister;
import com.app.grocerybazzar.view.fragments.FragmentSplash;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        fragmnetLoader(C.FRAGMENT_SPLASH,null);
    }


    public void fragmnetLoader(int fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case C.FRAGMENT_SPLASH:
                getSupportActionBar().hide();
                fragment = new FragmentSplash();
                fragmentTransaction.replace(R.id.container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_LOGIN:
                getSupportActionBar().hide();
                fragment = new FragmentLogin();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_LOGIN);
                break;
            case C.FRAGMENT_SIGNUP:
                getSupportActionBar().show();

                tvTitle.setText("Register");
                fragment = new FragmentRegister();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SIGNUP);
                break;
            case C.FRAGMENT_FORGOT:
                getSupportActionBar().hide();
                fragment = new FragmentForgotPassword();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_FORGOT);
                break;
        }
        fragment.setArguments(bundle);

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    @Override
    public void onBackPressed() {

        if(fragment instanceof FragmentRegister){
            getSupportActionBar().hide();
        }
        getSupportFragmentManager().executePendingTransactions();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

//            Log.e("CountPop", getSupportFragmentManager().getBackStackEntryCount() + "");

            int fragmentCount = getSupportFragmentManager().getBackStackEntryCount();
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(fragmentCount - 2);
            String fragmentTag = backEntry.getName();

            getSupportFragmentManager().popBackStack();

            getSupportFragmentManager().executePendingTransactions();


        } else {
            finish();
        }
    }
}
