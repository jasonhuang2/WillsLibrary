package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookTab extends Fragment {
    Connection conn;
    String un, pass, db, ip;
    private ListView lv;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //BOOK CLIENT STUFF
        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //enter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.catalogue_tab_books, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvbooks);
        ArrayList<Book> books = new ArrayList<Book>();

        //Getting all the information of book database
        String bookquery = "SELECT * FROM DB_A3C994_will.dbo.book;";
        String imagequery = "SELECT image FROM DB_A3C994_will.dbo.item WHERE b_isbn IS NOT NULL;";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(bookquery);
            Log.d("query", "Sent book query");
            Log.d("query", "Getting response from Book Query");
            int numbooks = rs.getFetchSize();
            while(rs.next()){
                Book book = new Book();
                book.setBookTitle(rs.getString(2));
                book.setBookGenre(rs.getString(3));
                //Print to Log
                Log.d("query", "Title: " + rs.getString(2));
                Log.d("query", "Genre: " + rs.getString(3));
                books.add(book);
            }
            //Uncomment this once images can be added
            /*
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(imagequery);
            Log.d("query", "Sent image query");
            index = 0;
            Log.d("query", "Getting response from Image Query");
            while(rs1.next()){
                bookimage[index] = rs1.getString(12);
                index++;
                Log.d("query", "Image URL: " + rs1.getString(12));
            }
            */
            conn.close();
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookstitle);
        }catch (SQLException e){
            Log.d("query", "Query of BookTab Failed");
        }
        bookAdapter = new BookAdapter(getContext(), books);
        lv.setAdapter(bookAdapter);
        //setupBookSelectedListener();
        return rootView;
    }
    /*
    public void setupBookSelectedListener() {
        lv.setOnClickListener(new AdapterView.OnItemClickListener() {
            ;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(this, itemBookActivity.class);
                intent.putExtra("book", bookAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
    */
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
