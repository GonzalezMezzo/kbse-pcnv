/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.CommentDTO;
import access.PostDTO;
import access.SystemUserDTO;
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
import javax.ws.rs.PUT;
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
        SystemUserDTO u = new SystemUserDTO(); // TODO user mit senden, added da sonst compile fehler 
        return Json.createObjectBuilder().add("success", mdlctrl.addPost(p,u)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletePost/{id}")
    public JsonObject deletePost(@PathParam("id") long id) {
        return Json.createObjectBuilder().add("success", mdlctrl.deletePost(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/refreshState")
    public JsonObject refreshState() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        mdlctrl.refreshState();
        List<PostDTO> list = mdlctrl.getPostList();
        Gson gson = new Gson();
        return Json.createObjectBuilder().add("list", gson.toJson(list)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addComment")
    public JsonObject addComment(JsonObject jo) {
        CommentDTO c = CommentDTO.toPOJO(jo);
        PostDTO p = new PostDTO(); // TODO replace with suitable getter
        SystemUserDTO u = new SystemUserDTO();
        mdlctrl.addComment(c,p,u);
        return Json.createObjectBuilder().add("success", true).build();
    }
}
