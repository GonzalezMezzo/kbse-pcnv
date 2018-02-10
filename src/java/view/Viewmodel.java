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
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

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

    private static final boolean SAVE_IMAGES_TO_DISK = false;

    private List<PostDTO> postList;
    private List<SystemUserDTO> userList;
    private Map<PostDTO, RatingDTO> ratingCollector;
    private String inputTextUser;
    private String inputTextFName;
    private String inputTextLName;
    private String inputTextEMail;
    private String inputTextDescription;
    private String inputTexTURL;
    private int inputTextNumber;

    private String inputCommentUser;
    private String inputCommentMessage;

    //private int[] ratingCollector;
    private SystemUserDTO currentUser;
    private PostDTO currentPost;

    private Part uploadedAvatar;
    private byte[] parsedAvatar;

    Long postId;
    String username;

    @PostConstruct
    public void init() {
        ratingCollector = new HashMap();
        postId = null;
        username = null;
        /*
        this.inputTextUser = "User";
        this.inputTextDescription = "Dies das, ein bisschen länger";
        this.inputTextEMail = "sample@sample.com";
        this.inputTextFName = "Hans";
        this.inputTextLName = "Peter";
        this.inputTexTURL = "www.google.de";
        this.inputTextNumber = 0;
         */
        ctrl.addAvatar(new AvatarDTO());

        refreshState();
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
        SystemUserDTO user = new SystemUserDTO(this.inputTextUser, this.inputTextFName, this.inputTextLName, this.inputTextEMail);
        ctrl.addSystemUser(user);
        refreshState();
        this.currentUser = user;
        //ratingCollector = new int[postList.size()];
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

        PostDTO post = new PostDTO(this.inputTexTURL, this.inputTextDescription, this.currentUser, 0, new ArrayList<>());

        ctrl.addPost(post, this.currentUser);
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
        ctrl.addComment(comment, this.currentPost, this.currentUser);
        refreshState();
        return POST;
    }

    public void refreshState() {
        if (this.currentPost != null) {
            postId = this.currentPost.getId();
            this.currentPost = ctrl.getPost(postId);

        }
        if (this.currentUser != null) {
            username = this.currentUser.getUsername();
            this.currentUser = ctrl.getSystemUser(username);
        }
        this.postList = ctrl.getPostList();
        this.userList = ctrl.getUserList();

        ratingCollector = new HashMap();
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

    public String submitRating() {
        if (validate() == true) {//method stub

            //delete every previous rating for this user
            ctrl.deleteRating(currentUser.getUsername());
            //add individual ratings for this submit    
            for (Map.Entry<PostDTO, RatingDTO> entry : ratingCollector.entrySet()) {
                ctrl.addRating(entry.getKey(), entry.getValue(), currentUser);
            }
        }
        refreshState();
        return BOARD;
    }

    public boolean validate() {
        int res = 0;
        for (Map.Entry<PostDTO, RatingDTO> entry : ratingCollector.entrySet()) {
            res += entry.getValue().getRatedValue();
        }
        if (res <= 10 && res >= 0) {
            return true;
        }
        return false;
    }

    /**
     * fetches rating input for post at pos i
     *
     * @param i
     */
    public void addRating(PostDTO post) {
        System.out.println(post);
        System.out.println(inputTextNumber);
        ratingCollector.put(post, new RatingDTO(inputTextNumber, currentUser, post));
    }

    public String upload() {
        Part uploadedFile = this.uploadedAvatar;
        final Path destination = Paths.get(System.getProperty("user.dir") + "\\tempImage.jpeg");
        List<Byte> byteList;
        int hash = 0;
        AvatarDTO avatar = null;
        InputStream bytes = null;
        if (null != uploadedFile) {
            try {
                if (SAVE_IMAGES_TO_DISK) {
                    bytes = uploadedFile.getInputStream();
                    Files.copy(bytes, destination, REPLACE_EXISTING);
                }
                bytes = uploadedFile.getInputStream();
                this.parsedAvatar = IOUtils.toByteArray(bytes);
                //this.parsedAvatar = toByteArray(bytes);
                //byteList = Arrays.asList(this.parsedAvatar);
                //hash = calculateImageHash(this.parsedAvatar);

                avatar = new AvatarDTO(hash, this.parsedAvatar);

                ctrl.addAvatar(avatar);
            } catch (IOException ex) {
                Logger.getLogger(Viewmodel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return USER_CONTROL;
    }

    //1048576 = 1mb
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) throws ValidatorException {
        List<FacesMessage> msgs = new ArrayList<>();
        Part file = (Part) value;
        if (file != null) {
            if (file.getSize() > 1048576) {
                msgs.add(new FacesMessage("file too big"));
            }
            if (!file.getContentType().endsWith("jpeg")) {
                msgs.add(new FacesMessage("Select JPEG file"));
            }
            if (!"image/jpeg".equals(file.getContentType())) {
                msgs.add(new FacesMessage("not a jpeg file"));
            }
            if (!msgs.isEmpty()) {
                throw new ValidatorException(msgs);
            }
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;
        FacesMessage message = null;
        try {
            if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
                message = new FacesMessage("Select a valid file");
            } else if (!file.getContentType().endsWith("jpeg")) {
                message = new FacesMessage("Select JPEG file");
            } else if (file.getSize() > 2000000) {
                message = new FacesMessage("File size too big. File size allowed is less than or equal to 2 MB.");
            }

            if (message != null && !message.getDetail().isEmpty()) {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        } catch (ValidatorException ex) {
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }

    }

    private Byte[] convertByteList(byte[] b) {
        Byte[] res = new Byte[b.length];
        for (int i = 0; i < b.length; i++) {
            res[i] = b[i];
        }
        return res;
    }

    private int calculateImageHash(Byte[] image) {
        return Arrays.hashCode(image);
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

    /*
    public int[] getRatingCollector() {
        return ratingCollector;
    }

    public void setRatingCollector(int[] ratingCollector) {
        this.ratingCollector = ratingCollector;
    }
     */
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

    public SystemUserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(SystemUserDTO currentUser) {
        this.currentUser = currentUser;
    }

    public Map<PostDTO, RatingDTO> getRatingCollector() {
        return ratingCollector;
    }

    public void setRatingCollector(Map<PostDTO, RatingDTO> ratingCollector) {
        this.ratingCollector = ratingCollector;
    }

    public Part getUploadedAvatar() {
        return uploadedAvatar;
    }

    public void setUploadedAvatar(Part uploadedAvatar) {
        this.uploadedAvatar = uploadedAvatar;
    }

}
