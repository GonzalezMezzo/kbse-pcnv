/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.PostDTO;
import com.google.gson.Gson;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addPost")
    public JsonObject addPost(JsonObject jo) {
        PostDTO p = PostDTO.toPOJO(jo);
        return Json.createObjectBuilder().add("success", mdlctrl.addPost(p)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletePost/{id}")
    public JsonObject deletePost(@PathParam("id")long id){
        return Json.createObjectBuilder().add("success", mdlctrl.deletePost(id)).build();   
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/refreshState")
    public JsonObject refreshState() {
        //  PostDTO p = PostDTO.toPOJO();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<PostDTO> list = mdlctrl.refreshState();
        Gson gson = new Gson();
        return Json.createObjectBuilder().add("list", gson.toJson(list)).build();    
    }
}