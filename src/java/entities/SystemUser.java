/**
 * This file was generated by the Jeddict
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author nolde
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SystemUser.findAll", query= "SELECT s FROM SystemUser s")
})
public class SystemUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Basic
    private String username;

    @Column(nullable = false, updatable = true)
    @Basic
    private String fname;

    @Column(nullable = false, updatable = true)
    @Basic
    private String lname;

    @Column(nullable = false, updatable = true)
    @Basic
    private String email;

    @JoinColumn(nullable = true, updatable = true)
    @OneToOne(targetEntity = Avatar.class)
    private Avatar avatar;

    @OneToMany(targetEntity = Post.class, mappedBy = "author")
    private List<Post> posts;

    @OneToMany(targetEntity = Comment.class, mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(targetEntity = Rating.class, mappedBy = "user")
    private List<Rating> ratings;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public List<Post> getPosts() {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        return this.posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        getPosts().add(post);
        post.setAuthor(this);
    }

    public void removePost(Post post) {
        getPosts().remove(post);
        post.setAuthor(null);
    }

    public List<Comment> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        getComments().add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(Comment comment) {
        getComments().remove(comment);
        comment.setAuthor(null);
    }

    public List<Rating> getRatings() {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        return this.ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating) {
        getRatings().add(rating);
        rating.setUser(this);
    }

    public void removeRating(Rating rating) {
        getRatings().remove(rating);
        rating.setUser(null);
    }

}