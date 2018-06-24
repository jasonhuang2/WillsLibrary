package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.Duration;

public class myfinesMainActivity extends AppCompatActivity {

    private String overDueList, username;
    Connection conn;
    String un, pass, db, ip;
    double balanceDue;
    private String amountOfMoneyInAccount;

    String amountDueString, walletBalanceString, feeID;
    double amountDueDouble;
    double walletBalance;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //NOTE: Fill these attributes before you execute this program
        //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
        ip = "sql5004.site4now.net:1433";    //enter ip address here
        db = "DB_A3C994_will";    //emter database name here
        un = "DB_A3C994_will_admin";    //enter username here
        pass = "willslibrary1";  //enter password here

        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database



        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfines_activity);
        setTitle("My Fines");


        username = getIntent().getStringExtra("username");
        TextView noOverDueText = (TextView)findViewById(R.id.noOverDueText);
        TextView overdueText = (TextView)findViewById(R.id.overdueText);
        TextView overdueText2 = (TextView)findViewById(R.id.overdueText2);
        TextView overdueListTextView = (TextView) findViewById(R.id.overdueItemList);
        TextView walletBalanceTextView = (TextView) findViewById(R.id.walletBalance);
        TextView amountDueInput = (TextView) findViewById(R.id.amountDueInput);
        TextView amountDueText = (TextView) findViewById(R.id.amountDueText);
        Button payButton = (Button)findViewById(R.id.payButton);



        try{
            String checkIfOverDueQuery = "SELECT * FROM DB_A3C994_will.dbo.fee WHERE paid = 'false' AND u_username='" + username + "';";
            Statement checkOverDueStatement = conn.createStatement();
            ResultSet overdueRS = checkOverDueStatement.executeQuery(checkIfOverDueQuery);

            if(overdueRS.next()){
                // IT IS OVERDUE
                overDueList =  overdueRS.getString("i_item_id");
                feeID = overdueRS.getString("fee_id");

                overdueText.setVisibility(View.VISIBLE);
                overdueText2.setVisibility(View.VISIBLE);
                overdueListTextView.setVisibility(View.VISIBLE);
                overdueListTextView.setText(overDueList);
                amountDueInput.setVisibility(View.VISIBLE);
                amountDueText.setVisibility(View.VISIBLE);
                payButton.setVisibility(View.VISIBLE);

                String payQuery = "SELECT amount_due FROM fee WHERE u_username='" + username + "';";
                Statement payQueryStatement = conn.createStatement();
                ResultSet payRS = payQueryStatement.executeQuery(payQuery);

                if(payRS.next()){
                    amountDueInput.setText("$" + payRS.getString("amount_due"));
                    amountDueString = payRS.getString("amount_due");
                }




            }else{
                noOverDueText.setVisibility(View.VISIBLE);
            }

            String walletBalance = "SELECT amount_of_money FROM DB_A3C994_will.dbo.users WHERE username='" + username +"';";
            Statement walletStatement = conn.createStatement();
            ResultSet walletRS = walletStatement.executeQuery(walletBalance);

            if(walletRS.next()){
                walletBalanceTextView.setText("$" + walletRS.getString("amount_of_money"));
                if(Double.parseDouble(walletRS.getString("amount_of_money")) <= 0){
                    walletBalanceTextView.setTextColor(Color.RED);
                }
                else
                    walletBalanceTextView.setTextColor(Color.BLACK);

                walletBalanceString = walletRS.getString("amount_of_money");
            }

        }catch(SQLException e){

        }

    }





    public void payButtonListener(View v){
        TextView noOverDueText = (TextView)findViewById(R.id.noOverDueText);
        TextView overdueText = (TextView)findViewById(R.id.overdueText);
        TextView overdueText2 = (TextView)findViewById(R.id.overdueText2);
        TextView overdueListTextView = (TextView) findViewById(R.id.overdueItemList);
        TextView walletBalanceTextView = (TextView) findViewById(R.id.walletBalance);
        TextView amountDueInput = (TextView) findViewById(R.id.amountDueInput);
        TextView amountDueText = (TextView) findViewById(R.id.amountDueText);
        Button payButton = (Button)findViewById(R.id.payButton);

        amountDueDouble = Double.parseDouble(amountDueString);
        walletBalance = Double.parseDouble(walletBalanceString);

        walletBalance = walletBalance - amountDueDouble;

        overdueText.setVisibility(View.INVISIBLE);
        overdueText2.setVisibility(View.INVISIBLE);
        overdueListTextView.setVisibility(View.INVISIBLE);
        amountDueInput.setVisibility(View.INVISIBLE);
        amountDueText.setVisibility(View.INVISIBLE);
        payButton.setVisibility(View.INVISIBLE);

        noOverDueText.setVisibility(View.VISIBLE);

        conn = connectionclass(un, pass, db, ip);   //I need this so I can query to the database

        walletBalanceTextView.setText("$" + Double.toString(walletBalance));


        String updateBalanceQuery = "UPDATE fee SET paid ='True' WHERE fee_id='"+feeID+"';";
        try{
            Statement updateBalanceStnt = conn.createStatement();
            updateBalanceStnt.executeQuery(updateBalanceQuery);

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
}
