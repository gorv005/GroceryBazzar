package com.app.grocerybazzar.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.adapter.AdapterGridCategory;
import com.app.grocerybazzar.pojos.Category;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.view.ActivityContainer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubProducts extends Fragment {
    AdapterGridCategory adapterGridCategory;
    GridView gridView;
    public FragmentSubProducts() {
        // Required empty public constructor
    }
    private static final Integer[] IMAGES_BRAND= {R.drawable.axe,R.drawable.bru_logo,R.drawable.close_up,
            R.drawable.dove,R.drawable.kissan,R.drawable.knorr,R.drawable.lakme,R.drawable.lifebuoy,
            R.drawable.lipton,R.drawable.liril,R.drawable.lux,R.drawable.pears,R.drawable.pepsodent,R.drawable.ponds_logo
    ,R.drawable.rin,R.drawable.sunsilk,R.drawable.surf_excel,R.drawable.taj_logo,R.drawable.vim_logo};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_products2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView)view.findViewById(R.id.gridView1);

        ArrayList<Category> categories=new ArrayList<>();
        for(int i=0;i<IMAGES_BRAND.length;i++){
            Category category=new Category();
            category.setCategoryDrawable(IMAGES_BRAND[i]);
            categories.add(category);
        }
        //   adapterGridCategory = new AdapterGridCategory(ActivityHomeWithCategory.this, R.layout.grid_item_view, categoryInfo.getCategories());
        adapterGridCategory = new AdapterGridCategory(getActivity(), R.layout.grid_item_view, categories);

        gridView.setAdapter(adapterGridCategory);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ActivityContainer.class);
                // intent.putExtra(C.DATA,categoryInfo.getCategories().get(position).getName());

                Bundle bundle=new Bundle();
                bundle.putInt(C.SCREEN,C.FRAGMENT_CATEGORY_DETAILS);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.cartInfoView.setVisibility(View.GONE);

    }
}
