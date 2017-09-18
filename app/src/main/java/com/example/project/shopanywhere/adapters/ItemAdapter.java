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
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.utils.Repository;

import java.util.ArrayList;

/**
 * Created by project on 7/24/2017.
 */


public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Item> data = new ArrayList();
    private Cursor cursor;
    public  ItemAdapter(Context context, int layoutResourceId, ArrayList data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        if(data != null)
            this.data = data;
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    public class ItemViewHolder {
        private ImageView imageView;
        private TextView iemname, itemprice;
    }

    public void swapCursor(Cursor vcursor){
        if(vcursor != null){
            data = new ArrayList<>();
            cursor = vcursor;
        }

        while (cursor.moveToNext()){
            Item item = new Item();
            item.setStorename(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMSTORENAME)));
            item.setItemname(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMNAME)));
            item.setItemprice(cursor.getString(cursor.getColumnIndexOrThrow( DatabaseContract.ItemColumns.PRICE)));
            item.setColor(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.COLOR)));
            item.setPicture(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMSPICTURE)));
            data.add(item);
        }
        notifyDataSetChanged();
    }


    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemViewHolder holder = null;
        Item currentStore = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ItemViewHolder();
            holder.iemname = (TextView) convertView.findViewById(R.id.itemname);
            holder.itemprice = (TextView) convertView.findViewById(R.id.itemprice);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        if(currentStore != null){
            holder.iemname.setText(currentStore.getItemname());
            holder.itemprice.setText("N " + currentStore.getItemprice());
            new Repository(getContext()).downLoadImage(currentStore.getPicture(),holder.imageView);
        }


        return convertView;
    }
}

