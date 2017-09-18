package com.example.project.shopanywhere;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.shopanywhere.R;

public class Payment extends AppCompatActivity {

    private ActionBar bar;
    private EditText cardnumber,cardcvv2,cardpin,cardexpire;
    private Spinner cardtype;
    private Button btnPay;
    private ImageView imageView;
    private TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Payment");

        total = (TextView) findViewById(R.id.total);
        total.setText(getIntent().getData().toString());
        cardnumber = (EditText) findViewById(R.id.cardnumber);
        cardcvv2 = (EditText) findViewById(R.id.ccv2);
        cardpin = (EditText) findViewById(R.id.cardpin);
        cardexpire = (EditText) findViewById(R.id.cardexpire);
        cardtype = (Spinner) findViewById(R.id.cardtype);
        btnPay = (Button) findViewById(R.id.btnPay);
        imageView = (ImageView) findViewById(R.id.imageView);
        cardtype.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.cardtype) ));
        cardtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        imageView.setImageResource(R.drawable.mastercard);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.verve);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.visa);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(cardnumber.getText().toString()) || TextUtils.isEmpty(cardcvv2.getText().toString()) || TextUtils.isEmpty(cardpin.getText().toString()))
                    Toast.makeText(Payment.this,"Fill the required fields",Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(Payment.this,Confirmation.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
