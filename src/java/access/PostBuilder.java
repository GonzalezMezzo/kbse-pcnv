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
public class PostBuilder implements Serializable{
    
    private long id;
    private String url;
    private String comment;
    private String creator;
    private int rating;
    
    private PostBuilder(){};
    
    public static PostBuilder create(){
        return new PostBuilder();
    }
    
    public PostBuilder id(int id){
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
    
    public PostBuilder rating(int rating){
        this.rating = rating;
        return this;
    }
    
    public Post build(){
        Post res = new Post();
        res.setId(this.id);
        res.setUrl(this.url);
        res.setComment(this.comment);
        res.setCreator(this.creator);
        res.setRating(this.rating);
        return res;
    }
}
