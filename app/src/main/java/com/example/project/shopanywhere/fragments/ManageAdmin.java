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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.UpdateUser;
import com.example.project.shopanywhere.adapters.AdminStoreAdapter;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Store;
import com.example.project.shopanywhere.model.User;
import com.example.project.shopanywhere.utils.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by project on 7/27/2017.
 */

public class ManageAdmin extends Fragment implements AdapterView.OnItemLongClickListener {
    public ManageAdmin(){

    }

    private ListView list_item;
    private List<User> userList;
    private List<String> usernames;


    private void getUser() {
        User user;
        userList = new ArrayList<>();usernames = new ArrayList<>();
        Cursor cursor =  getActivity().getContentResolver().query(DatabaseContract.CONTENT_URI_USER,null,null,null,null);
        while (cursor.moveToNext()){
            user   = new User();
            user.setUsername(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERNAME));
            user.setPassword(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERPASSWORD));
            user.setUserId(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERID));
            userList.add(user); usernames.add(user.getUsername());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUser();
        View view = inflater.inflate(R.layout.fragment_manage_user,container,false);
        list_item = (ListView) view.findViewById(R.id.list_item);
        if(usernames != null && usernames.size() > 0)
            list_item.setAdapter( new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, usernames.toArray()));
        list_item.setOnItemLongClickListener(this);
        return view;
    }

    private void UpdateUser(User user) {
        Intent intent = new Intent(getActivity(),UpdateUser.class);
        intent.setData(DatabaseContract.CONTENT_URI__USER_ID(user.getUsername()));
        startActivity(intent);
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Operation");
        builder.setCancelable(true);
        String [] myarray =getContext().getResources().getStringArray(R.array.operations);
        builder.setItems(myarray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch(i){
                    case 0:
                        UpdateUser(userList.get(i));
                        break;
                    case 1:
                        new Repository(getContext()).DeleteUser(userList.get(i).getUserId());
                        break;
                }
            }
        });
        builder.show();
        return true;
    }
}
