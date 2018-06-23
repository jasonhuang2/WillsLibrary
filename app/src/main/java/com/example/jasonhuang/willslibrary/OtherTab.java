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
    private ArrayList<Other> others = new ArrayList<Other>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        username = getArguments().getString("username");
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.catalogue_tab_others, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvothers);

       // Log.d("query", "Testing Other Tab");
        others = catalogueTabActivity.other();
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
