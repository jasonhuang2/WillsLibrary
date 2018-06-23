package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class catalogueTabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Connection conn;
    String un, pass, db, ip;
    private ListView lv;
    private BookAdapter bookAdapter;
    private String username;
    public static ArrayList<Book> books = new ArrayList<Book>();
    public static ArrayList<Disk> disks = new ArrayList<Disk>();
    public static ArrayList<Other> others = new ArrayList<Other>();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_tab_activity);
        Log.d("query", "Testing Tab Activity");
        //BOOK CLIENT STUFF
        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //enter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        books.clear();
        disks.clear();
        others.clear();
        getBooks();
        getDisks();
        getOthers();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        username = getIntent().getStringExtra("username");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catalogue_tab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Deleted PlaceholderFragment class from here

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //This makes it so that only the rental button will bring us back to the main local.
        if(resultCode!=Activity.RESULT_CANCELED) {
            finish();
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    BookTab booktab = new BookTab();
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username);
                    booktab.setArguments(bundle);
                    return booktab;
                case 1:
                    DiskTab disktab = new DiskTab();
                    Bundle diskbundle = new Bundle();
                    diskbundle.putString("username", username);
                    disktab.setArguments(diskbundle);
                    return disktab;
                case 2:
                    OtherTab othertab = new OtherTab();
                    Bundle otherbundle = new Bundle();
                    otherbundle.putString("username", username);
                    othertab.setArguments(otherbundle);
                    return othertab;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return "Books";
                case 1:
                    return "Disks";
                case 2:
                    return "Other Items";
            }
            return null;
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
    public void getBooks(){
        //Log.d("query", "Called getBooks");
        //Getting all the information of book database
        String bookquery = "SELECT * FROM DB_A3C994_will.dbo.book;";
        String imagequery = "SELECT image FROM DB_A3C994_will.dbo.item WHERE b_isbn IS NOT NULL;";


        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(bookquery);
            //Log.d("query", "Sent book query");
            //Log.d("query", "Getting response from Book Query");
            int numbooks = rs.getFetchSize();
            while (rs.next()) {
                Book book = new Book();
                book.setBookISBN(rs.getString(1));
                book.setBookTitle(rs.getString(2));
                book.setBookGenre(rs.getString(3));
                book.setPublisher(rs.getString(4));
                book.setPublishingDate(rs.getString(5));
                book.setDescription(rs.getString(6));

                //This part is for the status of the book.
                String statusquery = "SELECT * FROM DB_A3C994_will.dbo.item WHERE b_isbn='"+ book.getBookISBN()+"' ORDER BY status asc;";
                Statement st2 = conn.createStatement();
                ResultSet statusrs = st2.executeQuery(statusquery);

                if(statusrs.next()){
                    book.setStatus(statusrs.getString(3));
                    book.setItemNum(statusrs.getInt(1));
                }

                //This part is for the author(s) of the book.
                String authorquery = "SELECT * FROM DB_A3C994_will.dbo.author WHERE b_isbn='"+ book.getBookISBN()+"';";
                Statement authorStatmt = conn.createStatement();
                ResultSet rsauthor = authorStatmt.executeQuery(authorquery);
                ArrayList<String> authorarray = new ArrayList<String>();

                while(rsauthor.next()){
                    authorarray.add(rsauthor.getString("author_name"));
                }
                String author_string = "";
                for(int i = 0; i < authorarray.size(); i++)
                {
                    author_string = author_string + authorarray.get(i) + ", ";
                }
                author_string = author_string.substring(0, author_string.length()-2);
                book.setBookAuthor(author_string);

                //Print to Log
                //Log.d("query", "Title: " + book.getBookTitle());
                //Log.d("query", "Genre: " + book.getBookGenre());
                //Log.d("query", "Status: " + book.getStatus());

                books.add(book);
            }
           // conn.close();
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookstitle);
        } catch (SQLException e) {
            Log.d("query", "Query of BookTab Failed");
        }
    }

    public static ArrayList<Book> book(){
        return books;
    }

    public void getDisks(){
        /*
        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //enter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        */
        //Getting all the information of disk database
        String diskquery = "SELECT * FROM DB_A3C994_will.dbo.disk;";
        String imagequery = "SELECT image FROM DB_A3C994_will.dbo.item WHERE d_title IS NOT NULL;";


        try {
            Statement diskst = conn.createStatement();
            ResultSet diskrs = diskst.executeQuery(diskquery);
            //Log.d("query", "Sent disk query");
            //Log.d("query", "Getting response from Disk Query");
            int numdisks = diskrs.getFetchSize();
            while (diskrs.next()) {
                Disk disk = new Disk();
                disk.setDiskTitle(diskrs.getString(1));
                disk.setDiskdatereleased(diskrs.getString(2));
                disk.setDiskGenre(diskrs.getString(3));
                disk.setDiskType(diskrs.getString(4));
                disk.setDiskDescription(diskrs.getString(5));

                //This part is for the status of the disk.
                String statusquery = "SELECT * FROM DB_A3C994_will.dbo.item WHERE d_title='"+ disk.getDiskTitle()+"' ORDER BY status asc;";
                Statement dstatusst = conn.createStatement();
                ResultSet dstatusrs = dstatusst.executeQuery(statusquery);

                if(dstatusrs.next()){
                    disk.setStatus(dstatusrs.getString(3));
                    disk.setItemNum(dstatusrs.getInt(1));
                }

                //Print to Log
                //Log.d("query", "Title: " + disk.getDiskTitle());
                //Log.d("query", "Genre: " + disk.getDiskGenre());
                //Log.d("query", "Status: " + disk.getStatus());

                disks.add(disk);
            }
           // conn.close();
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diskstitle);
        } catch (SQLException e) {
            Log.d("query", "Query of DiskTab Failed");
        }
    }
    public static ArrayList<Disk> disk(){
        return disks;
    }
    public void getOthers(){
        /*
        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //enter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here
        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database
        */
        //Getting all the information of other database
        String otherquery = "SELECT * FROM DB_A3C994_will.dbo.other;";
        String imagequery = "SELECT image FROM DB_A3C994_will.dbo.item WHERE o_other_id IS NOT NULL;";


        try {
            Statement otherst = conn.createStatement();
            ResultSet otherrs = otherst.executeQuery(otherquery);
            //Log.d("query", "Sent other query");
            //Log.d("query", "Getting response from Other Query");
            int numothers = otherrs.getFetchSize();
            while (otherrs.next()) {
                Other other = new Other();
                other.setOtherID(otherrs.getInt(1));
                other.setOtherType(otherrs.getString(2));
                other.setOtherDescription(otherrs.getString(3));

                //This part is for the status of the other.
                String statusquery = "SELECT * FROM DB_A3C994_will.dbo.item WHERE o_other_id='"+ other.getOtherID()+"' ORDER BY status asc;";
                Statement otherstatusst = conn.createStatement();
                ResultSet ostatusrs = otherstatusst.executeQuery(statusquery);

                if(ostatusrs.next()){
                    other.setStatus(ostatusrs.getString(3));
                    other.setItemNum(ostatusrs.getInt(1));
                }

                //Print to Log
                //Log.d("query", "Type: " + other.getOtherType());
                //Log.d("query", "ID: " + Integer.toString(other.getOtherID()));
                //Log.d("query", "Status: " + other.getStatus());

                others.add(other);
            }
            conn.close();
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, otherstitle);
        } catch (SQLException e) {
            Log.d("query", "Query of OtherTab Failed");
        }
    }

    public static ArrayList<Other> other(){
        return others;
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
