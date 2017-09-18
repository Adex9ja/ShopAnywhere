package com.example.project.shopanywhere;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.utils.Repository;

public class ItemDetailActivity extends AppCompatActivity {

    private Button btnPay;
    private TextView description,price,contact;
    private ImageView imageView;
    private String picturename;
    private ActionBar bar;
    private Item item;

    public static TextView txtcartlevel;
    private ImageButton btncart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Item Details");

        picturename = getIntent().getData().toString();
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.itemprice);
        contact = (TextView) findViewById(R.id.contact);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToCart();
            }
        });
        new Repository(this).downLoadImage(picturename,imageView);
        getItemDetails();
    }

    private void addItemToCart() {
        MainActivity.myshoppingCart.add(item);
        txtcartlevel.setText(String.valueOf(MainActivity.myshoppingCart == null ? 0 : MainActivity.myshoppingCart.size()));
        Toast.makeText(this,"Item added successfully!", Toast.LENGTH_SHORT).show();
    }

    private void getItemDetails() {
        String storename = null;
        Cursor cursor = getContentResolver().query(DatabaseContract.CONTENT_URI__ITEM_ID(picturename),
                DatabaseContract.itemProjection(),
                null,
                null,
                null);
        while (cursor.moveToNext()){
            item = new Item();
            String name = DatabaseContract.getColumnString(cursor, DatabaseContract.ItemColumns.ITEMNAME);
            storename = DatabaseContract.getColumnString(cursor, DatabaseContract.ItemColumns.ITEMSTORENAME);
            String coloritem = DatabaseContract.getColumnString(cursor, DatabaseContract.ItemColumns.COLOR);
            price.setText(DatabaseContract.getColumnString(cursor, DatabaseContract.ItemColumns.PRICE));
            description.setText(name + " ( " + coloritem + " } ");
            item.setItemname(name);item.setItemprice(price.getText().toString());item.setColor(coloritem);
            item.setPicture(picturename);item.setStorename(storename);
        }
        cursor = getContentResolver().query(DatabaseContract.CONTENT_URI_ID(storename),
                DatabaseContract.storeProjection(),
                null,
                null,
                null);
        while (cursor.moveToNext()){
            String locatiom = DatabaseContract.getColumnString(cursor, DatabaseContract.StoreColumns.LOCATION);
            String phoneno = DatabaseContract.getColumnString(cursor, DatabaseContract.StoreColumns.PHONENO);
            contact.setText(locatiom + "\n" + phoneno);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(txtcartlevel != null)
            txtcartlevel.setText(String.valueOf(MainActivity.myshoppingCart == null ? 0 : MainActivity.myshoppingCart.size()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
        txtcartlevel = (TextView) badgeLayout.findViewById(R.id.counter);
        btncart = (ImageButton) badgeLayout.findViewById(R.id.cart);
        txtcartlevel.setText(String.valueOf(MainActivity.myshoppingCart == null ? 0 : MainActivity.myshoppingCart.size()));
        return true;
    }

}
