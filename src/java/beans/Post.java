/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author c0660563
 */
public class Post {
    /*
    CREATE TABLE posts (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL UNIQUE,
    created_time DATETIME NOT NULL,
    contents TEXT,
    FOREIGN KEY posts(user_id) REFERENCES users(id)
    );

    */
    
    private int id;
    private int userId;
    private String title;
    private Date createdTime;
    private String contents;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getContents() {
        return contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Post(int id, int userId, String title, Date createdTime, String contents) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.createdTime = createdTime;
        this.contents = contents;
    }

}
