package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    Connection conn;
    String un, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        conn = connectionclass(un, pass, db, ip);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
    }

    //Make sure to link the method to the button using the activity_main.xml file, click on the button, go to properties
    //and add this method under "onClick" property
    public void onButtonClickConnect(View v){
        conn = connectionclass(un, pass, db, ip);

        EditText t1 = (EditText)findViewById(R.id.text);

        if(conn == null){
            t1.setText("No");
        }else{
            t1.setText("Yes");

            try {
                //Inserting into the database with db = database name
                //dbo.names is the table name
                //standard format for accessing the sql server provided through Tamer is the following
                String query = "INSERT INTO " + db + ".dbo.names VALUES (3,'Dudesdfasdfasdfasdf');";
                Statement stmt = conn.createStatement();
                stmt.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public void loginButtonClick(View v){
        conn = connectionclass(un, pass, db, ip);

        EditText usernameInputBox = (EditText)findViewById(R.id.usernameInputBox);
        EditText passwordInputBox = (EditText)findViewById(R.id.passwordInputBox);
        TextView testText = (TextView)findViewById(R.id.textView);


        String query = "SELECT * FROM DB_A3C994_will.dbo.users WHERE username='" + usernameInputBox.getText().toString()+"';";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            if(rs.next()){
                String result = rs.getString("password");
                if(passwordInputBox.getText().toString().equals(result)){
                    testText.setText("Logged in");
                }else{
                    testText.setText("Incorrect username or password");
                }


            }else{
                testText.setText("Something went wrong with the query");

            }

        }catch (SQLException e){
            e.printStackTrace();
            testText.setText("EXPLODED");

        }



    }

    public void testButtonClick(View v){
        TextView onoff = (TextView) findViewById(R.id.testTEST2);
        Button onoffButton = (Button) findViewById(R.id.test_button);

        if(onoff.getText().equals("OFF")){
            onoff.setText("ON");
            onoffButton.setText("ON");
        }
        else{
            onoff.setText("OFF");
            onoffButton.setText("OFF");
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
