package com.app.grocerybazzar.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.SliderAdapter;
import com.app.grocerybazzar.pojos.Category;
import com.app.grocerybazzar.pojos.Description;
import com.app.grocerybazzar.pojos.Subcategory;
import com.app.grocerybazzar.util.C;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDescription extends Fragment {

    ViewPager viewPager;
    TabLayout indicator;
    Subcategory subcategory;

    TextView tvTitle,tvDesc;
    public FragmentDescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            subcategory = (Subcategory) bundle.getSerializable(C.SUB_CAT_DETAIL);

        }
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        indicator=(TabLayout)view.findViewById(R.id.indicator);
        tvTitle=(TextView) view.findViewById(R.id.tv_cat);

        tvDesc=(TextView) view.findViewById(R.id.tvDesc);


        tvTitle.setText(subcategory.getTitle());
        List<Description> descriptions=new ArrayList<>();

        Description description=new Description();
        description.setSubcatIcon1(R.drawable.taj_250);
        descriptions.add(description);


        Description description1=new Description();
        description1.setSubcatIcon1(R.drawable.taj_desc);
        descriptions.add(description1);
        tvDesc.setText("The Perfect combination of strength & flavour making a great cup of tea");

        viewPager.setAdapter(new SliderAdapter(getActivity(),descriptions));
        indicator.setupWithViewPager(viewPager, true);

    }
}
