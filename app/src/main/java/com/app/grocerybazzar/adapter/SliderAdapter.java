package com.app.grocerybazzar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.pojos.Description;
import com.jsibbold.zoomage.ZoomageView;

import java.util.List;

/**
 * Created by gaurav.garg on 18-05-2018.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Description> descriptionList;

    public SliderAdapter(Context context, List<Description> descriptionList) {
        this.context = context;
        this.descriptionList = descriptionList;

    }

    @Override
    public int getCount() {
        return descriptionList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        ImageView img_cat = (ImageView) view.findViewById(R.id.img_cat);
        img_cat.setImageResource(descriptionList.get(position).getSubcatIcon1());

        img_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             getImage(position);
            }
        });
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }


    void getImage(int pos){
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.overlay_popup_uia);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ZoomageView img = (ZoomageView) dialog.findViewById(R.id.img);

        img.setImageResource(descriptionList.get(pos).getSubcatIcon1());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }
}