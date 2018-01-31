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
    private static final String RATING = "/rating.xhtml?faces-redirect=true";
    
    private List<PostDTO> postList;
    private String inputTextUser;
    private boolean changeUserBool = false;
    private boolean changeUebernehmenBool = false;
    private int inputTextNumber =0;
    private String comment = "Comment";
    private String url = "www.google.de";

    
    
    @PostConstruct
    public void init(){
        this.inputTextUser = "User";
        this.postList = refreshState();
    }
    
    public String changeUser(int i){
        if(i==0)
            return INDEX;
        return RATING;
    }
    
    public String rating(){
        return RATING;
    }
    
    public String postLink(){
        return INDEX;
    }
    
    public String submit(){
        changeUebernehmenBool = true;
        PostDTO post = new PostDTO(url,comment,inputTextUser,0,new HashMap<String,Integer>());
        post.getRatings().put(inputTextUser, 0);
        mdlctrl.addPost(post);
        this.postList = refreshState();
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
    
    /**
     * Get user's rating on index i on the current rendered list of posts
     * @param i
     * @return rating
     */
    public int getPersonalRating(int i){
        //int rating = this.postList.get(i).getRatings().get(inputTextUser);
        return 0;
    }
    
    public boolean renderInputForRating(int i){
        if(inputTextUser.equals(postList.get(i).getCreator()))
            return false;
        return true;
    }
    
    public void submitRating(){}

        /*--------------------------------------------------------------------------
    getter
    --------------------------------------------------------------------------*/

    public String getInputTextUser() {
        return inputTextUser;
    }

    public void setInputTextUser(String inputTextUser) {
        this.inputTextUser = inputTextUser;
    }

    public int getInputTextNumber() {
        return inputTextNumber;
    }

    public void setInputTextNumber(int inputTextNumber) {
        this.inputTextNumber = inputTextNumber;
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
