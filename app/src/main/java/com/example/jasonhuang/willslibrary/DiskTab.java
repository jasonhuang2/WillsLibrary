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

public class DiskTab extends Fragment  {
    Connection conn;
    String un, pass, db, ip;
    private ListView lv;
    private DiskAdapter diskAdapter;
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
        View rootView = inflater.inflate(R.layout.catalogue_tab_disks, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvdisks);
        ArrayList<Disk> disks = new ArrayList<Disk>();


        //Getting all the information of disk database
        String diskquery = "SELECT * FROM DB_A3C994_will.dbo.disk;";
        String imagequery = "SELECT image FROM DB_A3C994_will.dbo.item WHERE d_title IS NOT NULL;";


        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(diskquery);
            Log.d("query", "Sent disk query");
            Log.d("query", "Getting response from Disk Query");
            int numdisks = rs.getFetchSize();
            while (rs.next()) {
                Disk disk = new Disk();
                disk.setDiskTitle(rs.getString(1));
                disk.setDiskdatereleased(rs.getString(2));
                disk.setDiskGenre(rs.getString(3));
                disk.setDiskType(rs.getString(4));
                disk.setDiskDescription(rs.getString(5));

                //This part is for the status of the disk.
                String statusquery = "SELECT * FROM DB_A3C994_will.dbo.item WHERE d_title='"+ disk.getDiskTitle()+"' ORDER BY status asc;";
                Statement st2 = conn.createStatement();
                ResultSet statusrs = st2.executeQuery(statusquery);

                if(statusrs.next()){
                    disk.setStatus(statusrs.getString(3));
                    disk.setItemNum(statusrs.getInt(1));
                }

                //Print to Log
                Log.d("query", "Title: " + disk.getDiskTitle());
                Log.d("query", "Genre: " + disk.getDiskGenre());
                Log.d("query", "Status: " + disk.getStatus());

                disks.add(disk);
            }
            //Uncomment this once images can be added
            /*
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(imagequery);
            Log.d("query", "Sent image query");
            index = 0;
            Log.d("query", "Getting response from Image Query");
            while(rs1.next()){
                diskimage[index] = rs1.getString(12);
                index++;
                Log.d("query", "Image URL: " + rs1.getString(12));
            }
            */
            conn.close();
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diskstitle);
        } catch (SQLException e) {
            Log.d("query", "Query of DiskTab Failed");
        }
        diskAdapter = new DiskAdapter(getContext(), disks);
        lv.setAdapter(diskAdapter);
        setupDiskSelectedListener();


        return rootView;

    }

    public void setupDiskSelectedListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View v,int position,long id)
            {
                //I no longer know what disk i clicked on?
                Intent intent = new Intent(getActivity(),itemDiskActivity.class);
                intent.putExtra("disk",(Serializable) diskAdapter.getItem(position));
                intent.putExtra("username",username);
                // Log.i("message",diskAdapter.getItem(position));
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
