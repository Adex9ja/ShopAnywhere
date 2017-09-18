package com.example.project.shopanywhere.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Store;
import com.example.project.shopanywhere.utils.Repository;

import java.util.ArrayList;

/**
 * Created by project on 7/15/2017.
 */

public class StoreAdapter extends ArrayAdapter<Store> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Store> data = new ArrayList();
    private Cursor cursor;
    public  StoreAdapter(Context context, int layoutResourceId, ArrayList data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        if(data != null)
        this.data = data;
    }

    @Nullable
    @Override
    public Store getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    public class StoreViewHolder {
        private ImageView imageView;
        private TextView textView;
    }

    public void swapCursor(Cursor vcursor){
        if(vcursor != null){
            data = new ArrayList<>();
            cursor = vcursor;
        }

        while (cursor.moveToNext()){
            Store store = new Store();
            store.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.STORENAME)));
            store.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.DESCRIPTION)));
            store.setLocation(cursor.getString(cursor.getColumnIndexOrThrow( DatabaseContract.StoreColumns.LOCATION)));
            store.setPhoneno(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.PHONENO)));
            store.setPicture(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.PICTURE)));
            data.add(store);
        }
        notifyDataSetChanged();
    }


    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        StoreViewHolder holder = null;
        Store currentStore = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new StoreViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.description);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (StoreViewHolder) convertView.getTag();;
        }

        if(currentStore != null){
            holder.textView.setText(currentStore.getName());
            new Repository(getContext()).downLoadImage(currentStore.getPicture(),holder.imageView);
        }


        return convertView;
    }
}

