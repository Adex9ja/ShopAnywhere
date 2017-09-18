package com.example.project.shopanywhere;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.shopanywhere.model.Item;

import java.util.List;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText surname, othernames, occupation, phoneno, address;
    private TextView total;
    private Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Customer Information");
        btnContinue = (Button) findViewById(R.id.btnContinue);
        surname = (EditText) findViewById(R.id.tsurname);
        othernames = (EditText) findViewById(R.id.tothernames);
        occupation = (EditText) findViewById(R.id.toccup);
        phoneno = (EditText) findViewById(R.id.tphone);
        address = (EditText) findViewById(R.id.taddress);
        total = (TextView) findViewById(R.id.total);
        total.setText("Total: " + getTotal(MainActivity.myshoppingCart));
        btnContinue.setOnClickListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private double getTotal(List<Item> myshoppingCart) {
        double totalamount = 0;
        for(Item item : myshoppingCart){
            totalamount += covertToDouble(item.getItemprice());
        }
        return totalamount;
    }

    private double covertToDouble(String itemprice) {
        try {
            double temp = Double.parseDouble(itemprice);
            return temp;
        }catch (Exception e){
            return  0;
        }
    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(phoneno.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(surname.getText().toString()) || TextUtils.isEmpty(othernames.getText().toString()))
            Toast.makeText(this,"Fill required fields!",Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(this,Payment.class);
            intent.setData(Uri.parse(total.getText().toString()));
            startActivity(intent);
            finish();
        }

    }
}
