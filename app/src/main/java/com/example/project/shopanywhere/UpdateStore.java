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
import com.example.project.shopanywhere.model.Store;
import com.example.project.shopanywhere.utils.Repository;

public class UpdateStore extends AppCompatActivity implements View.OnClickListener {
    private EditText storename, storelocation, phoneno, description;
    private Button btnUpdate;
    private Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Uri uri = getIntent().getData();
        store = getStore(uri);
        storename = (EditText) findViewById(R.id.storename);
        storelocation = (EditText) findViewById(R.id.storelocation);
        phoneno = (EditText) findViewById(R.id.phoneno);
        description = (EditText) findViewById(R.id.description);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        PopulateSrore();
    }

    private void PopulateSrore() {
        storename.setText(store.getName().toString());
        storelocation.setText(store.getLocation());
        phoneno.setText(store.getPhoneno());
        description.setText(store.getDescription());
    }

    private Store getStore(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, DatabaseContract.itemProjection(),null,null,null);
        Store store = new Store();
        while (cursor.moveToNext()){
            store.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.STORENAME)));
            store.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.DESCRIPTION)));
            store.setLocation(cursor.getString(cursor.getColumnIndexOrThrow( DatabaseContract.StoreColumns.LOCATION)));
            store.setPhoneno(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.PHONENO)));
            store.setPicture(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StoreColumns.PICTURE)));
        }
        return  store;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        store.setName(storename.getText().toString());
        store.setDescription(description.getText().toString());
        store.setLocation(storelocation.getText().toString());
        store.setPhoneno(phoneno.getText().toString());
        UpdateStore();
    }
    private void UpdateStore(){
        new Repository(this).UpdateStore(store);
    }
}
