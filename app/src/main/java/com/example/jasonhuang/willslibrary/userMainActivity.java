package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userMainActivity extends AppCompatActivity {

    Connection conn;
    String un, pass, db, ip;
    private TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Any layout that requires access to the database must have this
        conn = connectionclass(un, pass, db, ip);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_activity);

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here

        String first_name = getIntent().getExtras().getString("first_name");

        nameText = (TextView)findViewById(R.id.nameText);
        nameText.setText(first_name);

        //This is for the back button but I dont think the user's main page needs a back button back to the login page
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
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
    //Going to the Catalogue page
    public void catalogueClick(View v){
        Intent catalogueI = new Intent(this, catalogueMainActivity.class);
        startActivity(catalogueI);
    }
    //Going to the My Rentals page
    public void my_rental_Click(View v){
        Intent myrentalI = new Intent(this, myrentalMainActivity.class);
        startActivity(myrentalI);
    }
    //Going to the My Fines page
    public void my_fines_Click(View v){
        Intent myfinesI = new Intent(this, myfinesMainActivity.class);
        startActivity(myfinesI);
    }
}
