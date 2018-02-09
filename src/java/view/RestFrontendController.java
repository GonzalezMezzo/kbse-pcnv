/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.AvatarDTO;
import access.CommentDTO;
import access.PostDTO;
import access.RatingDTO;
import access.SystemUserDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author philippnienhuser
 */
@Dependent
public class RestFrontendController implements Serializable {

    private final static String ADRESS = "http://localhost:8080/kbse-pcnv/r";

    private Client client;
    private WebTarget wt;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.wt = client.target(ADRESS);
    }

    boolean deletePost(long id) {
        this.wt = client.target(ADRESS + "/deletePost/" + id);

        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        try {
            JsonObject res = build.delete(JsonObject.class);
            return res.getBoolean("success");
        } catch (Exception e) {
            System.out.println("deletePost(rest) ->");
            return false;
        }
    }

    public List<PostDTO> refreshState() {
        this.wt = client.target(ADRESS + "/refreshState");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        try {
            JsonObject res = build.get(JsonObject.class);
            Type listType = new TypeToken<ArrayList<PostDTO>>() {
            }.getType();
            return (List<PostDTO>) gson.fromJson(res.getString("list"), listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void updateRatings(List<PostDTO> postList) {
        this.wt = client.target(ADRESS + "/updateRatings");

    }

    boolean addComment(CommentDTO comment) {
        this.wt = client.target(ADRESS + "/addComment");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(comment.toJsonObject(), MediaType.APPLICATION_JSON);
        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("addComment(rest) ->");
        }
        return false;
    }

    public boolean addComment(CommentDTO comment, PostDTO currentPost, SystemUserDTO currentUser) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("comment", comment.toJsonObject()).add("post", currentPost.toJsonObject()).add("user", currentUser.toJsonObject());

        this.wt = client.target(ADRESS + "/addComment");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(builder.build(), MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            System.out.println("addComment(rest) ->");
        }
        return false;
    }
    
     public boolean addRating(PostDTO post, RatingDTO rating, SystemUserDTO user) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("post", post.toJsonObject()).add("rating", rating.toJsonObject()).add("user", user.toJsonObject());

        this.wt = client.target(ADRESS + "/addRating");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(builder.build(), MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            System.out.println("addRating(rest) ->");
        }
        return false;
    }

    public boolean addAvatar(AvatarDTO avatarDTO) {
        this.wt = client.target(ADRESS + "/addAvatar");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(avatarDTO.toJsonObejct(), MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            System.out.println("addAvatar (rest) ->");
        }
        return false;
    }

    public boolean addSystemUser(SystemUserDTO user) {
        this.wt = client.target(ADRESS + "/addSystemUser");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(user.toJsonObject(), MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            System.out.println("addSystemUer(rest) ->");
        }
        return false;
    }

    public boolean addPost(PostDTO post, SystemUserDTO currentUser) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("post", post.toJsonObject()).add("user", currentUser.toJsonObject());

        this.wt = client.target(ADRESS + "/addPost");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(builder.build(), MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            System.out.println("addPost(rest) ->");
        }
        return false;
    }

    List<PostDTO> getPostList() {
        this.wt = client.target(ADRESS + "/getPostList");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        try {
            JsonObject res = build.get(JsonObject.class);
            Type listType = new TypeToken<ArrayList<PostDTO>>() {
            }.getType();
            return (List<PostDTO>) gson.fromJson(res.getString("list"), listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    List<SystemUserDTO> getUserList() {
        this.wt = client.target(ADRESS + "/getUserList");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        try {
            JsonObject res = build.get(JsonObject.class);
            Type listType = new TypeToken<ArrayList<PostDTO>>() {
            }.getType();
            return (List<SystemUserDTO>) gson.fromJson(res.getString("list"), listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    PostDTO getPost(long postId) {
        this.wt = client.target(ADRESS + "/getPost/" + postId);
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.get(JsonObject.class);
            return PostDTO.toPOJO(res);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    SystemUserDTO getSystemUser(String username) {
        this.wt = client.target(ADRESS + "/getSystemUser/" + username);
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.get(JsonObject.class);
            return SystemUserDTO.toPOJO(res.getJsonObject("user"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
}
