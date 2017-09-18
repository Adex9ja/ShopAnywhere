package com.example.project.shopanywhere;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        username = (EditText) findViewById(R.id.tuser);
        password = (EditText) findViewById(R.id.tpass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = getUsers();
                if(users != null && users.size() > 0){
                    if(IsPasswordCorrect(users)){
                        startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Incorrect password!",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(LoginActivity.this,"Username does not exist!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean IsPasswordCorrect(List<User> users) {
        for (User user : users){
            if(user.getPassword().equals(password.getText().toString())){
               return true;
            }
        }
        return  false;
    }

    private List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        User user;
        Cursor cursor =  getContentResolver().query(DatabaseContract.CONTENT_URI__USER_ID(username.getText().toString()),null,null,null,null);
        while (cursor.moveToNext()){
            user = new User();
            user.setUsername(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERNAME));
            user.setPassword(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERPASSWORD));
            user.setUserId(DatabaseContract.getColumnString(cursor, DatabaseContract.UserColumns.USERID));
            userList.add(user);
        }
        return userList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
