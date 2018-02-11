/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access.DTO;

import access.builder.AvatarBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Avatar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author nolde
 */
public class AvatarDTO implements Serializable {

    private Long id;
    private int imageHash;
    private String contentType;
    private byte[] image;

    public AvatarDTO() {
        this.id = -1L;
        this.contentType = "image/jpeg";
        this.imageHash = -1;
        this.image = new byte[1];
    }

    public AvatarDTO(int imageHash, String contentType, byte[] image) {
        //this.id = 0L;
        this.imageHash = imageHash;
        this.contentType = contentType;
        this.image = image;
    }

    public AvatarDTO(long id, int imageHash,String contentType, byte[] image) {
        this.id = id;
        this.imageHash = imageHash;
        this.contentType = contentType;
        this.image = image;
    }

    public static AvatarDTO toAvatarDTO(Avatar a) {
        if (a == null) {
            return null;
        }
        return new AvatarDTO(a.getId(), a.getImageHash(), a.getContentType(),a.getImage());
    }

    public Avatar toAvatar() {
        if (this.id == null) {
            return AvatarBuilder.create().imageHash(this.imageHash).contentType(this.contentType).image(this.image).build();
        } else {
            return AvatarBuilder.create().id(this.id).imageHash(this.imageHash).contentType(this.contentType).image(this.image).build();
        }
    }

    public JsonObject toJsonObejct() {
        JsonObjectBuilder js = Json.createObjectBuilder();
        Gson gson = new Gson();
        String byteList = gson.toJson(this.image, byte[].class);
        if (this.id != null) {
            js.add("id", this.id);
        }

        js.add("imageHash", this.imageHash).add("image", byteList);
        return js.build();
    }

    public static AvatarDTO toPOJO(JsonObject js) {
        AvatarDTO a = new AvatarDTO();
        Gson gson = new Gson();
        if(js.getJsonNumber("id")!= null)
        a.setId(js.getJsonNumber("id").longValue());
        a.setImageHash(js.getInt("imageHash"));
        a.setImage(gson.fromJson(js.getString("image"), new TypeToken<byte[]>() {
        }.getType()));
        return a;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImageHash() {
        return imageHash;
    }

    public void setImageHash(int imageHash) {
        this.imageHash = imageHash;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
