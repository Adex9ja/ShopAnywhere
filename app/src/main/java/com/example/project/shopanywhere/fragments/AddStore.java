package com.example.project.shopanywhere.fragments;


import android.Manifest;
import com.example.project.shopanywhere.R;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.project.shopanywhere.model.Store;
import com.example.project.shopanywhere.utils.Repository;

import static android.app.Activity.RESULT_OK;


public class AddStore extends Fragment implements View.OnClickListener {
    private EditText storename, storelocation, phoneno, description, picturename;
    private ImageButton choosepicture;
    private Button btnadd;
    private Uri selectedImageUri;
    private String selectedImageName;



    public AddStore() {
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
        View view = inflater.inflate(R.layout.fragment_add_store, container, false);
        storename = (EditText) view.findViewById(R.id.storename);
        storelocation = (EditText) view.findViewById(R.id.storelocation);
        phoneno = (EditText) view.findViewById(R.id.phoneno);
        description = (EditText) view.findViewById(R.id.description);
        picturename = (EditText) view.findViewById(R.id.picturename);
        btnadd = (Button) view.findViewById(R.id.btnAddStore);
        choosepicture = (ImageButton) view.findViewById(R.id.choosepucture);
        choosepicture.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        if(view == choosepicture)
            choosePicture();
        else
            addToStore();
    }

    private void addToStore() {
        if(TextUtils.isEmpty(storename.getText().toString()) || TextUtils.isEmpty(description.getText().toString()) )
            Toast.makeText(getContext(),"Fill required filled!",Toast.LENGTH_SHORT).show();
        else{
                /*ContentValues values = new ContentValues();
                values.put(DatabaseContract.StoreColumns.DESCRIPTION,description.getText().toString());
                values.put(DatabaseContract.StoreColumns.LOCATION,storelocation.getText().toString());
                values.put(DatabaseContract.StoreColumns.STORENAME,storename.getText().toString());
                values.put(DatabaseContract.StoreColumns.PHONENO,phoneno.getText().toString());
                values.put(DatabaseContract.StoreColumns.PICTURE, selectedImageName);*/
            Store store = new Store();
            store.setDescription(description.getText().toString());
            store.setLocation(storelocation.getText().toString());
            store.setName(storename.getText().toString());
            store.setPhoneno(phoneno.getText().toString());
            store.setPicture(selectedImageName);
                new Repository(getActivity()).uploadFileStore(selectedImageUri,selectedImageName,store);
                description.setText("");storelocation.setText("");storename.setText(""); picturename.setText("");phoneno.setText("");

        }

    }

    private void choosePicture() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                lauchchooser();
            else
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            lauchchooser();
    }

    private void lauchchooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            selectedImageName = Repository.getPixName();
            picturename.setText(getName(selectedImageUri));
        }
    }


    public String getName(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DISPLAY_NAME };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
