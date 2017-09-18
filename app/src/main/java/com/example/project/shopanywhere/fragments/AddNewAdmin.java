package com.example.project.shopanywhere.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.User;
import com.example.project.shopanywhere.utils.Repository;


public class AddNewAdmin extends Fragment implements View.OnClickListener {

    private EditText tuser,tpass,tpassconfirm;
    private Button btnContinue;



    public AddNewAdmin() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_admin, container, false);
        tuser = (EditText) view.findViewById(R.id.tuser);
        tpass = (EditText) view.findViewById(R.id.tpass);
        tpassconfirm = (EditText) view.findViewById(R.id.tpassconfirm);
        btnContinue = (Button) view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        verifyFields();
    }

    private void verifyFields() {
        if(TextUtils.isEmpty(tuser.getText().toString()) || TextUtils.isEmpty(tpass.getText().toString()) || TextUtils.isEmpty(tpassconfirm.getText().toString()))
            Toast.makeText(getContext(),"Fill required filled!",Toast.LENGTH_SHORT).show();
        else{
               if(!tpassconfirm.getText().toString().equals(tpass.getText().toString())){
                   Toast.makeText(getContext(),"Password mismatch!",Toast.LENGTH_SHORT).show();
                   return;
               }else{
                    RegisterUser();
               }
        }

    }

    private void RegisterUser() {
        User user = new User();
        user.setUsername(tuser.getText().toString());
        user.setPassword(tpass.getText().toString());
        Cursor cursor = getActivity().getContentResolver().query(DatabaseContract.CONTENT_URI__USER_ID(tuser.getText().toString()),
                null,null,null,null);
        int count = cursor.getCount();
        if(cursor != null && cursor.getCount() > 0)
            Toast.makeText(getActivity(), "Username already exist!", Toast.LENGTH_SHORT).show();
        else
            new Repository(getContext()).AddNewAdmin(user);
        tuser.setText("");tpass.setText("");tpassconfirm.setText("");
    }


}
