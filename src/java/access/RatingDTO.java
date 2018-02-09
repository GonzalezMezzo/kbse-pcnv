/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import entities.Rating;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 *
 * @author nolde
 */
public class RatingDTO implements Serializable {

    private Long id;
    private int ratedValue;
    private SystemUserDTO user;
    
    
    private PostDTO post;
    
    public RatingDTO(){}

    public RatingDTO(Long id, int ratedValue, SystemUserDTO user) {
        this.id = id;
        this.ratedValue = ratedValue;
        this.user = user;
        this.post = post;
    }

    public RatingDTO(int ratedValue, SystemUserDTO user, PostDTO post) {
        this.ratedValue = ratedValue;
        this.user = user;
        this.post = post;
    }
       
    public static RatingDTO toRatingDTO(Rating r) {
         return new RatingDTO(r.getId(),r.getRatedValue(),SystemUserDTO.toSystemUserDTO(r.getUser()));
    }
    
    public Rating toRating() {
        return RatingBuilder.create().ratedValue(this.ratedValue).user(user.toSystemUser())/*.post(post.toPost())*/.build();
    }

    public SystemUserDTO getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public int getRatedValue() {
        return ratedValue;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRatedValue(int ratedValue) {
        this.ratedValue = ratedValue;
    }

    public void setUser(SystemUserDTO user) {
        this.user = user;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }
    
    

    public JsonObject toJsonObject() {
        JsonObjectBuilder js = Json.createObjectBuilder();
        if (this.id != null) {
            js.add("id", this.id);
        }
        js.add("ratedValue", this.ratedValue)
                .add("user", this.user.toJsonObject())
                .add("post", this.post.toJsonObject());
        return js.build();
    }
    
    public static RatingDTO toPOJO(JsonObject js) {
        RatingDTO r = new RatingDTO();
        
        if (js.getJsonNumber("id") != null) {
            r.setId(js.getJsonNumber("id").longValue());
        }
        r.setRatedValue(js.getInt("ratedValue"));
        r.setUser(SystemUserDTO.toPOJO(js.getJsonObject("user")));
        r.setPost(PostDTO.toPOJO(js.getJsonObject("post")));
        return r;
    }
    
}
