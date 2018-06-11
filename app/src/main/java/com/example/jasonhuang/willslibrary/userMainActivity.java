package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userMainActivity extends AppCompatActivity {

    Connection conn;
    String un, pass, db, ip;
    private TextView nameText; //Problem: Once this layout is loaded I want it to display "Welcome 'yournamegoeshere'"
    // But this doesn't work if you try doing a query inside of  the onCreate function.
    //Solution: I want to do a query in the mainActivity.java which finds out the name depending on the inputted username
    // I then passed it on into this java and displayed it.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Any layout that requires access to the database must have this
        conn = connectionclass(un, pass, db, ip);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_activity);

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here

        //This part here retrives whatever I passed through from MainActivity.java using the key word, "first_name"
        String first_name = getIntent().getExtras().getString("first_name");

        //Printed it out.
        nameText = (TextView)findViewById(R.id.nameText);
        nameText.setText(first_name);

        //This is for the back button but I dont think the user's main page needs a back button back to the login page
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void bookLookUpListener(View v){
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database

        EditText isbnInputBox = (EditText) findViewById(R.id.isbnInputBox);

        String isbn = isbnInputBox.getText().toString();






        String query = "SELECT * FROM DB_A3C994_will.dbo.book WHERE isbn='" + isbn + "';";

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()){

                String title_string = rs.getString("title");
                String genre_string = rs.getString("genre");
                String publisher_string = rs.getString("publisher");
                String publishing_date_string = rs.getString("publishing_date");
                String description_string = rs.getString("description");

                Intent itemPage = new Intent(this, itemActivity.class);
                itemPage.putExtra("title_string", title_string);
                itemPage.putExtra("genre_string", genre_string);
                itemPage.putExtra("publisher_string", publisher_string);
                itemPage.putExtra("publishing_date_string", publishing_date_string);
                itemPage.putExtra("description_string", description_string);

                startActivity(itemPage);

            }

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
    //Going to the Catalogue page
    public void catalogueClick(View v){
        Intent catalogueI = new Intent(this, catalogueMainActivity.class);
        startActivity(catalogueI);
    }
    //Going to the My Rentals page
    public void my_rental_Click(View v){
        Intent myrentalI = new Intent(this, myrentalMainActivity.class);
        startActivity(myrentalI);
    }
    //Going to the My Fines page
    public void my_fines_Click(View v){
        Intent myfinesI = new Intent(this, myfinesMainActivity.class);
        startActivity(myfinesI);
    }
}
