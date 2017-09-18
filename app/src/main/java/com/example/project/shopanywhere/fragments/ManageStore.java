package com.example.project.shopanywhere.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.UpdateItem;
import com.example.project.shopanywhere.UpdateStore;
import com.example.project.shopanywhere.adapters.AdminStoreAdapter;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.model.Store;
import com.example.project.shopanywhere.utils.Repository;

/**
 * Created by project on 7/27/2017.
 */

public class ManageStore extends Fragment implements AdminStoreAdapter.StoreOnClickListener {
    public ManageStore(){

    }

    private RecyclerView recyclerView;
    private AdminStoreAdapter mAdapter;


    private Cursor getStores() {
        return getActivity().getContentResolver().query(DatabaseContract.CONTENT_URI,DatabaseContract.storeProjection(),null,null,null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_store,container,false);
        mAdapter = new AdminStoreAdapter(getContext(), getStores(), this);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStoreClick(Cursor cursor) {
        final Store store = getStore(cursor);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Operation");
        builder.setCancelable(true);
        String [] myarray =getContext().getResources().getStringArray(R.array.operations);
        builder.setItems(myarray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch(i){
                    case 0:
                        UpdateStore(store);
                        break;
                    case 1:
                        new Repository(getContext()).DeleteStore(store.getPicture());
                        break;
                }
            }
        });
        builder.show();
    }
    private void UpdateStore(Store store) {
        Intent intent = new Intent(getActivity(),UpdateStore.class);
        intent.setData(DatabaseContract.CONTENT_URI_ID(store.getName()));
        startActivity(intent);
    }

    private Store getStore(Cursor cursor) {
        Store store = new Store();
        store.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.STORENAME)));
        store.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.DESCRIPTION)));
        store.setLocation(cursor.getString(cursor.getColumnIndexOrThrow( DatabaseContract.StoreColumns.LOCATION)));
        store.setPhoneno(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.PHONENO)));
        store.setPicture(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.PICTURE)));
        return store;
    }
}
