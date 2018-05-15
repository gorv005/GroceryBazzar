package com.app.grocerybazzar.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterGridCategory;
import com.app.grocerybazzar.adapter.AdapterSideMenu;
import com.app.grocerybazzar.adapter.ViewPagerAdapter;
import com.app.grocerybazzar.pojo.SectionDataModel;
import com.app.grocerybazzar.pojo.SingleItemModel;
import com.app.grocerybazzar.pojos.CartList;
import com.app.grocerybazzar.pojos.CategoryInfo;
import com.app.grocerybazzar.pojos.User;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.SharedPreference;
import com.app.grocerybazzar.util.Utils;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import serviceconnection.CompleteListener;
import serviceconnection.ServiceConnection;

public class ActivityHomeWithCategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompleteListener {
    private AdapterSideMenu adapterSideMenu;
    ListView listView;
    Utils utils;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.abc,R.drawable.abc,R.drawable.abc,R.drawable.abc};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    ArrayList<SectionDataModel> allSampleData;
    GridView gridView;
    ImageView ibCart;
    CartList cartList;
    TextView tvCartQuantity,tvName,tvContact,tvEmail,tvprofile_image;

    AdapterGridCategory adapterGridCategory;
    CategoryInfo categoryInfo = new CategoryInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_home_with_category);
        utils=new Utils();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        listView=(ListView) findViewById(R.id.lvMenuItem);
        ibCart=(ImageView)findViewById(R.id.ibCart);
        tvCartQuantity=(TextView) findViewById(R.id.tvCartQuantity);
        tvName=(TextView) findViewById(R.id.tvName);
        tvContact=(TextView) findViewById(R.id.tvContact);
        tvEmail=(TextView) findViewById(R.id.tvMail);
        tvprofile_image=(TextView) findViewById(R.id.tvprofile_image);

        ibCart.setOnClickListener(mCartClickListner);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        adapterSideMenu = new AdapterSideMenu(this, Utils.getSideMenuList());
        listView.setAdapter(adapterSideMenu);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(ActivityHomeWithCategory.this, ActivityContainer.class);
                        intent.putExtra(C.SCREEN, C.FRAGMENT_ADDRESS_LIST);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(ActivityHomeWithCategory.this, ActivityOrders.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(ActivityHomeWithCategory.this, ActivityMyAccount.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(ActivityHomeWithCategory.this, ActivityChangePassword.class);
                        startActivity(intent);
                        break;
                    case 4:
                        getDailogConfirm("Are you sure you want to logout?", "1");
                        break;
                }
            }
        });
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ActivityHomeWithCategory.this,ActivityContainer.class);
               // intent.putExtra(C.DATA,categoryInfo.getCategories().get(position).getName());

                Bundle bundle=new Bundle();
                bundle.putSerializable(C.DATA,categoryInfo.getCategories().get(position));
                bundle.putSerializable(C.CART_LIST,cartList);
                bundle.putInt(C.SCREEN,C.FRAGMENT_CATEGORY_DETAILS);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        init();
     /*   allSampleData = new ArrayList<SectionDataModel>();
        createDummyData();


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);*/
        User user = SharedPreference.getInstance(ActivityHomeWithCategory.this).getUser(C.USER);
        tvEmail.setText(user.getEmail());
        tvContact.setText(user.getMobilePrimary());
        tvName.setText(user.getFirstName()+" "+user.getLastName());
    //    tvprofile_image.setText(user.getFirstName().charAt(0)+user.getLastName().charAt(0));

     getcategoryList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartList();
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 10; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
    View.OnClickListener mCartClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ActivityHomeWithCategory.this,ActivityContainer.class);
            // intent.putExtra(C.DATA,categoryInfo.getCategories().get(position).getName());

            Bundle bundle=new Bundle();
            bundle.putInt(C.SCREEN,C.FRAGMENT_CART_LIST_FROM_HOME);
            intent.putExtras(bundle);
            startActivity(intent);        }
    };
    void getcategoryList(){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);

        HashMap<String,String> hashMap=new HashMap<>();
        serviceConnection.sendToServer(C.BASE_URL_PRODUCT,C.category,hashMap,C.MSG,ActivityHomeWithCategory.this);
    }

    void  getCartList(){
        ServiceConnection serviceConnection = new ServiceConnection();
        //    serviceConnection.makeJsonObjectRequest(C.Login,json,C.MSG,FragmentLogin.this);
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(C.USER_ID, SharedPreference.getInstance(this).getString(C.USER_ID));
        serviceConnection.sendToServerWithoutLoader(C.BASE_URL_PRODUCT,C.cart,hashMap,C.MSG,ActivityHomeWithCategory.this);
    }
    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new ViewPagerAdapter(ActivityHomeWithCategory.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DEBUG","currentPage="+currentPage);

            }
        });
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                Log.e("DEBUG","position="+position);

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    void getDailogConfirm(final String dataText, final String titleText) {
        try {
            final Dialog dialog = new Dialog(ActivityHomeWithCategory.this);
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
            cancelTv.setVisibility(View.VISIBLE);
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
                    if(titleText.equals("1")) {
                        SharedPreference.getInstance(ActivityHomeWithCategory.this).setBoolean(C.IS_LOGIN, false);
                        SharedPreference.getInstance(ActivityHomeWithCategory.this).clearData();
                        Intent intent = new Intent(ActivityHomeWithCategory.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

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
            if(response.contains("categories")) {
                categoryInfo = gson.fromJson(response, CategoryInfo.class);
                adapterGridCategory = new AdapterGridCategory(ActivityHomeWithCategory.this, R.layout.grid_item_view, categoryInfo.getCategories());
                gridView.setAdapter(adapterGridCategory);
                if (categoryInfo.getStatus().equals(C.SUCSESS)) {
                    adapterGridCategory = new AdapterGridCategory(ActivityHomeWithCategory.this, R.layout.grid_item_view, categoryInfo.getCategories());
                    gridView.setAdapter(adapterGridCategory);
                    //  adapterGridCategory.notifyDataSetChanged();
                } else {

                    getDailogConfirm(categoryInfo.getMessage(), "");

                }
            }
            else if(response.contains(C.cart)){
                cartList = gson.fromJson(response, CartList.class);
                if (cartList.getStatus().equals(C.SUCSESS)) {

                    if(cartList.getCart()!=null &&cartList.getCart().size()>0 ) {
                        tvCartQuantity.setText(""+cartList.getCart().size());
                    }
                    else {
                        tvCartQuantity.setText("");
                    }

                    //  adapterGridCategory.notifyDataSetChanged();
                } else {
                    tvCartQuantity.setText("");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Context getApplicationsContext() {
        return this;
    }
}
