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

public class OtherTab extends Fragment  {
    Connection conn;
    String un, pass, db, ip;
    private ListView lv;
    private OtherAdapter otherAdapter;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //DISK CLIENT STUFF
        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //enter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        username = getArguments().getString("username");
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.catalogue_tab_others, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvothers);
        ArrayList<Other> others = new ArrayList<Other>();


        //Getting all the information of other database
        String otherquery = "SELECT * FROM DB_A3C994_will.dbo.other;";
        String imagequery = "SELECT image FROM DB_A3C994_will.dbo.item WHERE o_other_id IS NOT NULL;";


        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(otherquery);
            Log.d("query", "Sent other query");
            Log.d("query", "Getting response from Other Query");
            int numothers = rs.getFetchSize();
            while (rs.next()) {
                Other other = new Other();
                other.setOtherID(rs.getInt(1));
                other.setOtherType(rs.getString(2));
                other.setOtherDescription(rs.getString(3));

                //This part is for the status of the other.
                String statusquery = "SELECT * FROM DB_A3C994_will.dbo.item WHERE o_other_id='"+ other.getOtherID()+"' ORDER BY status asc;";
                Statement st2 = conn.createStatement();
                ResultSet statusrs = st2.executeQuery(statusquery);

                if(statusrs.next()){
                    other.setStatus(statusrs.getString(3));
                    other.setItemNum(statusrs.getInt(1));
                }

                //Print to Log
                Log.d("query", "Type: " + other.getOtherType());
                Log.d("query", "ID: " + Integer.toString(other.getOtherID()));
                Log.d("query", "Status: " + other.getStatus());

                others.add(other);
            }
            //Uncomment this once images can be added
            /*
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(imagequery);
            Log.d("query", "Sent image query");
            index = 0;
            Log.d("query", "Getting response from Image Query");
            while(rs1.next()){
                otherimage[index] = rs1.getString(12);
                index++;
                Log.d("query", "Image URL: " + rs1.getString(12));
            }
            */
            conn.close();
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, otherstitle);
        } catch (SQLException e) {
            Log.d("query", "Query of OtherTab Failed");
        }
        otherAdapter = new OtherAdapter(getContext(), others);
        lv.setAdapter(otherAdapter);
        setupOtherSelectedListener();


        return rootView;

    }

    public void setupOtherSelectedListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View v,int position,long id)
            {
                //I no longer know what other i clicked on?
                Intent intent = new Intent(getActivity(),itemOtherActivity.class);
                intent.putExtra("other",(Serializable) otherAdapter.getItem(position));
                intent.putExtra("username",username);
                // Log.i("message",otherAdapter.getItem(position));
                getActivity().startActivityForResult(intent,1033);
            }
        });
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
