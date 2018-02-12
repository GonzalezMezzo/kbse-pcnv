/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.DTO.AvatarDTO;
import access.DTO.CommentDTO;
import access.DTO.PostDTO;
import access.DTO.RatingDTO;
import access.DTO.SystemUserDTO;
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

    /**
     *
     */
    @PostConstruct
    public void init() {
        this.postList = db.getAllPosts();
        this.userList = db.getAllUsers();
    }

    /** Returns true if adding a Post to the Database was successful
     *  
     * @param p PostDTO object to be added
     * @param u SystemUser object to be added
     * @return Boolean
     */
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

    /** Returns true if adding a Comment to the Database was successful
     *
     * @param comment CommentDTO object to be added to the database
     * @param p PostDTO object the Comment belongs to
     * @param currentUser SystemUser object the Comment belongs to
     * @return
     */
    public boolean addComment(CommentDTO comment, PostDTO p, SystemUserDTO currentUser) throws EJBException{
            db.addComment(comment, p, currentUser);
            return true;  
    }

    /** Returns true if adding a Rating to the database was successful
     *
     * @param p PostDTO which belongs to the Rating
     * @param r RatingDTO object which contain the Rating for a specific PostDTO object
     * @param u SystemuserDTO object belonging to the Creator of the Comment
     * @return
     */
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

    /** returns true if deleting a post from the database was successful
     *
     * @param id    ID under which the Post is listed in the database
     * @return  Boolean
     */
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

    /** returns true if deleting a rating from the database was successful
     *
     * @param userName username of the SystemUserDTO object belonging to the rating to delete
     * @return  Boolean
     */
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

    /** updates PostList and UserList with fresh entry from the database
     *
     */
    public void refreshState() {
        this.postList = db.getAllPosts();
        this.userList = db.getAllUsers();
    }

    /**
     * get updated ratings for entries in the postList
     * @param postList PostList which ratings should be updated
     */
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

    /**
     *  Returns true  if Updating the SystemUserDTO from the database was successful
     * @param su SystemUserDTO object to be updated with new data
     * @return boolean
     */
    public boolean updateSystemUser(SystemUserDTO su) {
        try {
            db.updateSystemUser(su);
            return true;
        } catch (EJBException e) {
            System.out.println("updateSystemUser -> exception");
            //System.out.println(e.getCause().toString());
            return false;
        }
    }

    /** 
     * return true if adding a SystemUser to the database was successful
     * @param user SystemUserDTO object to be added to the model
     * @return
     */
    public boolean addSystemUser(SystemUserDTO user) {
        try {
            db.addSystemUser(user);
            return true;
        } catch (EJBException e) {
            /**
             * todo: error handling
             */
            System.out.println("addUser -> exception");
            return false;
        }
    }

    /**
     *
     * @return PostListDTO
     */
    public List<PostDTO> getPostList() {
        return postList;
    }

    /** 
     *  returns Post with given postID. id cannot be null.
     * @param id Long postID
     * @return null if postID is null, else PostDTO 
     */
    public PostDTO getPost(Long id) {
        if (id == null) {
            return null;
        } else {
            return db.getPost(id);
        }
    }

    /**
     *
     * @return
     */
    public List<SystemUserDTO> getUserList() {
        return userList;
    }

    /**
     *  returns true if adding the avatarImage to the database was successful
     * @param avatarDTO avatarImage to be added to the model
     * @return  boolean
     */
    public boolean addAvatar(AvatarDTO avatarDTO) {
        try {
            db.addAvatar(avatarDTO);
            return true;
        } catch (EJBException e) {
            System.out.println("addAvatar -> exception");
            return false;
        }
    }

    /**
     * Converts SystemUser from the Model to the SystemUserDTO class, which is then returned
     * @param userId SystemUserID
     * @return SystemUserDTO object identified by the id in the model
     */
    public SystemUserDTO getSystemUser(Long userId) {
        if (userId == null) {
            return null;
        } else {
            return SystemUserDTO.toSystemUserDTO(db.getUser(userId));
        }
    }

    /**
     * Converts SystemUser from the Model to the SystemUserDTO class, which is then returned
     * @param username SystemUserID
     * @return SystemUserDTO object identified by the username String in the model
     */
    public SystemUserDTO getSystemUser(String username) {
        if (username == null) {
            return null;
        } else {
            return SystemUserDTO.toSystemUserDTO(db.getUser(username));
        }
    }

    /**
     *  Returns AvatarDTO from the database with given avatarHash
     * @param uploadedAvatarHash
     * @return avatarDTO
     */
    public AvatarDTO getAvatar(int uploadedAvatarHash) {
        return db.getAvatar(uploadedAvatarHash);
    }

}
