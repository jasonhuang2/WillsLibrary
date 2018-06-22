package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class itemBookActivity extends AppCompatActivity {

    private Book toDisplay;
    private int item_id;
    private String ip,db,un,pass;
    private String username;
    private Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_book_activity);
        setTitle("Book Description");

        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here

        /*
        String title_string = getIntent().getExtras().getString("title_string");
        String author_string = getIntent().getExtras().getString("author_string");
        String genre_string = getIntent().getExtras().getString("genre_string");
        String publisher_string = getIntent().getExtras().getString("publisher_string");
        String publishing_date_string = getIntent().getExtras().getString("publishing_date_string");
        String description_string = getIntent().getExtras().getString("description_string");
        String status_string = getIntent().getExtras().getString("status_string");
        String author_name_string = getIntent().getExtras().getString("author_name_string");*/
       // String status_string="In_Store";
        toDisplay = (Book) getIntent().getSerializableExtra("book");
        username = getIntent().getStringExtra("username");



        TextView titleNameBox = (TextView)findViewById(R.id.titleTextViewBox);
        TextView authorBox = (TextView) findViewById(R.id.authorTextViewBox);
        TextView genreBox = (TextView)findViewById(R.id.genreTextViewBox);
        TextView publisherBox = (TextView)findViewById(R.id.publisherTextViewBox);
        TextView publishing_date_Box = (TextView)findViewById(R.id.publishingDateTextView);
        TextView descriptionBox = (TextView)findViewById(R.id.descriptionTextViewBox);
        ImageView bookImage = (ImageView)findViewById(R.id.bookImage);

        titleNameBox.setText(toDisplay.getBookTitle());
        authorBox.setText(toDisplay.getBookAuthor());
        genreBox.setText(toDisplay.getBookGenre());
        publisherBox.setText(toDisplay.getPublisher());
        publishing_date_Box.setText(toDisplay.getPublishingDate());
        descriptionBox.setText(toDisplay.getDescription());

        item_id=toDisplay.getItemNum();
        switch(item_id) {
            case 1:
                bookImage.setImageResource(R.drawable.deathofasalesman);
                break;
            case 2:
                bookImage.setImageResource(R.drawable.merchantofvenice);
                break;
            case 5:
                bookImage.setImageResource(R.drawable.readyplayerone);
                break;
            case 6:
                bookImage.setImageResource(R.drawable.hpandps);
                break;
            case 8:
                bookImage.setImageResource(R.drawable.merchantofvenice);
                break;
            case 9:
                bookImage.setImageResource(R.drawable.merchantofvenice);
                break;
            default:
                bookImage.setImageResource(R.drawable.ic_action_name);
                break;
        }

        TextView availableForRentText = (TextView)findViewById(R.id.availableForRentText);
        //Where is this comming from... as it is known by the item entity, an not the book entity.
        //Next How do i know what item is being passed through to me for rental, is could search the db for all items with
        //That isbn but i could get multiple available items.
        TextView notAvailableForRentText = (TextView)findViewById(R.id.notAvailableForRent);
        TextView inStockText = (TextView)findViewById(R.id.inStockText);

        Button rentButton = (Button)findViewById(R.id.rentButton);

        //canIRent will check to see if the username is able to rent or not by determining if they're any outstanding fees.
        //Parameters: The username string.
        //Returns 0 if username is NOT able to rent (has outstanding fees)
        //Returns 1 if username is ABLE to rent (has NO outstanding fees)
        rentEligibilityChecker canIRent = new rentEligibilityChecker();

        if(canIRent.check(username) == 0){
            //This username cannot rent (has overdue fees to pay)

            TextView overdueMessage = (TextView) findViewById(R.id.overdueMessage);
            overdueMessage.setVisibility(View.VISIBLE);
            rentButton.setEnabled(false);

        }else{
            //This username is able to rent

            //To display "available" or "rented" for the user to see
            //"In_Store" means item is available for rent.
            //"Rented" means it's rented
            //"In_Stock" means it's available but owner does not want it rented
            if(toDisplay.getStatus().equals("1_In_Store")){
                availableForRentText.setVisibility(View.VISIBLE);

            }else if (toDisplay.getStatus().equals("3_Rented")){
                notAvailableForRentText.setVisibility(View.VISIBLE);
                rentButton.setEnabled(false);
            }else{
                //status is not "in_store" or "rented" then this part of the code is "In_stock"
                inStockText.setVisibility(View.VISIBLE);
                rentButton.setEnabled(false);
            }
        }



        //END OF OnCreate();
    }


    public void onRentalClick(View v)
    {
        conn = connectionclass(un, pass, db, ip);
        TextView errorBox = findViewById(R.id.descriptionTextViewBox);
        //Now this needs to do something, this is set the user rental service to be rented, and all the other things.
        String book_isbn=toDisplay.getBookISBN();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar myDate = Calendar.getInstance();
        //ADD 7 day in the future.
        myDate.add(myDate.DATE,7 );

        //String query = "SELECT ITEM.item_id, ITEM.status FROM DB_A3C994_will.dbo.item WHERE ITEM.b_isbn"+book_isbn+" AND ITEM.status='In_Stock';";
        String query = "SELECT COUNT(*) AS num_rentals FROM DB_A3C994_will.dbo.rental;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int nextID;
            if(rs.next())
            {
                nextID=rs.getInt(1);
            }else{
                //Note this should never happen, idk why it might i guess
                return;
            }
            //Add the book to the rental service.
            query ="INSERT INTO DB_A3C994_will.dbo.rental (rental_id,cost_per_day_overdue,due_date,i_item_id,a_username) VALUES ("+nextID+",0.50," +
                    "'"+sd.format(myDate.getTime())+"',"+item_id+",'ADMIN');";

            stmt.execute(query);
            //Set the book as rented.
            query ="UPDATE DB_A3C994_will.dbo.item SET status='3_Rented' WHERE item_id="+item_id+";";
            stmt.execute(query);
            //make sure that the user has the book rented
            //how do i find out the user that is logged in? well push it through really janky like through the entire code base.
            query = "INSERT INTO DB_A3C994_will.dbo.consume VALUES ('"+username+"',"+nextID+");";
            //errorBox.setText(query);
            stmt.execute(query);
            //now update the location of the book to the address of the user.
            //This is going unimplemented.
            //UPDATE LOCATION
            //SET [LOCATION].address = [USERS].address
            //FROM LOCATION
            //INNER JOIN ITEM ON LOCATION.i_item_id=ITEM.item_id
            //INNER JOIN RENTAL ON RENTAL.i_item_id=ITEM.item_id
            //INNER JOIN CONSUME ON RENTAL.rental_id=CONSUME.r_rental_id
            //INNER JOIN USERS ON [USERS].username=[CONSUME].u_username
            //WHERE [USERS].username='user';
            //That is an stupid in my opinion. But I do not want to deal with asking for responce
            query="UPDATE LOCATION " +
                    "SET [LOCATION].address = [USERS].address " +
                    "FROM LOCATION " +
                    "INNER JOIN ITEM ON LOCATION.i_item_id=ITEM.item_id " +
                    "INNER JOIN RENTAL ON RENTAL.i_item_id=ITEM.item_id " +
                    "INNER JOIN CONSUME ON RENTAL.rental_id=CONSUME.r_rental_id " +
                    "INNER JOIN USERS ON [USERS].username=[CONSUME].u_username " +
                    "WHERE [USERS].username='"+username+"';";
            stmt.execute(query);



        conn.close();
        }catch (SQLException e)
        {
            Log.i("ERROR",e.getMessage());

        }
        Toast.makeText(getBaseContext(), "Item Rented!", Toast.LENGTH_SHORT).show();
        //SELECT username FROM DB_A3C994_will.dbo.users WHERE username
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
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
