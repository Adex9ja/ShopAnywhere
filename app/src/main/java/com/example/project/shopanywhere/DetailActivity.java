package com.example.project.shopanywhere;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.shopanywhere.adapters.ItemAdapter;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.R;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private String storename;
    private ItemAdapter mAdapter;

    public static TextView txtcartlevel;
    private ImageButton btncart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar bar = getSupportActionBar();

        storename = getIntent().getData().toString();

        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(storename);

        GridView gridview = (GridView) findViewById(R.id.list_item);
        mAdapter = new ItemAdapter(this,R.layout.list_item_item,null);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DetailActivity.this,ItemDetailActivity.class);
                intent.setData(Uri.parse(mAdapter.getItem(i).getPicture()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.swapCursor(getAllItems());
        if(txtcartlevel != null)
            txtcartlevel.setText(String.valueOf(MainActivity.myshoppingCart == null ? 0 : MainActivity.myshoppingCart.size()));
    }

    private Cursor getAllItems() {
        return getContentResolver().query(DatabaseContract.CONTENT_URI_ITEM,
                DatabaseContract.itemProjection(),
                DatabaseContract.ItemColumns.ITEMSTORENAME + " = ? ",
                new String[]{storename},null);
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
