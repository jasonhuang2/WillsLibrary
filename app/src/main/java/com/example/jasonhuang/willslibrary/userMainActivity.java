package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
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
        nameText.setText(first_name + '!');

        //This is for the back button but I dont think the user's main page needs a back button back to the login page
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void itemLookUpListener(View v){
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        //Gets the input from the itemID input box
        EditText itemIDInputBox = (EditText) findViewById(R.id.itemIDInputBox);

        String itemID = itemIDInputBox.getText().toString();
        Log.d("query", "ITEMID: " + itemID);
        //First Query to obtain the item type
        //Creating the query to obtain the type
        String itemquery = "SELECT * FROM DB_A3C994_will.dbo.item WHERE item_id='" + itemID + "';";

        try{
            Statement itemstmt = conn.createStatement();
            ResultSet itemrs = itemstmt.executeQuery(itemquery);
            Log.d("query", "Sending Query");
            //If the query returns a response
            if(itemrs.next()){
                //Obtain the type from the response
                String item_type = itemrs.getString("type");
                String item_status = itemrs.getString("status");
                String item_image = itemrs.getString("image");
                Log.d("query", "Received Response: " + item_type);
                //Do further querying to obtain Book information
                if(item_type.equals("Book")){
                    String isbn = itemrs.getString("b_isbn");

                    String bookquery = "SELECT * FROM DB_A3C994_will.dbo.book WHERE isbn='" + isbn + "';";
                    //Obtaining information for the book
                    try{
                        Statement bookstmt = conn.createStatement();
                        ResultSet bookrs = bookstmt.executeQuery(bookquery);

                        if(bookrs.next()){
                            String title_string = bookrs.getString("title");
                            String genre_string = bookrs.getString("genre");
                            String publisher_string = bookrs.getString("publisher");
                            String publishing_date_string = bookrs.getString("publishing_date");
                            String description_string = bookrs.getString("description");
                            //Obtaining the author information
                            String authorquery = "SELECT * FROM DB_A3C994_will.dbo.author WHERE b_isbn='" + isbn + "';";
                            try{
                                Statement authorstmt = conn.createStatement();
                                ResultSet authorrs = authorstmt.executeQuery(authorquery);

                                if(authorrs.next()) {
                                    String author_string = authorrs.getString("author_name");
                                    //Passing the information to the activity to be displayed
                                    Intent itemBookPage = new Intent(this, itemBookActivity.class);
                                    itemBookPage.putExtra("title_string", title_string);
                                    itemBookPage.putExtra("author_string", author_string);
                                    itemBookPage.putExtra("genre_string", genre_string);
                                    itemBookPage.putExtra("publisher_string", publisher_string);
                                    itemBookPage.putExtra("publishing_date_string", publishing_date_string);
                                    itemBookPage.putExtra("description_string", description_string);

                                    startActivity(itemBookPage);
                                } else {
                                    Log.d("query", "Author does not exist in the database");
                                }
                            } catch (SQLException e) {
                                Log.d("query", "Query Failed");
                            }
                        }
                        else{
                            Log.d("query", "Book does not exist in the database");
                        }
                    } catch (SQLException e){
                        Log.d("query", "Query Failed");
                    }
                }
                else if(item_type.equals("Disk"))
                {
                    String d_title = itemrs.getString("d_title");
                    String d_date_released = itemrs.getString("d_date_released");

                    String diskquery = "SELECT * FROM DB_A3C994_will.dbo.disk WHERE title='" + d_title + "' AND date_released='" + d_date_released + "';";
                    try{
                        Statement diskstmt = conn.createStatement();
                        ResultSet diskrs = diskstmt.executeQuery(diskquery);

                        if(diskrs.next()){
                            String title_string = diskrs.getString("title");
                            String datereleased_string = diskrs.getString("date_released");
                            String genre_string = diskrs.getString("genre");
                            String disktype_string = diskrs.getString("disk_type");
                            String description_string = diskrs.getString("description");

                            Intent itemDiskPage = new Intent(this, itemDiskActivity.class);
                            itemDiskPage.putExtra("title_string", title_string);
                            itemDiskPage.putExtra("genre_string", genre_string);
                            itemDiskPage.putExtra("datereleased_string", datereleased_string);
                            itemDiskPage.putExtra("disktype_string", disktype_string);
                            itemDiskPage.putExtra("description_string", description_string);

                            startActivity(itemDiskPage);
                        }
                        else{
                            Log.d("query", "Disk does not exist in the database");
                        }
                    } catch (SQLException e){
                        Log.d("query", "Query Failed");
                    }
                }
                else if(item_type.equals("Other"))
                {
                    String o_other_id = itemrs.getString("o_other_id");

                    String otherquery = "SELECT * FROM DB_A3C994_will.dbo.other WHERE other_id='" + o_other_id + "';";
                    try{
                        Statement otherstmt = conn.createStatement();
                        ResultSet otherrs = otherstmt.executeQuery(otherquery);

                        if(otherrs.next()){
                            String type_string = otherrs.getString("type");
                            String description_string = otherrs.getString("description");

                            Intent itemOtherPage = new Intent(this, itemOtherActivity.class);
                            itemOtherPage.putExtra("type_string", type_string);
                            itemOtherPage.putExtra("description_string", description_string);

                            startActivity(itemOtherPage);
                        }
                        else{
                            Log.d("query", "Other Item does not exist in the database");
                        }
                    } catch (SQLException e){
                        Log.d("query", "Query Failed");
                    }
                }
            }
            else{
                Log.d("query", "Item does not exist in the database");

            }
        } catch(SQLException e){

        }
        /*
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(itemquery);
            Log.d("query", "Sent Query");
            if(rs.next()){
                Log.d("query", "Received Response from Query");
                String title_string = rs.getString("title");
                String genre_string = rs.getString("genre");
                String publisher_string = rs.getString("publisher");
                String publishing_date_string = rs.getString("publishing_date");
                String description_string = rs.getString("description");

                Intent itemPage = new Intent(this, itemBookActivity.class);
                itemPage.putExtra("title_string", title_string);
                itemPage.putExtra("genre_string", genre_string);
                itemPage.putExtra("publisher_string", publisher_string);
                itemPage.putExtra("publishing_date_string", publishing_date_string);
                itemPage.putExtra("description_string", description_string);

                startActivity(itemPage);

            }

        }catch(SQLException e){
            Log.d("query", "Query Failed");
        }
        */


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
