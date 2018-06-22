package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class adminActiveUsers extends AppCompatActivity {

    String ip,db,un,pass;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_active_users);
        TextView activeUser = (TextView) findViewById(R.id.activeUserData);
        activeUser.setText("");
        setTitle("Will's Library's Active Users");

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here

        conn=connectionclass(un,pass,db,ip);
        try{
            Statement stmt = conn.createStatement();
            String query="SELECT username, count(*) AS NumberOfRentals FROM USERS, RENTAL, CONSUME " +
                         "WHERE username=u_username AND r_rental_id=rental_id AND date_returned IS NULL " +
                         "GROUP BY username " +
                         "ORDER BY NumberOfRentals DESC;";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                activeUser.append("User: '"+rs.getString("username")+"' is currently renting "+
                                  rs.getString("NumberOfRentals")+" item"+(rs.getInt("NumberOfRentals")==1?"":"s")+"\n");
            }
        conn.close();
        }catch (SQLException e)
        {
            Log.d("ERROR",e.getMessage());
            e.printStackTrace();
        }


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