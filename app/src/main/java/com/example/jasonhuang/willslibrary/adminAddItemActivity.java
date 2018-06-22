package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
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

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;

public class adminAddItemActivity extends AppCompatActivity {
    Connection conn;
    String un, pass, db, ip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_item_activity);

        setTitle("Add Item");

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
         EditText bookNameInput = (EditText)findViewById(R.id.bookNameInput);
         EditText authorNameInput = (EditText) findViewById(R.id.authorNameInput);
         EditText publisherNameInput = (EditText)findViewById(R.id.publisherNameInput);
         EditText publisherDateInput = (EditText)findViewById(R.id.publishingDateInput);
         EditText descriptionInput = (EditText)findViewById(R.id.descriptionInput);

        //String addBookQuery = "Insert "

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
