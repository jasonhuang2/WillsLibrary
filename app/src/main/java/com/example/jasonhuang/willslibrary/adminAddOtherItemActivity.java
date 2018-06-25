package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class adminAddOtherItemActivity extends AppCompatActivity {
    Connection conn;
    String un, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        conn = connectionclass(un, pass, db, ip);

        ////Set from constants.
        ip = DBlocationConstants.getip();
        db = DBlocationConstants.getdb();
        un = DBlocationConstants.getun();
        pass = DBlocationConstants.getps();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_other_item);

        setTitle("Add Other Item");

    }


    public void addOtherButtonListener(View v){
        conn = connectionclass(un, pass, db, ip);

        EditText otherIdInput = (EditText)findViewById(R.id.otherIdInput);
        EditText typeInput = (EditText)findViewById(R.id.typeInput);
        EditText otherDescriptionInput = (EditText)findViewById(R.id.otherDescriptionInput);


        String addOtherQuery = "INSERT INTO other (other_id, type, description) VALUES('"+otherIdInput.getText().toString()+
                "', '"+typeInput.getText().toString()+"', '"+otherDescriptionInput.getText().toString()+"');";

        try{
            Statement addOtherStatement = conn.createStatement();
            addOtherStatement.execute(addOtherQuery);
            Toast.makeText(getApplicationContext(),"Other item has been added to the database",Toast.LENGTH_SHORT).show();

            otherIdInput.setText("");
            typeInput.setText("");
            otherDescriptionInput.setText("");



        }catch(SQLException e){

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
