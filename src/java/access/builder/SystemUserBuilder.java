/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package access.builder;

import entities.Avatar;
import entities.SystemUser;

/**
 *
 * @author nolde
 */
public class SystemUserBuilder {

    private long id;
    private String username;
    private String fname;
    private String lname;
    private String email;
    private Avatar avatar;

    private SystemUserBuilder() {
    }

    public static SystemUserBuilder create() {
        return new SystemUserBuilder();
    }

    public SystemUserBuilder id(long id) {
        this.id = id;
        return this;
    }

    public SystemUserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public SystemUserBuilder firstname(String fname) {
        this.fname = fname;
        return this;
    }

    public SystemUserBuilder lastname(String lname) {
        this.lname = lname;
        return this;
    }

    public SystemUserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public SystemUserBuilder avatar(Avatar avatar) {
        this.avatar = avatar;
        return this;
    }

    public SystemUser build() {
        SystemUser res = new SystemUser();
        res.setId(this.id);
        res.setUsername(this.username);
        res.setFname(this.fname);
        res.setLname(this.lname);
        res.setEmail(this.email);
        res.setAvatar(this.avatar);
        return res;
    }
}
