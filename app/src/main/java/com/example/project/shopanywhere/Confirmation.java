package com.example.project.shopanywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project.shopanywhere.R;

import java.util.ArrayList;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.myshoppingCart = new ArrayList<>();
        setContentView(R.layout.activity_confirmation);
        getSupportActionBar().setTitle("Payment Confirmation");
        Button  btnContnue = (Button) findViewById(R.id.btnContnue);
        btnContnue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
