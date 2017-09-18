package com.example.project.shopanywhere.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by project on 7/27/2017.
 */

public class AdminItemAdapter extends RecyclerView.Adapter<AdminItemAdapter.StoreViewHolder2>  {
    private Context context;
    private List<Item> itemList;
    private StoreOnClickListener mListener;



    public interface StoreOnClickListener{
        public void onItemClick(Item item);
    }
    public AdminItemAdapter(Context vcontext, StoreOnClickListener vListener){
        context = vcontext;
        mListener = vListener;
    }
    @Override
    public StoreViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item_list,parent,false);
        return new StoreViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder2 holder, int position) {
        holder.storename.setText(itemList.get(position).getStorename());
        holder.itemname.setText(itemList.get(position).getItemname());
        holder.itemprice.setText(itemList.get(position).getItemprice());
        holder.itemcolor.setText(itemList.get(position).getColor());
    }
    public void swapCursor(Cursor cursor){
        if(cursor != null){
            itemList = new ArrayList<>();
            while (cursor.moveToNext()){
                Item item = new Item();
                item.setStorename(DatabaseContract.getColumnString(cursor,DatabaseContract.ItemColumns.ITEMSTORENAME));
                item.setItemname(DatabaseContract.getColumnString(cursor,DatabaseContract.ItemColumns.ITEMNAME));
                item.setItemprice(DatabaseContract.getColumnString(cursor,DatabaseContract.ItemColumns.PRICE));
                item.setColor(DatabaseContract.getColumnString(cursor,DatabaseContract.ItemColumns.COLOR));
                item.setPicture(DatabaseContract.getColumnString(cursor,DatabaseContract.ItemColumns.ITEMSPICTURE));
                itemList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public class StoreViewHolder2 extends RecyclerView.ViewHolder{
        private TextView itemname,itemprice,itemcolor,storename;

        public StoreViewHolder2(final View itemView) {
            super(itemView);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            storename = (TextView) itemView.findViewById(R.id.storename);
            itemprice = (TextView) itemView.findViewById(R.id.itemprice);
            itemcolor = (TextView) itemView.findViewById(R.id.itemcolor);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Item item = itemList.get(getPosition());
                    mListener.onItemClick(item);
                    return true;
                }
            });
        }
    }
}

