/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author c0660563
 */
@ApplicationScoped
@ManagedBean
public class PostController {

    private List<Post> posts;
    private Post currentPost;

    public PostController() {
        currentPost = new Post(-1, -1, "", null, "");
        updatePostsFromDatabase();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Post getCurrentPost() {
        return currentPost;
    }

    public Post getPostById(int id) {
        for (Post p : posts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    public Post getPostByUser(String username) {
        int id = UserController.getInstance().getUserIdByUsername(username);
        for (Post p : posts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }


    public Post getPostByTitle(String title) {
        for (Post p : posts) {
            if (p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }

    private void updatePostsFromDatabase() {
        try {
            //posts.clear();
            posts = new ArrayList<>();

            //Make a Connection
            Connection conn = Utils.getConnection();

            //Build a Query
            String sql = "SELECT * FROM posts";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //Parse the Results
            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getTimestamp("created_time"),
                        rs.getString("contents")
                );
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            posts = new ArrayList<>();
        }
    }

    public String viewPost(Post post) {
        currentPost = post;
        return "viewPost";
    }

    public String addPost() {
        currentPost = new Post(-1, -1, "", null, "");
        return "editPost";
    }

    public String editPost() {
        return "editPost";
    }

    public String cancelPost() {
        // currentPost can be corrupted -- reset it based on the DB
        int id = currentPost.getId();
        updatePostsFromDatabase();
        currentPost = getPostById(id);
        return "editPost";
    }
    
    public String deletePost() {
        try {
            Connection conn = Utils.getConnection();
            
            String sql = "DELETE FROM posts WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, currentPost.getId());
                pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updatePostsFromDatabase();
        return "index";
    }

    public String savePost(User user) {
        try {

            //Make a Connection
            Connection conn = Utils.getConnection();

            if (currentPost.getId() >= 0) {
                //Build a Query
                String sql = "UPDATE posts SET title = ?, contents = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, currentPost.getTitle());
                pstmt.setString(2, currentPost.getContents());
                pstmt.setInt(3, currentPost.getId());
                pstmt.executeUpdate();
            } else {
                String sql = "INSERT INTO posts (user_id, title, created_time, contents) VALUES (?, ?, NOW(), ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, user.getId());
                pstmt.setString(2, currentPost.getTitle());
                pstmt.setString(3, currentPost.getContents());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updatePostsFromDatabase();
        currentPost = getPostByTitle(currentPost.getTitle());
        return "viewPost";
    }

}
