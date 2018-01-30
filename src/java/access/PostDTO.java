/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import enitities.Post;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author philippnienhuser
 */
public class PostDTO implements Serializable{
    
    private long id;
    private String url;
    private String comment;
    private String creator;
    private int totalRating;
    private Map<String,Integer> ratings;

    public PostDTO(){}

    public PostDTO(String url, String comment, String creator, int totalRating, Map<String,Integer> ratings) {
        this.url = url;
        this.comment = comment;
        this.creator = creator;
        this.totalRating = totalRating;
    }
    
    public PostDTO(long id, String url, String comment, String creator, int rating, Map<String,Integer> ratings){
        this.url = url;
        this.comment = comment;
        this.creator = creator;
        this.totalRating = rating;
    }
    
    public static PostDTO toPostDTO(Post p){
        if(p == null){
            return null;
        }
        return new PostDTO(p.getId(), p.getUrl(), p.getComment(), p.getCreator(), p.getTotalRating(), p.getRatings());
    }
    
    public Post toPost(){
        return PostBuilder.create().id(this.id).url(this.url).comment(this.comment)
                .creator(this.creator).totalRating(this.totalRating).ratings(this.ratings).build();
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

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }
    
    
}
