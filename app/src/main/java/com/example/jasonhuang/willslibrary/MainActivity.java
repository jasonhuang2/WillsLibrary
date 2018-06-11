package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity{

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


    /**
     * This function is responsible once the "LOGIN" button is pressed.
     *
     */
    public void loginButtonClick(View v){
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database

        EditText usernameInputBox = (EditText)findViewById(R.id.usernameInputBox);  //The username input box, I have to find the ID of it
        EditText passwordInputBox = (EditText)findViewById(R.id.passwordInputBox);  //The password input box, I have to find the ID of it
        TextView resultText = (TextView) findViewById(R.id.loginTextView2);     //I want to print out the result of entering a username and password and after querying the database
        Spinner userOrAdminSpinner = (Spinner) findViewById(R.id.user_or_admin_spinner);    //The spinner that is used to select what type of user is logging in


        //The actual query string
        String query = "SELECT * FROM DB_A3C994_will.dbo.users WHERE username='" + usernameInputBox.getText().toString()+"';";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            if(rs.next()){
                String result = rs.getString("password");
                String first_name = rs.getString("first_name");
                if(passwordInputBox.getText().toString().equals(result)){

                    if(userOrAdminSpinner.getSelectedItem().toString().equals("User")){
                        resultText.setText("Logged in");    //Set the text to Logged in to let the user know
                        Intent intent = new Intent(this, userMainActivity.class);   //Since now the user is logged in, switch over to the user main menu layout
                        intent.putExtra("first_name", first_name); //As explained in userMainActivity, first_name will be stored under the key word "first_name"

                        startActivity(intent);
                    }else{
                        resultText.setText("Sorry you are not an admin");
                    }

                }else{
                    resultText.setText("Incorrect username or password");
                }

            }else{
                resultText.setText("Incorrect username or password");
            }

        }catch (SQLException e){
            e.printStackTrace();
            resultText.setText("EXPLODED");

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
