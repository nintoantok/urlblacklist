package classes;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Home
 */
import classes.phishverify;
import classes.userverify;
import classes.dataStore;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

public class filter implements Filter
{
  private  HttpServletRequest requests;
  private FilterConfig filterConfig = null;
  private  HttpServletResponse responses;

    @Override
  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)
    throws IOException, ServletException
  {
        JOptionPane.showMessageDialog(null, "filter began");
        requests=(HttpServletRequest) request;
        responses=(HttpServletResponse) response;
        boolean phish,presence,match_presence[]=new boolean[2],dochain=true;
        String user=requests.getParameter("id");
        String pass=requests.getParameter("password");

        String url = requests.getRequestURL().toString();
        //to check whether url is already present-to get a measure of legitimacy
        presence=userverify.verify(url);
        if(!presence){
            JOptionPane.showMessageDialog(null, "this url is not present in profile");

        }
        //check whether url is suspected phish
        phish=phishverify.verify(url);
        //if yes give warning and wait for prompt to continue
        if(phish){
            int phish_choice = JOptionPane.showConfirmDialog(null, "this url is a suspected phish do you want to continue?", "Phish Suspect", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(phish_choice==JOptionPane.NO_OPTION){
                //if user dont want to continue mention it.
                dochain=false;

            }
        }
//if no login credentials are present, we may ignore check, also if user found it a suspected phish
 //and chose not to continue
        if(user!=null & dochain){
            match_presence=userverify.logcred_verify(url, user, pass);
            //check if credentials are present in userprofile. if not we may ignore check.
            if(match_presence[1]){
                //if present check for binding relationship
                if(!match_presence[0]){
                    //if it breaks give warning
                    int phish_choice = JOptionPane.showConfirmDialog(null, "this login credentials belong to another thus breaks binding relation Url do you sure to continue?", "Binding Violation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(phish_choice==JOptionPane.NO_OPTION){
                 //if user chose not to continue
                    dochain=false;
                    }
                }
            }
       }
        JOptionPane.showMessageDialog(null, "filter ended");
        //if user chose to continue pass control to servlet
        if(dochain){
            //insert currentcredentials and url to userprofile since user believes it or it didnt broke binding relationships
             dataStore.insertUrl_cred(url,user,pass,"USERPROFILE");
             chain.doFilter(request, response);
        }
        else{
//if user chose not to continue redirect to homepage
            responses.reset();
            responses.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            responses.setHeader("Location", "http://localhost:8080/phish/home.html");
         }
    }
    @Override
  public void destroy() { }

    @Override
  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }
}
