/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import com.google.gson.Gson;
import access.PostDTO;
import com.google.gson.reflect.TypeToken;
import enitities.Comment;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author chrschae
 */
public class CommentDTO implements Serializable {
    private long id;
    private String message;
    private String timeStamp;
    private String creator;
    
    public CommentDTO(){}

    public CommentDTO(String message, String creator){
        this.creator = creator;
        this.timeStamp = new SimpleDateFormat("HHmmss_ddMMyyyy").format(Calendar.getInstance().getTime());
        this.message = message;
    }
    public CommentDTO(Long id,String message, String creator, String timestamp){
        this.id = id;
        this.creator = creator;
        this.timeStamp = timestamp;
        this.message = message;
    }
    
    public static CommentDTO toCommentDTO(Comment c){
        if(c == null){
            return null;
        }
        return new CommentDTO(c.getId(),c.getCreator(),c.getTimestamp(),c.getMessage());
    }
    
    public Comment toComment(){
        return CommentBuilder.create().id(this.id).message(this.message)
                .creator(this.creator).timestamp(this.timeStamp).build();
    }
    
    public JsonObject toJsonObject(){
        Gson gson = new Gson();
        JsonObjectBuilder js = Json.createObjectBuilder();
        js.add("id", this.id)
                .add("message", this.message)
                .add("timestamp", this.timeStamp)
                .add("creator", this.creator);
        return js.build();
    }
    
    public static CommentDTO toPOJO(JsonObject js){
        CommentDTO c = new CommentDTO();       
        c.setId(js.getJsonNumber("id").longValue());
        c.setMessage(js.getString("message"));
        c.setTimeStamp(js.getString("timestamp"));
        c.setCreator(js.getString("creator"));
        return c;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
