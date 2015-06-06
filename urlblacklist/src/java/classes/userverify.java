package classes;


import classes.dataStore;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Home
 */
public class userverify {


    public static boolean verify(String Urll){
        try {
            String tablename = "userprofile";
            String Url=null;
            ResultSet results = dataStore.getUrl(tablename);

            //checking whether the url is already present in userprofile
            while (results.next()) {
                try {
                    Url = results.getString("URL");
                    if(Urll.equals(Url)){
                     System.out.println(Url);
                     //if present return true value,conditions are checked in filter
                     return true;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(userverify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            results.close();

        } catch (SQLException ex) {
            Logger.getLogger(phishverify.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }

    public static boolean[] logcred_verify(String Urll,String userName,String passWord){
        boolean[] match_presence={false,false};
        try {
            String tablename = "userprofile";
            ResultSet results = dataStore.getUrl_cred(tablename);
            ResultSetMetaData rsmd = results.getMetaData();

            //int numberCols = rsmd.getColumnCount();

            while (results.next()) {
                try {
                    //getting data columns from resultset in a row
                    String Url = results.getString(1);
                    String username = results.getString(2);
                    String password = results.getString(3);
                  //  System.out.println(Url + "\t\t" + username + "\t\t" + password);
   //check if username and password is already present. if its a new set we need not worry
                    if (username.equals(userName) && password.equals(passWord)) {
   // if username and password is present we should check match. if not a match it breaks binding rules
                        if (Urll.equals(Url)) {
                            match_presence[0]=true; //match_presence[0] stands for match
                            match_presence[1]=true; //match_presence[1] stands for presence
                            }
                        else{
                            match_presence[1]=true;
                        }

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(userverify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(userverify.class.getName()).log(Level.SEVERE, null, ex);
        }
         //return combination conditions are checked in filter
        return match_presence;
    }
}
