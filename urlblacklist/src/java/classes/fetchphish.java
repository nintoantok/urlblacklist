package classes;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Home
 */
public class fetchphish {
    public static void main(String args[]){
	//dataStore db=new dataStore();
        String tablename="PHISHDAT";
	ArrayList<URIBox> dat=new ArrayList<URIBox>();
   //get value from xml file to arraylist
        dat=phishtank.getPhishTank(null);
   // store values to database table phishdat
         dataStore.insertUrl(dat,tablename);
        //Iterator<URIBox> iterator = dat.iterator();
        //URIBox next = iterator.next();
        //String uRI = next.getURI();
        //System.out.println(uRI);
	//obj.cache();
	
    }

}
