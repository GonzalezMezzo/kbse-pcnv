/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import access.PostDTO;
import db.Persistence;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
    
    
}
