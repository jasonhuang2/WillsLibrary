package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class adminActiveRentals extends AppCompatActivity {

    String ip,db,un,pass;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_active_rentals);
        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        setTitle("Will's Library's Active Rentals");

        TextView output = (TextView) findViewById(R.id.adminActiveRentals);
        output.setText("");

        String query="(SELECT item_id,rental_id,title,username,address,due_date FROM RENTAL,CONSUME,USERS,ITEM,BOOK " +
                "WHERE date_returned IS NULL AND r_rental_id=rental_id AND RENTAL.i_item_id=item_id AND username=CONSUME.u_username AND b_isbn=isbn) " +
                "UNION " +
                "(SELECT item_id,rental_id,title,username,address,due_date FROM RENTAL,CONSUME,USERS,ITEM,DISK " +
                "WHERE date_returned IS NULL AND r_rental_id=rental_id AND RENTAL.i_item_id=item_id AND username=Consume.u_username AND d_title=title AND d_date_released=date_released) " +
                "UNION " +
                "(SELECT item_id,rental_id,OTHER.type AS title,username,address,due_date FROM RENTAL,CONSUME,USERS,ITEM,OTHER " +
                "WHERE date_returned IS NULL AND r_rental_id=rental_id AND RENTAL.i_item_id=item_id AND username=CONSUME.u_username AND o_other_id=other_id) " +
                "ORDER BY rental_id;";

        conn = connectionclass(un,pass,db,ip);
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                output.append("("+rs.getString("rental_id")+") \t User: '"+rs.getString("username")+"' \n\t\t\t Item: "+
                              rs.getString("title")+"("+rs.getString("item_id")+")"+
                              "\n\t\t\t Due: "+rs.getString("due_date")+
                              "\n\t\t\t Location: "+rs.getString("address")+"\n\n");
            }
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
