/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access.DTO;

import access.builder.PostBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Comment;
import entities.Post;
import entities.Rating;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author philippnienhuser
 */
public class PostDTO implements Serializable {

    private Long id;
    private String url;
    private String description;
    private SystemUserDTO creatorId;
    //private Long creatorId;
    private int totalRating;
    private List<CommentDTO> comments;
    private List<RatingDTO> ratings;
    //private List<Long> comments;
    //private List<Long> ratings;

    public PostDTO() {
    }

    public PostDTO(String url, String comment, SystemUserDTO creatorId, int totalRating, List<RatingDTO> ratings) {
        this.url = url;
        this.description = comment;
        this.comments = new ArrayList<>();
        this.creatorId = creatorId;
        this.totalRating = totalRating;
        this.ratings = ratings;
    }

    public PostDTO(Long id, String url, String comment, SystemUserDTO creatorId, int totalRating, List<RatingDTO> ratings, List<CommentDTO> comments) {
        this.id = id;
        this.description = comment;
        this.url = url;
        this.comments = comments;
        this.creatorId = creatorId;
        this.totalRating = totalRating;
        this.ratings = ratings;
    }

    public static PostDTO toPostDTO(Post p) {
        if (p == null) {
            return null;
        }
        List<CommentDTO> comments = new ArrayList<>();
        for (Comment c : p.getComments()) {
            comments.add(CommentDTO.toCommentDTO(c));
        }
        List<RatingDTO> ratings = new ArrayList<>();
        for (Rating rating : p.getRatings()) {
            ratings.add(RatingDTO.toRatingDTO(rating));
        }

        return new PostDTO(p.getId(), p.getUrl(), p.getDescription(), SystemUserDTO.toSystemUserDTO(p.getAuthor()), p.getTotalRating(), ratings, comments);
    }

    public Post toPost() {
        ArrayList<Rating> ratings = new ArrayList<>();
        for (RatingDTO rating : this.ratings) {
            ratings.add(rating.toRating());
        }
        return PostBuilder.create().url(this.url).comment(this.description).creator(this.creatorId.toSystemUser()).totalRating(this.totalRating).ratings(ratings).build();
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder js = Json.createObjectBuilder();
        Gson gson = new Gson();
        String mapString = gson.toJson(this.ratings);
        String listString = gson.toJson(this.comments);
        if (this.id != null) {
            js.add("id", this.id);
        }
        js.add("url", this.url)
                .add("description", this.description)
                .add("url", this.url)
                .add("creatorId", gson.toJson(this.creatorId, SystemUserDTO.class))
                .add("totalRating", this.totalRating)
                .add("ratings", mapString)
                .add("comments", listString);
        return js.build();
    }

    public static PostDTO toPOJO(JsonObject js) {
        Gson gson = new Gson();

        PostDTO p = new PostDTO();
        if (js.getJsonNumber("id") != null) {
            p.setId(js.getJsonNumber("id").longValue());
        }
        p.setUrl(js.getString("url"));
        p.setDescription(js.getString("description"));
        p.setCreatorId(gson.fromJson(js.getString("creatorId"), SystemUserDTO.class));
        p.setTotalRating(js.getInt("totalRating"));
        p.setComments(gson.fromJson(js.getString("comments"), new TypeToken<List<CommentDTO>>() {
        }.getType()));
        p.setRatings(gson.fromJson(js.getString("ratings"), new TypeToken<List<RatingDTO>>() {
        }.getType()));
        return p;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemUserDTO getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(SystemUserDTO creatorId) {
        this.creatorId = creatorId;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public List<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

}
