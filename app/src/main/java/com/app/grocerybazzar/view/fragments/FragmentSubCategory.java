package com.app.grocerybazzar.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterSubCategory;
import com.app.grocerybazzar.pojos.Category;
import com.app.grocerybazzar.pojos.Subcategory;
import com.app.grocerybazzar.view.ActivityContainer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubCategory extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSubCategory adapterSubCategory;
    LinearLayoutManager mLayoutManager;
    Category category;
    private static final Integer[] IMAGES_BRAND= {R.drawable.taj_100,R.drawable.taj_250,R.drawable.taj_leaf_tea_bags_25,
            R.drawable.taj_tea_carton_100,R.drawable.taj_tea_carton_250};
    public FragmentSubCategory() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
              String[] IMAGES_NAME= {getString(R.string.taj_100),getString(R.string.taj_250)
                      ,getString(R.string.taj_leaf_tea_bags_25),
                    getString(R.string.taj_tea_carton_100),getString(R.string.taj_tea_carton_250)};
            //category = (Category)getActivity().getIntent().getExtras().getSerializable(C.DATA);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            ArrayList<Subcategory> subcategories=new ArrayList<>();
            for (int i=0;i<IMAGES_BRAND.length;i++){
                Subcategory subcategory=new Subcategory();
                subcategory.setTitle(IMAGES_NAME[i]);
                subcategory.setSubcatIcon1(IMAGES_BRAND[i]);
                subcategories.add(subcategory);
            }
         //   adapterSubCategory = new AdapterSubCategory(category,category.getSubcategories(), getActivity());
            adapterSubCategory = new AdapterSubCategory(category,subcategories, getActivity());

            recyclerView.setAdapter(adapterSubCategory);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(category!=null && category.getName()!=null) {
            ActivityContainer.tvTitle.setText(category.getName());
        }
        else {
            ActivityContainer.tvTitle.setText(R.string.app_name);

        }
        ActivityContainer.cartInfoView.setVisibility(View.VISIBLE);

    }

}
