/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enitities.Post;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author philippnienhuser
 */
public class PostDTO implements Serializable{

    public static PostDTO toPOJO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private long id;
    private String url;
    private String comment;
    private String creator;
    private int totalRating;
    private Map<String,Integer> ratings;

    public PostDTO(){}
    
    public PostDTO(String url, String comment, String creator, int totalRating, Map<String,Integer> ratings){
        this.url = url;
        this.comment = comment;
        this.creator = creator;
        this.totalRating = totalRating;
        this.ratings = ratings;
    }
    
    public PostDTO(long id, String url, String comment, String creator, int totalRating, Map<String,Integer> ratings){
        this.id =id;
        this.url = url;
        this.comment = comment;
        this.creator = creator;
        this.totalRating = totalRating;
        this.ratings = ratings;
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
    
    public JsonObject toJsonObject(){
        JsonObjectBuilder js = Json.createObjectBuilder();
        Gson gson = new Gson();
        String tmp = gson.toJson(this.ratings);
        js.add("id", this.id)
                .add("url", this.url)
                .add("comment", this.comment)
                .add("creator", this.creator)
                .add("totalRating", this.totalRating)
                .add("ratings", tmp);
        return js.build();
    }
    
    public static PostDTO toPOJO(JsonObject js){
        Gson gson = new Gson();
        Type listType = new TypeToken<Map<String, Thread>>() {
		}.getType();
        
        PostDTO p = new PostDTO();
        p.setId(js.getJsonNumber("id").longValue());
        p.setUrl(js.getString("url"));
        p.setComment(js.getString("comment"));
        p.setCreator(js.getString("creator"));
        p.setTotalRating(js.getInt("totalRating"));
        p.setRatings(gson.fromJson(js.getString("rating"),listType));
        return p;
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

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Integer> ratings) {
        this.ratings = ratings;
    }
    
    
    
}
