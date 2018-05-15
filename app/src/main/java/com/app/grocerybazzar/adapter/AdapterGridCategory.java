package com.app.grocerybazzar.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.grocerybazzar.R;
import com.app.grocerybazzar.imagecaching.ImageLoader;
import com.app.grocerybazzar.pojos.Category;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gaurav.garg on 02-11-2017.
 */

public class AdapterGridCategory extends ArrayAdapter<Category> {
    Context context;
    int layoutResourceId;
    ImageLoader imageLoader;
    List<Category> data = new ArrayList<Category>();

    public AdapterGridCategory(Context context, int layoutResourceId,
                                 List<Category> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        imageLoader=new ImageLoader(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item_view, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.tv_cat);
            holder.imageItem = (ImageView) row.findViewById(R.id.img_cat);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        Category item = data.get(position);
        holder.txtTitle.setText(item.getName());
        imageLoader.DisplayImage(item.getCategoryIcon(),holder.imageItem);
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }
}
