/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author c0660563
 */
public class User {
    /*
    CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    passhash VARCHAR(255) NOT NULL
    );
    */
    private int id;
    private String username;
    private String passhash;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public User(int id, String username, String passhash) {
        this.id = id;
        this.username = username;
        this.passhash = passhash;
    }
    
}
