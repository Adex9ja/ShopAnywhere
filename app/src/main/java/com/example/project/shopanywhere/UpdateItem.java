package com.example.project.shopanywhere;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.utils.Repository;

public class UpdateItem extends AppCompatActivity implements View.OnClickListener {

    private EditText itemname,price,color,storename;
    private Button btnUpdate;
    private  Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Uri uri = getIntent().getData();
        item = getItem(uri);
        itemname = (EditText) findViewById(R.id.itemname);
        storename = (EditText) findViewById(R.id.storename);
        price = (EditText) findViewById(R.id.itemprice);
        color = (EditText) findViewById(R.id.itemcolor);
        btnUpdate = (Button) findViewById(R.id.btnUpdateItem);
        btnUpdate.setOnClickListener(this);
        PopulateItems();


    }

    private void PopulateItems() {
        itemname.setText(item.getItemname());
        price.setText(item.getItemprice());
        color.setText(item.getColor());
        storename.setText(item.getStorename());
    }

    private Item getItem(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, DatabaseContract.itemProjection(),null,null,null);
        Item item = new Item();
        while (cursor.moveToNext()){
            item.setStorename(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMSTORENAME)));
            item.setItemname(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMNAME)));
            item.setItemprice(cursor.getString(cursor.getColumnIndexOrThrow( DatabaseContract.ItemColumns.PRICE)));
            item.setColor(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.COLOR)));
            item.setPicture(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ItemColumns.ITEMSPICTURE)));
        }
        return  item;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void UpdateItem(){
        new Repository(this).UpdateItem(item);
    }

    @Override
    public void onClick(View view) {
        item.setStorename(storename.getText().toString());
        item.setColor(color.getText().toString());
        item.setItemprice(price.getText().toString());
        item.setItemname(itemname.getText().toString());
        UpdateItem();
    }
}

