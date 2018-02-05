/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.CommentDTO;
import access.PostDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
    private final static String ADRESS = "http://localhost:8080/kbse-pcnv/r";
    
    private Client client;
    private WebTarget wt;
    
    @PostConstruct
    public void init(){
        this.client = ClientBuilder.newClient();
        this.wt = client.target(ADRESS);
    }
    
    public Boolean addPost(PostDTO p){
        this.wt = client.target(ADRESS+"/addPost");
        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Entity entity = Entity.entity(p.toJsonObject(), MediaType.APPLICATION_JSON);
        
        try{
            JsonObject res = build.post(entity, JsonObject.class);
            return res.getBoolean("success");
            
        }catch(Exception e){
            System.out.println("addPost(rest) ->");
        }
        return false;
    }

    boolean deletePost(long id) {
        this.wt = client.target(ADRESS+"/deletePost/"+id);

        Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        try{         
            JsonObject res = build.delete(JsonObject.class);
            return res.getBoolean("success");
        }catch(Exception e){
            System.out.println("deletePost(rest) ->");
            return false;
        }
    }
    
    List<PostDTO> refreshState() {
        this.wt = client.target(ADRESS+"/refreshState");
       Invocation.Builder build = this.wt.request(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        
        try{
            JsonObject res = build.get(JsonObject.class);
            Type listType = new TypeToken<ArrayList<PostDTO>>(){}.getType();
            return (List<PostDTO>)gson.fromJson(res.getString("list"), listType);
                
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    void updateRatings(List<PostDTO> postList) {
        this.wt = client.target(ADRESS+"/updateRatings");
        
    }

    void addComment(Long currentPostId, CommentDTO comment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}