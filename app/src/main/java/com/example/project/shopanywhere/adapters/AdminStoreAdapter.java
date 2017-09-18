package com.example.project.shopanywhere.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.model.Store;

/**
 * Created by project on 7/27/2017.
 */

public class AdminStoreAdapter extends RecyclerView.Adapter<AdminStoreAdapter.StoreViewHolder2>  {
    private Context context;
    private Cursor cursor;
    private StoreOnClickListener mListener;



    public interface StoreOnClickListener{
        public void onStoreClick(Cursor cursor);
    }
    public AdminStoreAdapter(Context vcontext, Cursor vcursor, StoreOnClickListener vListener){
        context = vcontext;
        cursor = vcursor;
        mListener = vListener;
    }
    @Override
    public StoreViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_store_list,parent,false);
        return new StoreViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder2 holder, int position) {
        cursor.moveToPosition(position);
        holder.storename.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.StoreColumns.STORENAME));
        holder.description.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.StoreColumns.DESCRIPTION));
        holder.phoneno.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.StoreColumns.PHONENO));
        holder.storelocation.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.StoreColumns.LOCATION));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class StoreViewHolder2 extends RecyclerView.ViewHolder{

        private TextView storename, storelocation, phoneno, description;
        public StoreViewHolder2(View itemView) {
            super(itemView);
            storename = (TextView) itemView.findViewById(R.id.storename);
            description = (TextView) itemView.findViewById(R.id.description);
            storelocation = (TextView) itemView.findViewById(R.id.storelocation);
            phoneno = (TextView) itemView.findViewById(R.id.phoneno);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    cursor.moveToPosition(getPosition());
                    mListener.onStoreClick(cursor);
                    return true;
                }
            });
        }
    }
}
