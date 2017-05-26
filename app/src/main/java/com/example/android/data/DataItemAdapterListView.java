package com.example.android.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.data.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by User on 05/05/2017.
 */

public class DataItemAdapterListView extends ArrayAdapter<DataItem> {

    private List<DataItem> mDataItems;
    private LayoutInflater mLayoutInflater;

    public DataItemAdapterListView(@NonNull Context context, @NonNull List<DataItem> objects) {
        super(context, R.layout.list_item, objects);

        mDataItems = objects;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        }

        TextView itemNameV = (TextView) convertView.findViewById(R.id.itemNameText);
        ImageView imageV = (ImageView) convertView.findViewById(R.id.imageView);

        DataItem dataItem = mDataItems.get(position);

        itemNameV.setText(dataItem.getItemName());
//        imageV.setImageResource(R.drawable.apple_pie);
        String image = dataItem.getImage();
        InputStream in = null;
        try {
             in = getContext().getAssets().open(image);
            Drawable d = Drawable.createFromStream(in, null);
            imageV.setImageDrawable(d);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return convertView;
    }
}
