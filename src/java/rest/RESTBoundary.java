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
import controller.ModelController;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author philippnienhuser
 */
@Stateless
@Path("")
public class RESTBoundary {

    @Inject
    ModelController mdlctrl;

    @Context
    private UriInfo uriInfo;

    /**
     * adds a Post to the Model.Uses /addPost Route
     * @param jo PostDTO as JsonObject to be added to the Model
     * @return boolean as a JsonObject
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addPost")
    public JsonObject addPost(JsonObject jo) {
        PostDTO p = PostDTO.toPOJO(jo.getJsonObject("post"));
        SystemUserDTO u = SystemUserDTO.toPOJO(jo.getJsonObject("user"));
        return Json.createObjectBuilder().add("success", mdlctrl.addPost(p, u)).build();
    }
    
    /**
     * adds an AvatarImage to the Model. Uses /addAvatar route.
     * @param jo AvatarDTO as JsonObject to be added to the Model
     * @return boolean as a JsonObject
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addAvatar")
    public JsonObject addAvatar(JsonObject jo){
        AvatarDTO avatar = AvatarDTO.toPOJO(jo);
        return Json.createObjectBuilder().add("success", mdlctrl.addAvatar(avatar)).build();
    }
    
    /**
     * adds a SystemUser to the Model. Uses /addSystemUser route.
     * @param jo SystemUserDTO as JsonObject to be added to the Model.
     * @return boolean as JsonObject.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addSystemUser")
    public JsonObject addSystemUser(JsonObject jo){
        SystemUserDTO user = SystemUserDTO.toPOJO(jo);
        return Json.createObjectBuilder().add("success", mdlctrl.addSystemUser(user)).build();
    }
    
    /**
     * Updates new SystemUserdata with the model. Uses /updateSystemUser route.
     * @param jo SystemUserDTO as a JsonObject
     * @return boolean as JsonObject.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateSystemUser")
    public JsonObject updateSystemUser(JsonObject jo){
        SystemUserDTO user = SystemUserDTO.toPOJO(jo);
        return Json.createObjectBuilder().add("success", mdlctrl.updateSystemUser(user)).build();
    }

    /**
     * Deletes a Post from the Model. uses /deletePost/{id} route.
     * @param id id of the Post
     * @return  boolean as JsonObject
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletePost/{id}")
    public JsonObject deletePost(@PathParam("id") long id) {
        return Json.createObjectBuilder().add("success", mdlctrl.deletePost(id)).build();
    }
    
    /**
     * deletes  Ratings of given User. Uses /deleteRating/{username} route.
     * @param userName which Rating should be deleted.
     * @return boolean as JsonObject
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteRating/{userName}")
    public JsonObject deleteRating(@PathParam("userName") String userName) {
        return Json.createObjectBuilder().add("success", mdlctrl.deleteRating(userName)).build();
    }
    /**
     * Adds a Comment to the Model. uses /addComment route.
     * @param jo CommentDTO as jsonObject
     * @return boolean as JsonObject
     */


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addComment")
    public JsonObject addComment(JsonObject jo) {
        CommentDTO c = CommentDTO.toPOJO(jo.getJsonObject("comment"));
        PostDTO p = PostDTO.toPOJO(jo.getJsonObject("post"));
        SystemUserDTO u = SystemUserDTO.toPOJO(jo.getJsonObject("user"));
        mdlctrl.addComment(c, p, u);
        return Json.createObjectBuilder().add("success", true).build();
    }
    
    /**
     * adds a Rating to the Model.Uses /addRating route.
     * @param jo jsonObect containing RatingDTO, PostDTO and SystemUserDTO classes
     * @return boolean as jsonObject
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addRating")
    public JsonObject addRating(JsonObject jo) {
        RatingDTO r = RatingDTO.toPOJO(jo.getJsonObject("rating"));
        PostDTO p = PostDTO.toPOJO(jo.getJsonObject("post"));
        SystemUserDTO u = SystemUserDTO.toPOJO(jo.getJsonObject("user"));
        mdlctrl.addRating(p, r, u);
        return Json.createObjectBuilder().add("success", true).build();
    }

    /**
     * returns all Posts in a List as a JsonObject.Uses /getPostList route.
     * @return List of PostDTO objects as a JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPostList")
    public JsonObject getPostList() {
        mdlctrl.refreshState();
        List<PostDTO> list = mdlctrl.getPostList();
        Gson gson = new Gson();
        return Json.createObjectBuilder().add("list", gson.toJson(list)).build();
    }

    /**
     * returns all User in a List as a jsonObject.Uses /getUserList route
     * @return List of systemUserDTO objects as a jsonObject.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUserList")
    public JsonObject getUserList() {
        mdlctrl.refreshState();
        List<SystemUserDTO> list = mdlctrl.getUserList();
        Gson gson = new Gson();
        return Json.createObjectBuilder().add("list", gson.toJson(list)).build();
    }
    
    /**
     * Returns a SystemUser with given Username String as a jsonObject.Uses /getSystemUser/{username} route.
     * @param username SystemUsername to be searched for
     * @return SystemUserDTO as jsonObject.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSystemUser/{username}")
    public JsonObject getSystemUser(@PathParam("username")String username){
        SystemUserDTO user = mdlctrl.getSystemUser(username);
        return Json.createObjectBuilder().add("user", user.toJsonObject()).build();
    }
    
    /**
     * Returns a PostDTO with given ID as a jsonObject.Uses /getPost/{post} route.
     * @param postId belonging to the post 
     * @return PostDTO as jsonObject.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPost/{post}")
    public JsonObject getPost(@PathParam("post")long postId){
        PostDTO post = mdlctrl.getPost(postId);
        return post.toJsonObject();
    }
    
    
}
