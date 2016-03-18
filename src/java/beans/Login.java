/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author c0660563
 */
@ManagedBean
@SessionScoped
public class Login {

    private String username;
    private String password;
    private boolean loggedIn;
    private User currentUser;
    
    public Login() {
        username = null;
        password = null;
        loggedIn = false;
        currentUser = null;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String doLogin() {
        String passhash = Utils.hash(password);
        for(User u : UserController.getInstance().getUsers()) {
            if (username.equals(u.getUsername()) && passhash.equals(u.getPasshash())) {
                loggedIn = true;
                currentUser = u;
                return "editPost";
            }
        }
        currentUser = null;
        loggedIn = false;
        return "viewPost";
    }
   
}
