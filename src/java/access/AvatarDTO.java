/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import com.google.gson.Gson;
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
    private List<Byte> image;

    public AvatarDTO() {
    }

    public AvatarDTO(int imageHash, List<Byte> image) {
        this.imageHash = imageHash;
        this.image = image;
    }

    public AvatarDTO(long id, int imageHash, List<Byte> image) {
        this.id = id;
        this.imageHash = imageHash;
        this.image = image;
    }
    
    public static AvatarDTO toAvatarDTO(Avatar a) {
        if(a == null) {
            return null;
        }      
        return new AvatarDTO(a.getId(), a.getImageHash(), a.getImage());
    }

    public Avatar toAvatar() {
        return AvatarBuilder.create().id(this.id).imageHash(this.imageHash).image(new ArrayList<>(this.image)).build();
    }
    
    public JsonObject toJsonObejct() {
        JsonObjectBuilder js = Json.createObjectBuilder();
        Gson gson = new Gson();
        String byteList = gson.toJson(this.image, List.class);
        if(this.id != null) {
            js.add("id", this.id);
        }
        
        js.add("imageHash", this.imageHash).add("image", byteList);
        return js.build();
    }
}
