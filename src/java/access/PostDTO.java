/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import enitities.Post;
import java.io.Serializable;

/**
 *
 * @author philippnienhuser
 */
public class PostDTO implements Serializable{
    
    private long id;
    private String url;
    private String comment;
    private String creator;
    private int rating;

    public PostDTO(){}

    public PostDTO(String url, String comment, String creator, int rating) {
        this.url = url;
        this.comment = comment;
        this.creator = creator;
        this.rating = rating;
    }
    
    public PostDTO(long id, String url, String comment, String creator, int rating) {
        this.url = url;
        this.comment = comment;
        this.creator = creator;
        this.rating = rating;
    }
    
    public static PostDTO toPostDTO(Post p){
        if(p == null){
            return null;
        }
        return new PostDTO(p.getId(), p.getUrl(), p.getComment(), p.getCreator(), p.getRating());
    }
    
    public Post toPost(){
        return PostBuilder.create().id(this.id).url(this.url).comment(this.comment)
                .creator(this.creator).rating(this.rating).build();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
}
