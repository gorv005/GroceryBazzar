package com.app.grocerybazzar.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.CartList;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.view.fragments.FragmentAddAddress;
import com.app.grocerybazzar.view.fragments.FragmentAddressList;
import com.app.grocerybazzar.view.fragments.FragmentAddressListForOrder;
import com.app.grocerybazzar.view.fragments.FragmentCartList;
import com.app.grocerybazzar.view.fragments.FragmentDescription;
import com.app.grocerybazzar.view.fragments.FragmentOrderConfirmationScreen;
import com.app.grocerybazzar.view.fragments.FragmentProductInfo;
import com.app.grocerybazzar.view.fragments.FragmentSubCategory;
import com.app.grocerybazzar.view.fragments.FragmentSubProducts;
import com.app.grocerybazzar.view.fragments.FragmentThankuScreen;
import com.app.grocerybazzar.view.fragments.FragmentViewEditAddress;

import java.util.List;

public class ActivityContainer extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    ImageView ibCart;
    public  static View cartInfoView;

    CartList cartList;
  public static   TextView tvCartQuantity,tvTitle;

    int screen=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ibCart=(ImageView)findViewById(R.id.ibCart);
        tvCartQuantity=(TextView) findViewById(R.id.tvCartQuantity);
        tvTitle=(TextView) findViewById(R.id.tvTitle);
        cartInfoView=(View) findViewById(R.id.cartInfoView);

        ibCart.setOnClickListener(mCartClickListner);
        try {
            screen = getIntent().getIntExtra(C.SCREEN,-1);
            cartList = (CartList)getIntent().getSerializableExtra(C.CART_LIST);
            if(cartList!=null && cartList.getCart()!=null && cartList.getCart().size()>0){
                tvCartQuantity.setText(""+cartList.getCart().size());
            }
            else {
                tvCartQuantity.setText("");

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        fragmnetLoader(screen,null);

    }

    View.OnClickListener mCartClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        fragmnetLoader(C.FRAGMENT_CART_LIST,null);
        }
    };

    public void fragmnetLoader(int fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case C.FRAGMENT_ADDRESS_LIST:
              //  getSupportActionBar().hide();
                fragment = new FragmentAddressList();
                fragmentTransaction.replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_VIEW_EDIT_ADDRESS:
                //getSupportActionBar().hide();
                fragment = new FragmentViewEditAddress();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_ADD_ADDRESS:
              //  getSupportActionBar().hide();
                fragment = new FragmentAddAddress();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_CATEGORY_DETAILS:
             //   getSupportActionBar().hide();
                fragment = new FragmentSubCategory();
                fragmentTransaction.replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_SUB_CAT_DETAILS:
                //   getSupportActionBar().hide();
                fragment = new FragmentProductInfo();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_CART_LIST:
                //   getSupportActionBar().hide();
                fragment = new FragmentCartList();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;

            case C.FRAGMENT_DESCRIPTION:
                //   getSupportActionBar().hide();
                fragment = new FragmentDescription();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_SUB_PRODUCT:
                //   getSupportActionBar().hide();
                fragment = new FragmentSubProducts();
                fragmentTransaction.replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_CART_LIST_FROM_HOME:
                //   getSupportActionBar().hide();
                fragment = new FragmentCartList();
                fragmentTransaction.replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_ADDRESS_LIST_FOR_ORDER:
                //   getSupportActionBar().hide();
                fragment = new FragmentAddressListForOrder();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_ORDER_LIST_CONFIRM:
                //   getSupportActionBar().hide();
                fragment = new FragmentOrderConfirmationScreen();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
            case C.FRAGMENT_THANKU_SCREEN:
                //   getSupportActionBar().hide();
                fragment = new FragmentThankuScreen();
                fragmentTransaction.addToBackStack(null).replace(R.id.child_container, fragment);
                // fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SPLASH);
                break;
        }
        fragment.setArguments(bundle);

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    public  void getAddToCartMethod(int pos){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentProductInfo) {
            ((FragmentProductInfo) fragment).addTocartProduct(pos);
        }
    }
    public  void deleteCartItem(int pos){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentCartList) {
            ((FragmentCartList) fragment).deleteItemFromCart(pos);
        }
    }
    public  void editCartItem(int pos){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentCartList) {
            ((FragmentCartList) fragment).editCartItem(pos);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = ActivityContainer.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentThankuScreen) {
            Intent intent = new Intent(this, ActivityHomeWithCategory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
