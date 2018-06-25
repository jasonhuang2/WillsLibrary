package com.example.jasonhuang.willslibrary;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class rentEligibilityChecker {
    //NOTE: Fill these attributes before you execute this program
    //NOTE: For ip address, if you are provided with a port number, the format will be "ipaddress:portnumber"
    String ip = DBlocationConstants.getip();    //enter ip address here
    String db = DBlocationConstants.getdb();    //emter database name here
    String un = DBlocationConstants.getun();    //enter username here
    String pass = DBlocationConstants.getps();  //enter password here
    Connection conn;


    /**
     * This function checks if the username is allowed to rent or not
     * @param username
     * @return 0 If user is NOT able to rent (has overdue items)
     * Returns 1 if user is ABLE to rent
     */
    public int check(String username){
        conn = connectionclass(un, pass, db, ip);
        Log.d("RentCHecker" ,"I have been ran!!@@@");

        String checkIfOverDueQuery = "SELECT * FROM DB_A3C994_will.dbo.fee WHERE paid = 'false' AND u_username='" + username + "';";

        try{
            Statement checkOverDueStatement = conn.createStatement();
            ResultSet overdueRS = checkOverDueStatement.executeQuery(checkIfOverDueQuery);
            if(overdueRS.next()){
                //THEN IT IS OVERDUE
                return 0;
            }else{
                return 1;
            }
        }catch(SQLException e){

        }
        //This return doesn't really mean anything lol
        return 0;
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
