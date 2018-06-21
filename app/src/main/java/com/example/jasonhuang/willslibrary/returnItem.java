package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class returnItem extends AppCompatActivity {

    String ip,db,un,pass;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_item);

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here

    }


    public void submitOnClick(View v)
    {
        EditText itemIdInputBox = (EditText) findViewById(R.id.itemIDInput);
        String itemid=itemIdInputBox.getText().toString();
        String query ="SELECT * FROM RENTAL" +
                      " WHERE i_item_id="+itemid+" AND date_returned IS NULL;";
        conn = connectionclass(un,pass,db,ip);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs=stmt.executeQuery(query);
            //This book is ripe to be returned.
            if(rs.next())
            {
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");

                int rental_id = rs.getInt("rental_id");
                int item_number = rs.getInt("i_item_id");

                //update date_returned of that unique rental_id.
                query="UPDATE RENTAL SET date_returned='"+sf.format(Calendar.getInstance().getTime())+"' WHERE rental_id="+rental_id+";";
                stmt.execute(query);


                //update item_status.
                query="UPDATE ITEM SET status='1_In_Store' WHERE item_id="+item_number+";";
                stmt.execute(query);

                //update item location
                query="UPDATE LOCATION SET address='Wearhouse' WHERE i_item_id="+item_number+";";
                stmt.execute(query);

            }else
            {
                TextView errorBox = (TextView) findViewById(R.id.returnErrorBox);
                errorBox.setText("Item: "+itemid+" is not currently being rented.");
                return;
            }
            conn.close();
        }catch(SQLException e)
        {
            Log.d("error",e.getMessage());
            e.printStackTrace();
        }
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

