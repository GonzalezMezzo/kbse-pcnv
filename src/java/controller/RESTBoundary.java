/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.PostDTO;
import enitities.Post;
import java.math.BigDecimal;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author philippnienhuser
 */
@Stateless
@Path("")
public class RESTBoundary {
    
    @Inject ModelController mdlctrl;
    
    @Context
    private UriInfo uriInfo;
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addPost")
    public JsonObject addPost(JsonObject jo){
        PostDTO p = PostDTO.toPOJO();
        return Json.createObjectBuilder().add("success", mdlctrl.addPost(p)).build();   
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletePost")
    public JsonObject deletePost(JsonObject jo){
        PostDTO p = PostDTO.toPOJO();
        return Json.createObjectBuilder().add("success", mdlctrl.deletePost(p)).build();   
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/refreshState")
    public JsonObject refreshState(JsonObject jo){
        PostDTO p = PostDTO.toPOJO();
        return null;
        //return Json.createObjectBuilder().add("success", mdlctrl.refreshState(p)).build();   
    }
}
