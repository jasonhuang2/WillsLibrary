package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;

public class catalogueMainActivity extends AppCompatActivity {
    Connection conn;
    String un, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Any layout that requires access to the database must have this
        conn = connectionclass(un, pass, db, ip);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_activity);

        ////Set from constants.
        ip = DBlocationConstants.getip();
        db = DBlocationConstants.getdb();
        un = DBlocationConstants.getun();
        pass = DBlocationConstants.getps();


    }

    public void refreshButtonFunction(View v){
        TextView text = (TextView) findViewById(R.id.workplease);

        //Ignore this section, it's used for testing.

        StringBuilder stringBuilder = new StringBuilder();
        String someMessage = " This is some message. So can you scroll me down there could be some secret. ";

        for(int i = 0; i < 400; i++){
            stringBuilder.append(someMessage);
        }

        text.setText(stringBuilder.toString());




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
