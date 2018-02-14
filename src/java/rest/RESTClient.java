/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import access.DTO.AvatarDTO;
import access.DTO.CommentDTO;
import access.DTO.PostDTO;
import access.DTO.RatingDTO;
import access.DTO.SystemUserDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
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
public class RESTClient implements Serializable {

    private final static String ADRESS = "http://localhost:8080/kbse-pcnv/r";

    private Client client;
    private WebTarget wt;

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
        this.wt = client.target(ADRESS);
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

    /**
     * Sends POST request containig the CommentDTO, corresponding PostDTO and
     * the SystemUserDTO to the "/addComments" route
     *
     * @param comment the comment to be persisted
     * @param currentPost post the comment belongs to
     * @param currentUser user who created the comment
     * @return boolean indicating the success or failure
     */
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

    /**
     * Sends POST request containing the SystemUserDTO and the PostDTO to be
     * persisted to the "addPost" route
     *
     * @param post post to be persisted
     * @param currentUser the user who created the new post
     * @return boolean indicating the success or failure
     */
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

    /**
     * Sends POST request containing the SystemUserDTO, RatingDTO and the
     * PostDTO this rating belongs to to the "addRating" route
     *
     * @param post post the rating need to be added to
     * @param rating the rating to be persisted
     * @param user the user this rating belongs to
     * @return boolean indicating the success or failure
     */
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

    /**
     * Sends POST request containing submitted userdata to the "addSystemUser"
     * route
     *
     * @param user user object to be persisted
     * @return boolean indicating the success or failure
     */
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

    /**
     * Sends DELETE request containing the id of the post to be deleted from the
     * database
     *
     * @param id id of the post to be deleted
     * @return boolean indicating the success or failure
     */
    public boolean deletePost(long id) {
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

    /**
     * Sends DELETE request containing the unique username of the user whose
     * ratings will be removed from the database
     *
     * @param userName username of the SystemUser whose ratings will be removed
     * @return boolean indicating the success or failure
     */
    public boolean deleteRating(String userName) {
        this.wt = client.target(ADRESS + "/deleteRating/" + userName);
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        try {
            JsonObject res = build.delete(JsonObject.class);
            return res.getBoolean("success");
        } catch (Exception e) {
            System.out.println("deleteRating(rest) ->");
            return false;
        }
    }

    public AvatarDTO getAvatar(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sends GET request to the "getPost/{postId}" route requesting the curretn
     * state of the post object with given id
     *
     * @param postId id of the Post
     * @return PostDTO representing the current state of the Post object with
     * requested id
     */
    public PostDTO getPost(long postId) {
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

    /**
     * Sends GET request to the "getPostList" route
     *
     * @return List<PostDTO> containing the current state of every Post object
     * in the database
     */
    public List<PostDTO> getPostList() {
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

    /**
     * Sends GET request to the "getUser/{username}" route requesting the
     * current state of the SystemUser object with given username
     *
     * @param username username of the SystemUSer
     * @return SystemUserDTO representing the current state of the user with
     * requested id
     */
    public SystemUserDTO getSystemUser(String username) {
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

    /**
     * Sends GET request to the "getUserList" route
     *
     * @return List<SystemUserDTO> containing the current state of every
     * SystemUser object in the database
     */
    public List<SystemUserDTO> getUserList() {
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

    /**
     * Sends POST request containing submitted userdata to the
     * "updateSystemUser" route
     *
     * @param user user object to be updated
     * @return boolean indicating the success or failure
     */
    public boolean updateSystemUser(SystemUserDTO user) {
        this.wt = client.target(ADRESS + "/updateSystemUser");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(user.toJsonObject(), MediaType.APPLICATION_JSON);

        try {
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");

        } catch (Exception e) {
            System.out.println("updateSystemUer(rest) ->");
        }
        return false;
    }

}
