package com.example.project.shopanywhere.fragments;

import android.Manifest;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.utils.Repository;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class AddStoreItem extends Fragment implements View.OnClickListener {

    private EditText itemname,price,color,picturename;
    private Spinner storename;
    private ImageButton choosepicture;
    private Button btnadd;
    private Uri selectedImageUri;
    private String selectedImageName;

    public AddStoreItem() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_store_item, container, false);
        itemname = (EditText) view.findViewById(R.id.itemname);
        storename = (Spinner) view.findViewById(R.id.storename);
        price = (EditText) view.findViewById(R.id.itemprice);
        color = (EditText) view.findViewById(R.id.itemcolor);
        picturename = (EditText) view.findViewById(R.id.picturename);
        btnadd = (Button) view.findViewById(R.id.btnAddItem);
        choosepicture = (ImageButton) view.findViewById(R.id.choosepucture);
        storename.setAdapter(new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, getStores()));
        choosepicture.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        return view;
    }

    private ArrayList <String> getStores() {
        Cursor cursor = getActivity().getContentResolver().query(DatabaseContract.CONTENT_URI,new String[]{DatabaseContract.ItemColumns.ITEMSTORENAME},null,null,null);
        ArrayList <String>items = new ArrayList();
        while (cursor.moveToNext())
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMSTORENAME)));
        return items;
    }


    @Override
    public void onClick(View view) {
        if(view == choosepicture)
            choosePicture();
        else
            addToItem();
    }

    private void addToItem() {
        if(storename.getSelectedItem() == null || TextUtils.isEmpty(itemname.getText().toString()) || TextUtils.isEmpty(price.getText().toString()))
            Toast.makeText(getContext(),"Please fill required filled",Toast.LENGTH_SHORT).show();
        else{
            Item item = new Item();
            item.setStorename(storename.getSelectedItem() == null ? "" : storename.getSelectedItem().toString());
            item.setItemname(itemname.getText().toString());
            item.setItemprice(price.getText().toString());
            item.setColor(color.getText().toString());
            item.setPicture(selectedImageName);
            new Repository(getActivity()).uploadFileItem(selectedImageUri,selectedImageName,item);
            storename.setSelection(0);itemname.setText("");color.setText(""); price.setText("");picturename.setText("");
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
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
