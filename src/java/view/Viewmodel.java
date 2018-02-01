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
    /*
    @Inject
    RestFrontendController rfctrl;
    */
    
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
    private int[] ratingCollector;
    
    private int test;

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    
    
    @PostConstruct
    public void init(){
        this.inputTextUser = "User";
        refreshState();
        ratingCollector = new int[postList.size()];
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
        refreshState();
        return INDEX;
    }
    
    public String delete(PostDTO p){
        mdlctrl.deletePost(p);
        refreshState();
        return INDEX;
    }
    
    public void refreshState(){
        this.postList = mdlctrl.refreshState();
        ratingCollector = new int[postList.size()];
        inputTextNumber = 0;
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
    
    public void submitRating(){
        if (validate()==true){//method stub
        for(int i =0;i<ratingCollector.length;i++){
            if(ratingCollector[i]!=0)
                this.postList.get(i).getRatings().put(inputTextUser, new Integer(ratingCollector[i]));
            mdlctrl.updateRatings(this.postList);
        }
        refreshState();
        }
        
    }
    
    /**
     * fetches rating input for post at pos i 
     * @param i 
     */
    public void addRating(int i){
        ratingCollector[i] = inputTextNumber;
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

    public int[] getRatingCollector() {
        return ratingCollector;
    }

    public void setRatingCollector(int[] ratingCollector) {
        this.ratingCollector = ratingCollector;
    }

    private boolean validate() {
        int n1 =0;
        for(int i =0; i<ratingCollector.length; i++){
            n1 += ratingCollector[i];
            
            System.out.println("test" + n1);
            if(ratingCollector[i]<0 || ratingCollector[i]>10 || n1>10){
                return false;
            }
        }
        return true;      
    }
}
