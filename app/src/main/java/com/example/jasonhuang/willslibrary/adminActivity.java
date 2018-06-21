package com.example.jasonhuang.willslibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class adminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        String admin_name_var=getIntent().getStringExtra("first_name");
        TextView admin_name = (TextView) findViewById(R.id.admin_name);
        admin_name.setText(admin_name_var);

    }
    //This will open an action page that will list the active users and then we can click on them and view there rentals.
    public void activeUsersOnClick(View v){

    }

    public void logoutOnClick(View v){
        Intent dummy = new Intent();
        setResult(Activity.RESULT_OK,dummy);
        finish();
    }

    public void activeRentalsOnClick(View v) {

    }

    public void addItemOnClick(View v) {

    }

    public void returnItemOnClick(View v) {
        

    }
}
