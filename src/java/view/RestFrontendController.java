/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.PostDTO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.json.JsonObject;
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
    private final static String THREAD = "http://localhost:8080/kbse-pcnv";
    
    private Client client;
    private WebTarget wt;
    
    @PostConstruct
    public void init(){
        this.client = ClientBuilder.newClient();
        this.wt = client.target(THREAD);
    }
    
    /*public Boolean addPost(PostDTO p){
        this.wt = client.target(THREAD+"/addPost");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
       TODO Entity entity = Entity.entity(p.toJsonObject(), MediaType.APPLICATION_JSON);
        
        try{
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");
            
        }catch(Exception e){
            System.out.println("addPost(rest) ->");
        }
    }*/

    boolean deletePost(PostDTO p) {
        this.wt = client.target(THREAD+"/deletePost");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        //Entity entity = Entity.entity(p.toJsonObject(), MediaType.APPLICATION_JSON);
        try{          
            JsonObject res = build.delete(JsonObject.class);
            return res.getBoolean("success");
        }catch(Exception e){
            System.out.println("deletePost(rest) ->");
        }
        return false;
    }

    List<PostDTO> refreshState() {
        this.wt = client.target(THREAD+"/refreshState");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        try{
            JsonObject res = build.get(JsonObject.class);
            //TODO : json list
        }catch(Exception e){
        
        }
        return null;
    }

    void updateRatings(List<PostDTO> postList) {
        this.wt = client.target(THREAD+"/updateRatings");
        
    }
}
