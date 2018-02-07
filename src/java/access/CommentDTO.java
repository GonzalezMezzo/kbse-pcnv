/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Comment;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
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

    private Long id;
    private String message;
    private String timeStamp;
    private String creator;
    private Long ownerId;

    public CommentDTO() {
    }

    public CommentDTO(String message, String creator, Long ownerId) {
        this.creator = creator;
        this.timeStamp = new SimpleDateFormat("HHmmss_ddMMyyyy").format(Calendar.getInstance().getTime());
        this.message = message;
        this.ownerId = ownerId;
    }

    public CommentDTO(Long id, String message, String creator, String timestamp, Long ownerId) {
        this.id = id;
        this.creator = creator;
        this.timeStamp = timestamp;
        this.message = message;
        this.ownerId = ownerId;
    }

    public static CommentDTO toCommentDTO(Comment c) {
        if (c == null) {
            return null;
        }
        return new CommentDTO(c.getId(), c.getMessage(), c.getCreator(), c.getTimestamp(), c.getOwnerId());
    }

    public Comment toComment() {
        return CommentBuilder.create().message(this.message)
                .creator(this.creator).timestamp(this.timeStamp).owner(this.ownerId).build();
    }

    public JsonObject toJsonObject() {
        Gson gson = new Gson();
       
        JsonObjectBuilder js = Json.createObjectBuilder();
        if (this.id != null) {
            js.add("id", this.id);
        }
        js.add("message", this.message)
                .add("timestamp", this.timeStamp)
                .add("creator", this.creator)
                .add("owner", this.ownerId);
        return js.build();
    }

    public static CommentDTO toPOJO(JsonObject js) {
        CommentDTO c = new CommentDTO();
        Gson gson = new Gson();
        //c = gson.fromJson(js.toString(), CommentDTO.class);
                
        if (js.getJsonNumber("id") != null) {
            c.setId(js.getJsonNumber("id").longValue());
        }
        c.setMessage(js.getString("message"));
        c.setTimeStamp(js.getString("timestamp"));
        c.setCreator(js.getString("creator"));
        c.setOwnerId(js.getJsonNumber("owner").longValue());
        return c;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    
    
    
}
