/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.CommentDTO;
import access.PostDTO;
import db.Persistence;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author philippnienhuser
 */
@Named(value = "modelController")
@SessionScoped
public class ModelController implements Serializable{
    
    @Inject
    private Persistence db;
    
    private List<PostDTO> postList;
    
    @PostConstruct
    public void init(){
           this.postList = db.getAllPosts();
    }
    
    public boolean addPost(PostDTO p){
        try{
            db.addPost(p);
        }catch(EJBException | NullPointerException e){
            /**
             * todo: error handling
             */
            System.out.println("addPost -> exception");
            return false;
        }
        return true;
    }
    
    public boolean deletePost(long id){
        try{
            db.deletePost(id);
        }catch(EJBException e){
            /**
             * todo: error handling
             */
            System.out.println("deletePost -> exception");
            return false;
        }
        return true;
    }
        
    public List<PostDTO> refreshState(){
        this.postList = db.getAllPosts();
        return this.postList;
    }

    public void updateRatings(List<PostDTO> postList) {
        try{
            db.updateRatings(postList);
        }catch(EJBException e){
            /**
             * todo: error handling
             */
            System.out.println("refreshRatings -> exception");
        }
    }

    public boolean addComment(CommentDTO comment) {
        try{
            db.addComment(comment);
            return true;
        }catch(EJBException e){
            /**
             * todo: error handling
             */
            System.out.println("addComment -> exception");
            return false;
        }
    }
}
