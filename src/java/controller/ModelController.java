/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.AvatarDTO;
import access.CommentDTO;
import access.PostDTO;
import access.RatingDTO;
import access.SystemUserDTO;
import db.Persistence;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonValue;

/**
 *
 * @author philippnienhuser
 */
@Named(value = "modelController")
@SessionScoped
public class ModelController implements Serializable {

    @Inject
    private Persistence db;

    private List<PostDTO> postList;
    private List<SystemUserDTO> userList;

    @PostConstruct
    public void init() {
        this.postList = db.getAllPosts();
        this.userList = db.getAllUsers();
    }

    public boolean addPost(PostDTO p, SystemUserDTO u) {
        try {
            db.addPost(p, u);
        } catch (EJBException | NullPointerException e) {
            /**
             * todo: error handling
             */
            System.out.println("addPost -> exception");
            return false;
        }
        return true;
    }

    public boolean addComment(CommentDTO comment, PostDTO p, SystemUserDTO currentUser) {
        try {
            db.addComment(comment, p, currentUser);
            return true;
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("addComment -> exception");
            return false;
        }
    }
    
    public boolean addRating(PostDTO p, RatingDTO r, SystemUserDTO u) {
        try {
            db.addRating(p, r, u);
            return true;
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("addRating -> exception");
            return false;
        }
    }

    public boolean deletePost(long id) {
        try {
            db.deletePost(id);
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("deletePost -> exception");
            return false;
        }
        return true;
    }
    
    public boolean deleteRating(String userName) {
    try {
            db.deleteRatings(userName);
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("deleteRating -> exception");
            return false;
        }
        return true;
    }
        
        
    public void refreshState() {
        this.postList = db.getAllPosts();
        this.userList = db.getAllUsers();
    }

    public void updateRatings(List<PostDTO> postList) {
        try {
            db.updateRatings(postList);
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("refreshRatings -> exception");
        }
    }

    public boolean updateSystemUser(SystemUserDTO su) {
        try {
            db.updateSystemUser(su);
            return true;
        } catch (EJBException e) {
            System.out.println("updateSystemUser -> exception");
            System.out.println(e.getCause().toString());
            return false;
        }
    }

    public boolean addSystemUser(SystemUserDTO user) {
        try {
            db.addSystemUser(user);
            return true;
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("addUser -> exception");
            //System.out.println(e.getCause().toString());
            System.out.println("addUser -> try edit instead...");
            return updateSystemUser(user);
        }
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public PostDTO getPost(Long id) {
        if (id == null) {
            return null;
        } else {
            return db.getPost(id);
        }
    }

    public List<SystemUserDTO> getUserList() {
        return userList;
    }

    public boolean addAvatar(AvatarDTO avatarDTO) {
        try {
            db.addAvatar(avatarDTO);
            return true;
        } catch (EJBException e) {
            System.out.println("addAvatar -> exception");
            return false;
        }
    }

    public SystemUserDTO getSystemUser(Long userId) {
        if(userId == null) {
            return null;
        } else {
            return SystemUserDTO.toSystemUserDTO(db.getUser(userId));
        }
    }

    public SystemUserDTO getSystemUser(String username) {
        if(username == null) {
            return null;
        } else {
            return SystemUserDTO.toSystemUserDTO(db.getUser(username));
        }
    }

    

}
