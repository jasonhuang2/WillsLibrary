package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class myrentalMainActivity extends AppCompatActivity {

    private String username;
    Connection conn;
    String un, pass, db, ip;
    private ListView lv;
    private RentalAdapter rentalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrental_activity);
        lv = (ListView) findViewById(R.id.rentallv);
        ArrayList<Rental> aRentals = new ArrayList<Rental>();
        setTitle("My Rentals");
        username=getIntent().getStringExtra("username");

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //enter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        //Added check to see if this was returned and added the rental_id=r_rental_id check.
        String rentalquery = "SELECT i_item_id, due_date, cost_per_day_overdue, type FROM DB_A3C994_will.dbo.rental, DB_A3C994_will.dbo.consume, DB_A3C994_will.dbo.item WHERE date_returned IS NULL AND item_id = i_item_id AND r_rental_id=rental_id AND u_username = '" + username + "';";
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(rentalquery);
            Log.d("query", "Sent General Rental Query");
            while(rs.next()){
                Log.d("query", "Received one line of input");
                Rental rental = new Rental();
                rental.setItemID(rs.getInt(1));
                rental.setDueDate(rs.getString(2));
                rental.setOverDue(rs.getDouble(3));
                String type = rs.getString(4);

                if(type.equals("Book")){
                    Log.d("query", "Sent BOOK Rental Query");
                    String booktitlequery = "SELECT title FROM DB_A3C994_will.dbo.book, DB_A3C994_will.dbo.item WHERE isbn = b_isbn AND item_id = " + rental.getItemID() + ";";
                    Statement st2 = conn.createStatement();
                    ResultSet bookrs = st2.executeQuery(booktitlequery);
                    while(bookrs.next()){
                        rental.setItemTitle(bookrs.getString(1));
                    }
                }
                else if(type.equals("Disk")){
                    String disktitlequery = "SELECT title FROM DB_A3C994_will.dbo.disk, DB_A3C994_will.dbo.item WHERE d_title = title AND item_id = "+ rental.getItemID() + ";";
                    Statement st3 = conn.createStatement();
                    ResultSet diskrs = st3.executeQuery(disktitlequery);

                    while(diskrs.next()){
                        rental.setItemTitle(diskrs.getString(1));
                    }
                }
                else if(type.equals("Other")){
                    String othertitlequery = "SELECT Other.type FROM DB_A3C994_will.dbo.other, DB_A3C994_will.dbo.item WHERE o_other_id = other_id AND item_id = "+ rental.getItemID() + ";";
                    Statement st4 = conn.createStatement();
                    ResultSet otherrs = st4.executeQuery(othertitlequery);

                    while(otherrs.next()){
                        rental.setItemTitle(otherrs.getString(1));
                    }
                }
               aRentals.add(rental);
            }
            conn.close();
            Log.d("query", "Connection Closed");
        } catch(SQLException e){
            Log.d("query", "Query of Rentals Failed");
        }
        rentalAdapter = new RentalAdapter(this, aRentals);
        lv.setAdapter(rentalAdapter);


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
