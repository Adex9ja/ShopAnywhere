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
import com.example.project.shopanywhere.adapters.AdminItemAdapter;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.utils.Repository;

/**
 * Created by project on 7/27/2017.
 */

public class ManageItem extends Fragment implements AdminItemAdapter.StoreOnClickListener {
    private RecyclerView recyclerView;
    private AdminItemAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_item,container,false);
        mAdapter = new AdminItemAdapter(getContext(), this);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private Cursor getItems() {
        return getActivity().getContentResolver().query(DatabaseContract.CONTENT_URI_ITEM,DatabaseContract.itemProjection(),null,null,null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.swapCursor(getItems());
    }

    @Override
    public void onItemClick(final Item item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Operation");
        builder.setCancelable(true);
        String [] myarray =getContext().getResources().getStringArray(R.array.operations);
        builder.setItems(myarray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch(i){
                    case 0:
                        UpdateItem(item);
                        break;
                    case 1:
                        new Repository(getContext()).DeleteItem(item.getPicture());
                        break;
                }
            }
        });
        builder.show();

    }

    private void UpdateItem(Item item) {
       Intent intent = new Intent(getActivity(),UpdateItem.class);
        intent.setData(DatabaseContract.CONTENT_URI__ITEM_ID(item.getPicture()));
        startActivity(intent);

    }
}
