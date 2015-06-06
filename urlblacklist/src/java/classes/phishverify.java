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
public class phishverify {

    
    
    public static boolean verify(String Urll){
        try {
            String tablename = "phishdat";
            String Url=null;
           // String Urll="http://localhost:8080/phish/verify";

            ResultSet results = dataStore.getUrl(tablename);
            ResultSetMetaData rsmd = results.getMetaData();
            //int numberCols = rsmd.getColumnCount();
            //check whether current url is present in phishdat-the suspected list
            while (results.next()) {
                try {
                    Url = results.getString("URL");
                    // String cityName = results.getString();
                    if(Urll.equals(Url)){
                    System.out.println(Url);
              //if present return true. conditions are checked in filter
                    return true;
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(phishverify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            results.close();

        } catch (SQLException ex) {
            Logger.getLogger(phishverify.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
}
