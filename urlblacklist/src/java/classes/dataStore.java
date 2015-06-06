package classes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Home
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class dataStore
{
    private static String dbURL = "jdbc:derby://localhost:1527/phish;user=phishdet;password=phishdet";
    //private static String tableName = "PHISHDAT";
     //jdbc Connection;
    private static Connection conn = null;
    private static Statement stmt = null;

   

    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL);
            System.out.println("connection successes");
        }
        catch (Exception except)
        {
        }
    }
    static void insertUrl(String Url,String tableName){
        try {
            createConnection();
            stmt = conn.createStatement();
          
            stmt.execute("insert into " + tableName + " (URL) values (" + "'" + Url + "'" + ")");
            System.out.println("insertion success");
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(dataStore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void insertUrl_cred(String Url,String userName,String passWord,String tableName){
        try {
            createConnection();
            stmt = conn.createStatement();
            //inserts url,username and password to table. here used by filter to insert credentials and url to userprofile
           stmt.execute("insert into " + tableName + " (URL, USERNAME, PASSWORD) values (" + "'" + Url + "'"+ ", '" + userName + "'" +", '" + passWord + "'" + ")");
            System.out.println("insertion success");
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(dataStore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void insertUrl(ArrayList<URIBox> dat,String tableName)
    {

        try
        {
          createConnection();
          Iterator<URIBox> iterator = dat.iterator();
          for(int i=0;i<dat.size();i++){
          URIBox next = iterator.next();
        
          String Url = next.getURI();
        
           // System.out.println(Url);
        
            stmt = conn.createStatement();
             //inserts url to table. here used by fetchphish to insert suspected urls to phishdat
            stmt.execute("insert into " + tableName + " values ("+"'" + Url + "'"+")");
            System.out.println("insertion success");
            stmt.close();
            }
        }
        catch (SQLException sqlExcept)
        {
            System.out.println("xception");
        }
    }
    static ResultSet getUrl(String tableName)
    {
        ResultSet results = null;
        try {
            createConnection();
            stmt = conn.createStatement();
        //gets url from table. here used by fetchphish and userverify to check whether url is already present in phishdat and in userprofile
            results = stmt.executeQuery("select URL from " + tableName);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(dataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
       //resultset is returned which contains whole results
         return results;
       

    }
    static ResultSet getUrl_cred(String tableName)
    {
        
           
       ResultSet results = null;
        try {
            createConnection();
            stmt = conn.createStatement();
            //gets url and credentials from table. here used by userverify to check binding relationship in userprofile
            results = stmt.executeQuery("select * from " + tableName);


        } catch (SQLException ex) {
            Logger.getLogger(dataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
//resultset is returned which contains whole results
         return results;


    }

    private static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        }
        catch (SQLException sqlExcept)
        {

        }

    }
}



