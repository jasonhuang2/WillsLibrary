package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Date;
import java.util.Random;

public class adminAddItemActivity extends AppCompatActivity {
    Connection conn;
    String un, pass, db, ip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        conn = connectionclass(un, pass, db, ip);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_item_activity);

        setTitle("Add Item");

        //NOTE: Fill these attributes before you execute this program
        ////Set from constants.
        ip = DBlocationConstants.getip();
        db = DBlocationConstants.getdb();
        un = DBlocationConstants.getun();
        pass = DBlocationConstants.getps();

        Spinner typeOfItemSpinner = (Spinner)findViewById(R.id.typeOfItemSpinner);

        //All of this crap is for BOOK
        final EditText bookNameInput = (EditText)findViewById(R.id.bookNameInput);
        final TextView bookNameText = (TextView)findViewById(R.id.bookNameText);
        final TextView authorNameText = (TextView) findViewById(R.id.authorNameText);
        final EditText authorNameInput = (EditText) findViewById(R.id.authorNameInput);
        final TextView publisherNameText = (TextView)findViewById(R.id.publisherNameText);
        final EditText publisherNameInput = (EditText)findViewById(R.id.publisherNameInput);
        final TextView publisherDateText = (TextView) findViewById(R.id.publishingDateText);
        final EditText publisherDateInput = (EditText)findViewById(R.id.publishingDateInput);
        final TextView descriptionText = (TextView)findViewById(R.id.descriptionText);
        final EditText descriptionInput = (EditText)findViewById(R.id.descriptionInput);
        final Button addBookButton = (Button)findViewById(R.id.addItemButton);
        final TextView isbnText = (TextView) findViewById(R.id.isbnText);
        final EditText isbnInput = (EditText)findViewById(R.id.isbnInput);
        final EditText genreInput = (EditText)findViewById(R.id.genreInput);
        final TextView genreText = (TextView) findViewById(R.id.genreText);

        //All of this crap is for Disk
        final TextView diskTitleText = (TextView)findViewById(R.id.diskTitleText);
        final TextView diskDateReleasedText = (TextView)findViewById(R.id.dateReleasedText);
        final TextView diskGenreText = (TextView)findViewById(R.id.diskGenreText);
        final TextView diskTypeText = (TextView)findViewById(R.id.diskTypeText);
        final TextView diskDescriptionText = (TextView)findViewById(R.id.diskDescriptionText);
        final EditText diskTitleInput = (EditText)findViewById(R.id.diskTitleInput);
        final EditText diskDateReleasedInput = (EditText)findViewById(R.id.dateReleasedInput);
        final EditText diskGenreInput = (EditText)findViewById(R.id.diskGenreInput);
        final EditText diskTypeInput = (EditText)findViewById(R.id.diskTypeInput);
        final EditText diskDescriptionInput = (EditText)findViewById(R.id.diskDescriptionInput);
        final Button addDiskButton = (Button)findViewById(R.id.addDiskButton);

        final TextView costText = (TextView)findViewById(R.id.costText);
        final EditText costInput = (EditText)findViewById(R.id.costInput);




        typeOfItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String itemSelected = parentView.getItemAtPosition(position).toString();

                if(itemSelected.equals("Book")){
                    bookNameInput.setVisibility(View.VISIBLE);
                    bookNameText.setVisibility(View.VISIBLE);
                    authorNameText.setVisibility(View.VISIBLE);
                    authorNameInput.setVisibility(View.VISIBLE);
                    publisherNameText.setVisibility(View.VISIBLE);
                    publisherNameInput.setVisibility(View.VISIBLE);
                    publisherDateText.setVisibility(View.VISIBLE);
                    publisherDateInput.setVisibility(View.VISIBLE);
                    descriptionText.setVisibility(View.VISIBLE);
                    descriptionInput.setVisibility(View.VISIBLE);
                    addBookButton.setVisibility(View.VISIBLE);
                    isbnText.setVisibility(View.VISIBLE);
                    isbnInput.setVisibility(View.VISIBLE);
                    genreInput.setVisibility(View.VISIBLE);
                    genreText.setVisibility(View.VISIBLE);
                    costText.setVisibility(View.VISIBLE);
                    costInput.setVisibility(View.VISIBLE);

                    //Disk

                    diskTitleText.setVisibility(View.INVISIBLE);
                    diskDateReleasedText.setVisibility(View.INVISIBLE);
                    diskGenreText.setVisibility(View.INVISIBLE);
                    diskTypeText.setVisibility(View.INVISIBLE);
                    diskDescriptionText.setVisibility(View.INVISIBLE);
                    diskTitleInput.setVisibility(View.INVISIBLE);
                    diskDateReleasedInput.setVisibility(View.INVISIBLE);
                    diskGenreInput.setVisibility(View.INVISIBLE);
                    diskTypeInput.setVisibility(View.INVISIBLE);
                    diskDescriptionInput.setVisibility(View.INVISIBLE);
                    addDiskButton.setVisibility(View.INVISIBLE);



                }else if(itemSelected.equals("Disk")){
                    //WHEN USER SELECTS "Disk"
                    bookNameInput.setVisibility(View.INVISIBLE);
                    bookNameText.setVisibility(View.INVISIBLE);
                    authorNameText.setVisibility(View.INVISIBLE);
                    authorNameInput.setVisibility(View.INVISIBLE);
                    publisherNameText.setVisibility(View.INVISIBLE);
                    publisherNameInput.setVisibility(View.INVISIBLE);
                    publisherDateText.setVisibility(View.INVISIBLE);
                    publisherDateInput.setVisibility(View.INVISIBLE);
                    descriptionText.setVisibility(View.INVISIBLE);
                    descriptionInput.setVisibility(View.INVISIBLE);
                    addBookButton.setVisibility(View.INVISIBLE);
                    isbnText.setVisibility(View.INVISIBLE);
                    isbnInput.setVisibility(View.INVISIBLE);
                    genreInput.setVisibility(View.INVISIBLE);
                    genreText.setVisibility(View.INVISIBLE);
                    costText.setVisibility(View.VISIBLE);
                    costInput.setVisibility(View.VISIBLE);

                    diskTitleText.setVisibility(View.VISIBLE);
                    diskDateReleasedText.setVisibility(View.VISIBLE);
                    diskGenreText.setVisibility(View.VISIBLE);
                    diskTypeText.setVisibility(View.VISIBLE);
                    diskDescriptionText.setVisibility(View.VISIBLE);
                    diskTitleInput.setVisibility(View.VISIBLE);
                    diskDateReleasedInput.setVisibility(View.VISIBLE);
                    diskGenreInput.setVisibility(View.VISIBLE);
                    diskTypeInput.setVisibility(View.VISIBLE);
                    diskDescriptionInput.setVisibility(View.VISIBLE);
                    addDiskButton.setVisibility(View.VISIBLE);





                }else{
                    //IF USER SELECTED "OTHER"
                    bookNameInput.setVisibility(View.INVISIBLE);
                    bookNameText.setVisibility(View.INVISIBLE);
                    authorNameText.setVisibility(View.INVISIBLE);
                    authorNameInput.setVisibility(View.INVISIBLE);
                    publisherNameText.setVisibility(View.INVISIBLE);
                    publisherNameInput.setVisibility(View.INVISIBLE);
                    publisherDateText.setVisibility(View.INVISIBLE);
                    publisherDateInput.setVisibility(View.INVISIBLE);
                    descriptionText.setVisibility(View.INVISIBLE);
                    descriptionInput.setVisibility(View.INVISIBLE);
                    addBookButton.setVisibility(View.INVISIBLE);
                    isbnText.setVisibility(View.INVISIBLE);
                    isbnInput.setVisibility(View.INVISIBLE);
                    genreInput.setVisibility(View.INVISIBLE);
                    genreText.setVisibility(View.INVISIBLE);

                    diskTitleText.setVisibility(View.INVISIBLE);
                    diskDateReleasedText.setVisibility(View.INVISIBLE);
                    diskGenreText.setVisibility(View.INVISIBLE);
                    diskTypeText.setVisibility(View.INVISIBLE);
                    diskDescriptionText.setVisibility(View.INVISIBLE);
                    diskTitleInput.setVisibility(View.INVISIBLE);
                    diskDateReleasedInput.setVisibility(View.INVISIBLE);
                    diskGenreInput.setVisibility(View.INVISIBLE);
                    diskTypeInput.setVisibility(View.INVISIBLE);
                    diskDescriptionInput.setVisibility(View.INVISIBLE);
                    addDiskButton.setVisibility(View.INVISIBLE);


                    Intent otherItem = new Intent(adminAddItemActivity.this, adminAddOtherItemActivity.class);
                    startActivity(otherItem);


                }
            }
            //Ignore this for now
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

    }

    /*
     * this is responsible for adding a book
     */
    public void addButtonBookListener(View v){
        conn = connectionclass(un, pass, db, ip);

         EditText bookNameInput = (EditText)findViewById(R.id.bookNameInput);
         EditText authorNameInput = (EditText) findViewById(R.id.authorNameInput);
         EditText publisherNameInput = (EditText)findViewById(R.id.publisherNameInput);
         EditText publisherDateInput = (EditText)findViewById(R.id.publishingDateInput);
         EditText descriptionInput = (EditText)findViewById(R.id.descriptionInput);
         EditText isbnInput = (EditText)findViewById(R.id.isbnInput);
         EditText genreInput = (EditText)findViewById(R.id.genreInput);
         EditText costInput = (EditText)findViewById(R.id.costInput);



        //Ya writing your report? This query looks like this:
        /**
         * INSERT INTO book (isbn, title, genre, publisher, publishing_date, description) VALUES ('999', 'testBook', 'testGenre', 'somePublisher', '01-01-1992', 'suppppppDescription');
           INSERT INTO author (b_isbn, author_name) VALUES ((SELECT isbn FROM book WHERE isbn='999'), 'bob');
         */
         String addBookQuery = "INSERT INTO book (isbn, title, genre, publisher, publishing_date, description) VALUES ('"+isbnInput.getText().toString()+"', '"+bookNameInput.getText().toString()
                 +"', '"+genreInput.getText().toString()+"', '"+publisherNameInput.getText().toString()+"', '"+publisherDateInput.getText().toString()+"'," +
                 " '"+descriptionInput.getText().toString()+"');\n" +
                 "INSERT INTO author (b_isbn, author_name) VALUES ((SELECT isbn FROM book WHERE isbn='"+isbnInput.getText().toString()+"'), '"+authorNameInput.getText().toString()+"');";



        Random rand = new Random();
        int  rngNum = rand.nextInt(20 - 11 + 1) + 11;


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = df.format(c);


         String updateItemTable = "INSERT INTO item (item_id, cost, status, type, quality, a_username, date_added, date_removed, b_isbn, d_title, d_date_released, o_other_id, image)" +
                 " VALUES('"+Integer.toString(rngNum)+"', '"+costInput.getText().toString()+"', '1_In_Store', 'Book', 'Excellent', 'ADMIN', '"+todaysDate+"', NULL, '"+isbnInput.getText().toString()+"', '"+bookNameInput.getText().toString()+"', NULL, NULL, '@drawable/ic_launcher_background');";
        try{


            Statement addBookStatement = conn.createStatement();
            addBookStatement.execute(addBookQuery);





            Toast.makeText(getApplicationContext(),"Book item has been added to the database. The Item ID is: " + Integer.toString(rngNum),Toast.LENGTH_SHORT).show();
            bookNameInput.setText("");
            authorNameInput.setText("");
            publisherNameInput.setText("");
            publisherDateInput.setText("");
            descriptionInput.setText("");
            isbnInput.setText("");
            genreInput.setText("");



        }catch(SQLException e){

        }

        try{
            Statement updateTableStatement = conn.createStatement();
            updateTableStatement.executeQuery(updateItemTable);

            costInput.setText("");

        }catch (SQLException e){

        }

    }

    public void addDiskListener(View v){
        conn = connectionclass(un, pass, db, ip);

         EditText diskTitleInput = (EditText)findViewById(R.id.diskTitleInput);
         EditText diskDateReleasedInput = (EditText)findViewById(R.id.dateReleasedInput);
         EditText diskGenreInput = (EditText)findViewById(R.id.diskGenreInput);
         EditText diskTypeInput = (EditText)findViewById(R.id.diskTypeInput);
         EditText diskDescriptionInput = (EditText)findViewById(R.id.diskDescriptionInput);
        EditText costInput = (EditText)findViewById(R.id.costInput);


        String addDiskQuery = "INSERT INTO disk (title, date_released, genre, disk_type, description) VALUES('"+
                diskTitleInput.getText().toString() + "', '"+diskDateReleasedInput.getText().toString()+"', '"+diskGenreInput.getText().toString()
                +"', '"+diskTypeInput.getText().toString()+"', '"+diskDescriptionInput.getText().toString()+"');";



        Random rand = new Random();
        int  rngNum = rand.nextInt(20 - 11 + 1) + 11;


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = df.format(c);


        String updateItemTable = "INSERT INTO item (item_id, cost, status, type, quality, a_username, date_added, date_removed, b_isbn, d_title, d_date_released, o_other_id, image)" +
                " VALUES('"+Integer.toString(rngNum)+"', '"+costInput.getText().toString()+"', '1_In_Store', 'Disk', 'Excellent', 'ADMIN', '"+todaysDate+"', NULL, NULL, '"+diskTitleInput.getText().toString()+"', '"+diskDateReleasedInput.getText().toString()+"', NULL, '@drawable/diskimage');";

        try{
            Statement addDiskStatement = conn.createStatement();
            addDiskStatement.execute(addDiskQuery);
            Toast.makeText(getApplicationContext(),"Disk item has been added to the database. The Item ID is: " + Integer.toString(rngNum) ,Toast.LENGTH_SHORT).show();

            diskTitleInput.setText("");
            diskDateReleasedInput.setText("");
            diskGenreInput.setText("");
            diskTypeInput.setText("");
            diskDescriptionInput.setText("");


        }catch(SQLException e){

        }

        try{
            Statement updateTableStatement = conn.createStatement();
            updateTableStatement.executeQuery(updateItemTable);

            costInput.setText("");

        }catch (SQLException e){

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
