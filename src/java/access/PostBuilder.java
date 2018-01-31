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
public class PostBuilder implements Serializable{
    
    private long id;
    private String url;
    private String comment;
    private String creator;
    private int totalRating;
    private Map<String,Integer> ratings;
    
    private PostBuilder(){};
    
    public static PostBuilder create(){
        return new PostBuilder();
    }
    
    public PostBuilder id(long id){
        this.id = id;
        return this;
    } 
    
    public PostBuilder url(String url){
        this.url = url;
        return this;
    }
    
    public PostBuilder comment(String comment){
        this.comment = comment;
        return this;
    }
    
    public PostBuilder creator(String creator){
        this.creator = creator;
        return this;
    }
    
    public PostBuilder totalRating(int rating){
        this.totalRating = rating;
        return this;
    }
    
    public PostBuilder ratings(Map<String,Integer> ratings){
        this.ratings = ratings;
        return this;
    }
    
    public Post build(){
        Post res = new Post();
        //res.setId(this.id);
        res.setUrl(this.url);
        res.setComment(this.comment);
        res.setCreator(this.creator);
        res.setTotalRating(this.totalRating);
        res.setRatings(this.ratings);
        return res;
    }
}
