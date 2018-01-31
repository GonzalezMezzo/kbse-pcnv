/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.PostDTO;
import controller.ModelController;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author philippnienhuser
 */

@Named(value = "viewmodel")
@SessionScoped
public class Viewmodel implements Serializable{
    
    @Inject
    ModelController mdlctrl;
    
    private static final String INDEX = "/index.xhtml?faces-redirect=true";
    
    private List<PostDTO> postList;
    private String inputTextUser;
    private boolean changeUserBool = false;
    private boolean changeUebernehmenBool = false;
    private int nummer = 1;
    private String comment = "Begründung";
    private String url = "www.Google.de";
    
    
    @PostConstruct
    public void init(){
        this.inputTextUser = "-User-";
        this.postList = refreshState();
    }
    
    public String changeUser(){
        changeUserBool = true;
        return INDEX;
    }
    
    public String rating(){
        return INDEX;
    }
    
    public String submit(){
        changeUebernehmenBool = true;
        PostDTO post = new PostDTO(url,comment,inputTextUser,0,new HashMap<String,Integer>());
        mdlctrl.addPost(post);
        return INDEX;
    }
    
    public String delete(PostDTO p){
        mdlctrl.deletePost(p);
        this.postList = refreshState();
        return INDEX;
    }
    
    public List<PostDTO> refreshState(){
        return mdlctrl.refreshState();
    }

        /*--------------------------------------------------------------------------
    getter
    --------------------------------------------------------------------------*/

    public String getInputTextUser() {
        return inputTextUser;
    }

    public void setInputTextUser(String inputTextUser) {
        this.inputTextUser = inputTextUser;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int Nummer) {
        this.nummer = Nummer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChangeUserBool() {
        return changeUserBool;
    }

    public void setChangeUserBool(boolean changeUserBool) {
        this.changeUserBool = changeUserBool;
    }

    public boolean isChangeUebernehmenBool() {
        return changeUebernehmenBool;
    }

    public void setChangeUebernehmenBool(boolean changeUebernehmenBool) {
        this.changeUebernehmenBool = changeUebernehmenBool;
    }

    public List<PostDTO> getPostList() {
        return postList;
    }

    public void setPostList(List<PostDTO> postList) {
        this.postList = postList;
    }

    
    
    
    
}
