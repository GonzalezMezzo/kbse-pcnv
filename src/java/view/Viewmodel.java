/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.CommentDTO;
import access.PostDTO;
import controller.ModelController;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Inject
    //RestFrontendController ctrl;
    ModelController ctrl;

    private static final String INDEX = "/index.xhtml?faces-redirect=true";
    private static final String RATING = "/rating.xhtml?faces-redirect=true";
    private static final String BOARD = "/board.xhtml?faces-redirect=true";
    private static final String USER_CONTROL = "/user_control.xhtml?faces-redirect=true";
    private static final String ABOUT = "/about.xhtml?faces-redirect=true";
    private static final String POST = "/post.xhtml?faces-redirect=true";
    
    private List<PostDTO> postList;
    private String inputTextUser;
    private String inputTextComment = "standard";
    private boolean changeUserBool = false;
    private boolean changeUebernehmenBool = false;
    private int inputTextNumber =0;
    private String description = "Comment";
    private String url = "www.google.de";
    private int[] ratingCollector;

    
    private int test;
    private PostDTO currentPost;

    @PostConstruct
    public void init(){
        this.inputTextUser = "User";
        refreshState();
        /**
         * CRITICAL CODE !!!!!!!
         */
        currentPost = postList.get(0);
        /**
         * CRITICAL CODE !!!!!!!
         */
        ratingCollector = new int[postList.size()];
    }
    
    /* routing */
    public static String getINDEX() {
        return INDEX;
    }

    public static String getRATING() {
        return RATING;
    }

    public static String getBOARD() {
        return BOARD;
    }

    public static String getUSER_CONTROL() {
        return USER_CONTROL;
    }

    public static String getABOUT() {
        return ABOUT;
    }
    
    public static String getPOST() {
        return POST;
    }
      
    
    public String changeUser(int i){
        if(i==0)
            return INDEX;
        return USER_CONTROL;
    }
    
    public String rating(){
        return RATING;
    }
    
    public String postLink(){
        return INDEX;
    }
    
    public String submitLink(){
        PostDTO post = new PostDTO(url,description,inputTextUser,0,new HashMap<String,Integer>());
        post.getComments().add(new CommentDTO(description, inputTextUser, currentPost));
        post.getRatings().put(inputTextUser, 0);
        ctrl.addPost(post);
        refreshState();
        return INDEX;
    }
    
    public String delete(PostDTO p){
        ctrl.deletePost(p.getId());
        refreshState();
        return INDEX;
    }
    
    public String submitComment(){
        CommentDTO comment = new CommentDTO(inputTextComment, inputTextUser, currentPost);
        ctrl.addComment(comment);
        refreshState();
        return INDEX;
    }
    public void refreshState(){
        this.postList = ctrl.refreshState();
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
            ctrl.updateRatings(this.postList);
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

    public String getInputTextComment() {
        return inputTextComment;
    }

    public void setInputTextComment(String inputTextComment) {
        this.inputTextComment = inputTextComment;
    }

    public int getInputTextNumber() {
        return inputTextNumber;
    }

    public void setInputTextNumber(int inputTextNumber) {
        this.inputTextNumber = inputTextNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
