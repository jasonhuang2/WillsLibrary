package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class userCreationActivity extends AppCompatActivity {

    Connection conn;
    String un, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        ////Set from constants.
        ip = DBlocationConstants.getip();
        db = DBlocationConstants.getdb();
        un = DBlocationConstants.getun();
        pass = DBlocationConstants.getps();
        setTitle("User Creation");
    }

    public void onSubmit(View v) {
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        //Calendar myCalender = new C;
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        TextView errorBox = findViewById(R.id.errorBox);
        EditText firstName = findViewById(R.id.first_name);
        EditText lastName = findViewById(R.id.last_name);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText password_confirm = findViewById(R.id.passwordConfirm);
        EditText address= findViewById(R.id.address);
        errorBox.setText("");
        if(firstName.getText().toString().equals("")||
                lastName.getText().toString().equals("")||
                username.getText().toString().equals("")||
                address.getText().toString().equals("")) {
            errorBox.setText("Please make sure all field are not empty");
            return;
        }
        if(!password.getText().toString().equals(password_confirm.getText().toString())){
            errorBox.setText("Make sure passwords match");
            return;
        }
        String query ="SELECT username FROM DB_A3C994_will.dbo.users WHERE username='" + username.getText().toString()+"';";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //errorBox.setText(sm.format(Calendar.getInstance().getTime()));
            if(rs.next()) {
                errorBox.setText("Please select a new username");
                return;
            }
            query = "INSERT INTO DB_A3C994_will.dbo.users (username,password,creation_date,address,first_name,last_name,amount_of_money,a_username) VALUES ('" + username.getText().toString() + "','"
                    + password.getText().toString() + "','" +
                    sm.format(Calendar.getInstance().getTime()) + "','" +
                    address.getText().toString() + "','" +
                    firstName.getText().toString() + "','" +
                    lastName.getText().toString() + "'," +
                    0.00 + "," +
                    "'ADMIN');";
            //errorBox.setText(query);
            stmt.execute(query);
            //INSERT INTO USERS (username,password,creation_date,address,first_name,last_name,amount_of_money,a_username) VALUES ('h','','2018-06-13','p','p','p',0.00,'ADMIN');
            conn.close();
        }
        catch(SQLException e)
        {
            //errorBox.setText(e.getMessage());
            e.printStackTrace();
            errorBox.setText("Please Check your network connection");
            return;
        }
        //So I don't know why this is not working
        //I copied this right from you guys.
        /*Intent mainActivity = new Intent(this, MainActivity.class);
        String usernameInputBox = username.getText().toString();
        String passwordInputBox = "";
        String loginTextView2 = "Enter your password to continue";
        mainActivity.putExtra("usernameInputBox", usernameInputBox);
        mainActivity.putExtra("passwordInputBox",passwordInputBox);
        mainActivity.putExtra("loginTextView2",loginTextView2);
        startActivity(mainActivity);*/
        Intent intent = new Intent();
        intent.putExtra("usernameInputBox",username.getText().toString());
        intent.putExtra("passwordInputBox","");
        intent.putExtra("loginTextView2","Please login to continue");
        setResult(Activity.RESULT_OK,intent);
        finish();

    }



    //connection class
    @SuppressLint("NewApi")
    public Connection connectionclass (String user, String pass, String db, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + ";" + "databseName=" + db + ";user=" + user + ";password=" + pass + ";";
            connection = DriverManager.getConnection(connectionURL);

        }catch(Exception e){
            Log.e("Error: ", e.getMessage());
        }

        return connection;
    }
}
