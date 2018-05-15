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
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.view.ActivityContainer;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubCategory extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSubCategory adapterSubCategory;
    LinearLayoutManager mLayoutManager;
    Category category;

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
            category = (Category)getActivity().getIntent().getExtras().getSerializable(C.DATA);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            adapterSubCategory = new AdapterSubCategory(category,category.getSubcategories(), getActivity());
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
