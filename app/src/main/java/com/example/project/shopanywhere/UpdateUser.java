package com.example.project.shopanywhere;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.User;
import com.example.project.shopanywhere.utils.Repository;

public class UpdateUser extends AppCompatActivity implements View.OnClickListener {
    private EditText tuser,tpass,tpassconfirm;
    private Button btnContinue;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_new_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Uri uri = getIntent().getData();
        getUser(uri);
        tuser = (EditText) findViewById(R.id.tuser);
        tpass = (EditText) findViewById(R.id.tpass);
        tpassconfirm = (EditText) findViewById(R.id.tpassconfirm);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        PopulateUser();

    }

    private void PopulateUser() {
        tuser.setText(user.getUsername());
        tpass.setText(user.getPassword());
    }

    private void getUser(Uri uri) {
        Cursor cursor =  getContentResolver().query(uri,null,null,null,null);
        while (cursor.moveToNext()){
            user = new User();
            user.setUsername(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERNAME));
            user.setPassword(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERPASSWORD));
            user.setUserId(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERID));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(tuser.getText().toString()) || TextUtils.isEmpty(tpass.getText().toString()) ||  !tpass.getText().toString().equals(tpassconfirm.getText().toString()))
            Toast.makeText(this, "Password mis-match!", Toast.LENGTH_SHORT);
        else{
            user.setPassword(tpass.getText().toString());
            user.setPassword(tpass.getText().toString());
            new Repository(this).UpdateUser(user);
        }

    }
}
