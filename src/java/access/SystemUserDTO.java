/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.SystemUser;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author nolde
 */
public class SystemUserDTO implements Serializable {

    private Long id;
    private String username;
    private String fname;
    private String lname;
    private String email;
    private AvatarDTO avatar;

    public SystemUserDTO() {
        this.id = -1L;
    }

    public SystemUserDTO(Long id, String username, String fname, String lname, String email, AvatarDTO avatar) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.avatar = avatar;
    }

    public SystemUserDTO(String username, String fname, String lname, String email, AvatarDTO avatar) {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.avatar = avatar;
    }

    public SystemUserDTO(String username, String fname, String lname, String email) {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.avatar = new AvatarDTO();
    }

    public static SystemUserDTO toSystemUserDTO(SystemUser u) {
        if (u == null) {
            return null;
        }

        //conversation to come
        return new SystemUserDTO(u.getId(), u.getUsername(), u.getFname(), u.getLname(), u.getEmail(), AvatarDTO.toAvatarDTO(u.getAvatar()));
    }

    public SystemUser toSystemUser() {
        return SystemUserBuilder.create().username(this.username).firstname(this.fname).lastname(this.lname).email(this.email).avatar(this.avatar.toAvatar()).build();
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder js = Json.createObjectBuilder();
        Gson gson = new Gson();
        String byteArrayString = gson.toJson(this.avatar, AvatarDTO.class);

        if (this.id != null) {
            js.add("id", this.id);
        }

        js.add("username", this.username).add("firstname", this.fname).add("lastname", this.lname).add("email", this.email).add("avatar", byteArrayString);
        return js.build();
    }

    public static SystemUserDTO toPOJO(JsonObject js) {
        Gson gson = new Gson();
        SystemUserDTO u = new SystemUserDTO();

        if (js.getJsonNumber("id") != null) {
            u.setId(js.getJsonNumber("id").longValue());
        }
        u.setUsername(js.getString("username"));
        u.setFname(js.getString("firstname"));
        u.setLname(js.getString("lastname"));
        u.setEmail(js.getString("email"));
        u.setAvatar(gson.fromJson("avatar", AvatarDTO.class));
        return u;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AvatarDTO getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarDTO avatar) {
        this.avatar = avatar;
    }

}
