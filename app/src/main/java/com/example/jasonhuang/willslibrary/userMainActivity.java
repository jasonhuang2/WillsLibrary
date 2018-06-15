package com.example.jasonhuang.willslibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.Result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class userMainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    Connection conn;
    String un, pass, db, ip;
    private TextView nameText; //Problem: Once this layout is loaded I want it to display "Welcome 'yournamegoeshere'"
    // But this doesn't work if you try doing a query inside of  the onCreate function.
    //Solution: I want to do a query in the mainActivity.java which finds out the name depending on the inputted username
    // I then passed it on into this java and displayed it.
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ZXingScannerView zXingScannerView;

    String first_name;


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
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout mFrame = (RelativeLayout)inflater.inflate(R.layout.main_activity, null);

        //String first_name = getIntent().getExtras().getString("first_name");
        first_name = getIntent().getExtras().getString("first_name");

        //Printed it out.
        nameText = (TextView)findViewById(R.id.nameText);
        nameText.setText(first_name + '!');


        //The result from the barcode scanner. Once the user finishes scanning, the result of that barcode
        //Will be in the itemID inputbox. All use has to do is press SEARCH
        //.setText (.getExtras()) I just combined it all within one sentence.
        EditText itemIDInputBox = (EditText) findViewById(R.id.itemIDInputBox);
        itemIDInputBox.setText(getIntent().getExtras().getString("Barcode_Result"));


    }


    /**
     * This is the listening function for the barcode scanner
     * User must have camera permissions on
     */
    public void barcodeScannerListener(View v){
        ActivityCompat.requestPermissions(userMainActivity.this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);
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

                    //String bookquery = "SELECT * FROM DB_A3C994_will.dbo.book WHERE isbn='" + isbn + "';";
                    //I decided to join the book and author table in order to retrieve the author's name.
                    //https://www.youtube.com/watch?v=2HVMiPPuPIM is an extremely helpful vid on JOINS, very clear.
                    String bookquery = "SELECT * FROM DB_A3C994_will.dbo.book INNER JOIN DB_A3C994_will.dbo.author ON DB_A3C994_will.dbo.book.isbn = DB_A3C994_will.dbo.author.b_isbn WHERE isbn='" + isbn + "';";
                    //Obtaining information for the book
                    try{
                        Statement bookstmt = conn.createStatement();
                        ResultSet bookrs = bookstmt.executeQuery(bookquery);

                        if(bookrs.next()){
                            String title_string = bookrs.getString("title");
                            String author_string = bookrs.getString("author_name");
                            String genre_string = bookrs.getString("genre");
                            String publisher_string = bookrs.getString("publisher");
                            String publishing_date_string = bookrs.getString("publishing_date");
                            String description_string = bookrs.getString("description");
                            //Passing the information to the activity to be displayed
                            Intent itemBookPage = new Intent(this, itemBookActivity.class);
                            itemBookPage.putExtra("title_string", title_string);
                            itemBookPage.putExtra("genre_string", genre_string);
                            itemBookPage.putExtra("publisher_string", publisher_string);
                            itemBookPage.putExtra("publishing_date_string", publishing_date_string);
                            itemBookPage.putExtra("description_string", description_string);
                            itemBookPage.putExtra("status_string", item_status);
                            itemBookPage.putExtra("author_name_string", author_string);




                            startActivity(itemBookPage);
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
            conn.close();
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

    public void logoutListener(View v)
    {
        Intent dummy = new Intent();
        setResult(Activity.RESULT_OK,dummy);
        finish();
    }
    /**
     * This function is part of the barcode scanner
     * If permissions are granted then start the zXingScannerView (which reads barcode from Android)
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                zXingScannerView =new ZXingScannerView(getApplicationContext());
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler(this);
                zXingScannerView.startCamera();
                Toast.makeText(this, "Camera permission granted, Please scan barcode.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission denied, please enable permissions to use camera.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Pretty annoying, just ignore what this does. The camera requires this
     * function to run
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * This function is very important. It handles the result from the barcode scanner.
     * The barcode result number will be stored within "result"
     * I just made an new activity back to the user main menu with barcode number in the item id box.
     */
    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        Intent backToUserMainActivity = new Intent (this, userMainActivity.class);
        backToUserMainActivity.putExtra("Barcode_Result", result.getText());
        backToUserMainActivity.putExtra("first_name", first_name);
        zXingScannerView.stopCamera();

        startActivity(backToUserMainActivity);
       // zXingScannerView.resumeCameraPreview(this);

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
        //Intent catalogueI = new Intent(this, catalogueMainActivity.class);
        //startActivity(catalogueI);
        //Intent catalogue2 = new Intent(this, catalogue2Activity.class);
        //startActivity(catalogue2);
        Intent cataloguetab = new Intent(this, catalogueTabActivity.class);
        startActivity(cataloguetab);
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
