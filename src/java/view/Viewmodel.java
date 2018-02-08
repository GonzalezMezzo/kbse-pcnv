/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.CommentDTO;
import access.PostDTO;
import access.SystemUserDTO;
import access.AvatarDTO;
import access.RatingDTO;
import controller.ModelController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
public class Viewmodel implements Serializable {

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
    private List<SystemUserDTO> userList;
    private String inputTextUser;
    private String inputTextFName;
    private String inputTextLName;
    private String inputTextEMail;
    private String inputTextDescription;
    private String inputTexTURL;
    private int inputTextNumber;

    private String inputCommentUser;
    private String inputCommentMessage;

    private int[] ratingCollector;

    private SystemUserDTO currentUser;
    private PostDTO currentPost;

    @PostConstruct
    public void init() {
        /*
        this.inputTextUser = "User";
        this.inputTextDescription = "Dies das, ein bisschen länger";
        this.inputTextEMail = "sample@sample.com";
        this.inputTextFName = "Hans";
        this.inputTextLName = "Peter";
        this.inputTexTURL = "www.google.de";
        this.inputTextNumber = 0;
         */

        refreshState();
        /**
         * CRITICAL CODE !!!!!!!
         */
        //currentPost = postList.get(0);
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

    public String changeUser() {
        refreshState();
        SystemUserDTO user = new SystemUserDTO(this.inputTextUser,this.inputTextFName,this.inputTextLName, this.inputTextEMail);
        ctrl.addSystemUser(user);
        this.currentUser = user;
        refreshState();
        return USER_CONTROL;
    }

    public String rating() {
        return RATING;
    }

    public String postLink() {
        return INDEX;
    }

    public String submitLink() {
        refreshState();
        
        PostDTO post = new PostDTO(this.inputTexTURL, this.inputTextDescription, this.currentUser, 0, new ArrayList<RatingDTO>());
        
        ctrl.addPost(post);
        refreshState();
        return BOARD;
    }

    public String delete(PostDTO p) {
        refreshState();
        ctrl.deletePost(p.getId());
        refreshState();
        return USER_CONTROL;
    }

    public String submitComment() {
        refreshState();
        CommentDTO comment = new CommentDTO(this.inputCommentMessage, this.currentUser, this.currentPost);
        ctrl.addComment(comment);
        refreshState();
        return POST;
    }

    public void refreshState() {
        Long postId = null;
        Long userId = null;
        if (this.currentPost != null) {
            postId = this.currentPost.getId();
        }
        if (this.currentUser != null) {
            userId = this.currentUser.getId();
        }
        ctrl.refreshState();
        this.postList = ctrl.getPostList();
        ctrl.refreshState();
        this.userList = ctrl.getUserList();

        ratingCollector = new int[postList.size()];

        if (postId != null) {
            for (PostDTO postDTO : postList) {
                if (Objects.equals(postDTO.getId(), postId)) {
                    this.currentPost = postDTO;
                }
            }
        }

        if(userId != null) {
            for (SystemUserDTO systemUserDTO : userList) {
                if(Objects.equals(systemUserDTO.getId(), userId)) {
                    this.currentUser = systemUserDTO;
                }
            }
        }
        
        inputTextNumber = 0;
        ctrl.refreshState();
    }

    public String selectPost(PostDTO i) {
        ctrl.refreshState();
        this.postList = ctrl.getPostList();
        this.currentPost = i;
        return POST;
    }

    /**
     * Get user's rating on index i on the current rendered list of posts
     *
     * @param i
     * @return rating
     */
    public int getPersonalRating(int i) {
        //int rating = this.postList.get(i).getRatings().get(inputTextUser);
        return 0;
    }

    public boolean renderInputForRating(int i) {
        if (inputTextUser.equals(postList.get(i).getCreatorId())) {
            return false;
        }
        return true;
    }

    public void submitRating() {
        if (validate() == true) {//method stub
            for (int i = 0; i < ratingCollector.length; i++) {
                if(ratingCollector[i] != 0) {
                    this.postList.get(i).getRatings().add(new RatingDTO(ratingCollector[i], this.currentUser, this.currentPost));
                }
                ctrl.updateRatings(this.postList);
            }
            
            /*
            for (int i = 0; i < ratingCollector.length; i++) {
                if (ratingCollector[i] != 0) {
                    this.postList.get(i).getRatings().put(inputTextUser, new Integer(ratingCollector[i]));
                }
                ctrl.updateRatings(this.postList);
            }*/
            refreshState();
        }
    }

    /**
     * fetches rating input for post at pos i
     *
     * @param i
     */
    public void addRating(int i) {
        //ratingCollector[i] = inputTextNumber;
    }

    /*--------------------------------------------------------------------------
    getter
    --------------------------------------------------------------------------*/
    public String getInputTexTURL() {
        return inputTexTURL;
    }

    public void setInputTexTURL(String inputTexTURL) {
        this.inputTexTURL = inputTexTURL;
    }

    public String getInputTextFName() {
        return inputTextFName;
    }

    public void setInputTextFName(String inputTextFName) {
        this.inputTextFName = inputTextFName;
    }

    public String getInputTextLName() {
        return inputTextLName;
    }

    public void setInputTextLName(String inputTextLName) {
        this.inputTextLName = inputTextLName;
    }

    public String getInputTextEMail() {
        return inputTextEMail;
    }

    public void setInputTextEMail(String inputTextEMail) {
        this.inputTextEMail = inputTextEMail;
    }

    public String getInputTextUser() {
        return inputTextUser;
    }

    public void setInputTextUser(String inputTextUser) {
        this.inputTextUser = inputTextUser;
    }

    public String getInputTextDescription() {
        return inputTextDescription;
    }

    public void setInputTextDescription(String inputTextDescription) {
        this.inputTextDescription = inputTextDescription;
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

    public int getInputTextNumber() {
        return inputTextNumber;
    }

    public void setInputTextNumber(int inputTextNumber) {
        this.inputTextNumber = inputTextNumber;
    }

    public PostDTO getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(PostDTO currentPost) {
        this.currentPost = currentPost;
    }

    public String getInputCommentUser() {
        return inputCommentUser;
    }

    public void setInputCommentUser(String inputCommentUser) {
        this.inputCommentUser = inputCommentUser;
    }

    public String getInputCommentMessage() {
        return inputCommentMessage;
    }

    public void setInputCommentMessage(String inputCommentMessage) {
        this.inputCommentMessage = inputCommentMessage;
    }

    private boolean validate() {
        int n1 = 0;
        for (int i = 0; i < ratingCollector.length; i++) {
            n1 += ratingCollector[i];

            System.out.println("test" + n1);
            if (ratingCollector[i] < 0 || ratingCollector[i] > 10 || n1 > 10) {
                return false;
            }
        }
        return true;
    }

}
