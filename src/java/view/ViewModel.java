/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import access.DTO.CommentDTO;
import access.DTO.PostDTO;
import access.DTO.SystemUserDTO;
import access.DTO.AvatarDTO;
import access.DTO.RatingDTO;
import controller.ModelController;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ViewModel implements Serializable {

    @Inject
    //RESTClient ctrl;
    ModelController ctrl;

    private static final String INDEX = "/index.xhtml?faces-redirect=true";
    private static final String RATING = "/rating.xhtml?faces-redirect=true";
    private static final String BOARD = "/board.xhtml?faces-redirect=true";
    private static final String USER_CONTROL = "/user_control.xhtml?faces-redirect=true";
    private static final String ABOUT = "/about.xhtml?faces-redirect=true";
    private static final String POST = "/post.xhtml?faces-redirect=true";

    private List<PostDTO> postList;
    private List<SystemUserDTO> userList;
    private Map<PostDTO, RatingDTO> ratingCollector;
    private String inputTextUser;
    private String inputTextFName;
    private String inputTextLName;
    private String inputTextEMail;
    private String inputTextDescription;
    private String inputTexTURL;

    private String inputTextNumber;

    private String inputCommentUser;
    private String inputCommentMessage;
    private SystemUserDTO currentUser;
    private PostDTO currentPost;

    private Part uploadedAvatar;
    private int uploadedAvatarHash;
    private byte[] parsedAvatar;
    private AvatarDTO persistedAvatar;

    Long postId;
    String username;

    @PostConstruct
    public void init() {
        ratingCollector = new HashMap();
        postId = null;
        username = null;
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

    public String rating() {
        return RATING;
    }

    public String postLink() {
        return INDEX;
    }

    /**
     * Validates UserInput information and submits user data to the Model.
     *
     * @return redirect String USER_CONTROL or null if the user input is
     * invalid.
     */
    public String changeUser() {
        SystemUserDTO user = null;
        SystemUserDTO systemUser = null;
        systemUser = ctrl.getSystemUser(this.inputTextUser);
        if (!upload() && (this.uploadedAvatar == null)) {
            if (systemUser == null) {
                user = new SystemUserDTO(this.inputTextUser, this.inputTextFName, this.inputTextLName, this.inputTextEMail, ctrl.getAvatar(-1));
                ctrl.addSystemUser(user);
            } else {
                user = new SystemUserDTO(this.inputTextUser, this.inputTextFName, this.inputTextLName, this.inputTextEMail, systemUser.getAvatar());
                ctrl.updateSystemUser(user);
            }
        } else {
            if (systemUser == null) {
                user = new SystemUserDTO(this.inputTextUser, this.inputTextFName, this.inputTextLName, this.inputTextEMail, this.persistedAvatar);
                ctrl.addSystemUser(user);
            } else {
                user = new SystemUserDTO(this.inputTextUser, this.inputTextFName, this.inputTextLName, this.inputTextEMail, this.persistedAvatar);
                ctrl.updateSystemUser(user);
            }

        }
        refreshState();
        this.currentUser = user;
        this.username = user.getUsername();
        refreshState();
        this.showSuccessMessage();
        return USER_CONTROL;

    }

    private void showSuccessMessage() {
        if (FacesContext.getCurrentInstance() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Aktion erfolgreich!"));
        }
    }

    /**
     * Returns true if avatar hash is nor equals -1
     *
     * @param avatar
     * @return
     */
    public boolean showDefaultPic(AvatarDTO avatar) {
        boolean res = true;
        if (avatar != null) {
            if (avatar.getImageHash() != -1) {
                res = false;
            } else {
                res = true;
            }
        } else {
            res = true;
        }
        return res;
    }

    /**
     * Validates User Input for the SubmitLink method and outputs error messages
     * via facemessages
     *
     * @param ctx
     * @param comp
     * @param value
     */
    public void validateLink(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList();
        if (this.currentUser == null || "".equals(this.currentUser.getUsername())) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Username wird benötigt!"));
        }
        if (value.toString().isEmpty()) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "URL wird benötigt!"));
        } else if (!value.toString().matches("(w{3})\\.([a-zA-Z0-9-_]{1,}\\.?)+(\\.)([a-zA-Z]{2,6})")) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Bitte gültige URL angeben! Muster: www.subdomain.domain.TLD"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    /**
     * Validates User Input for the submitComment Method and outputs error
     * messages via facemessages
     *
     * @param ctx
     * @param comp
     * @param value
     */
    public void validateComment(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList();
        if (this.currentUser == null || "".equals(this.currentUser.getUsername())) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Username wird benötigt!"));
        }
        if (value == null || value.toString().isEmpty()) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Kommentar wird benötigt!"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    /**
     * Validates user input and submits a new Post to the model.
     *
     * @return redirect String to BOARD or null if input is invalid
     */
    public String submitLink() {
        refreshState();
        PostDTO post = new PostDTO(this.inputTexTURL, this.inputTextDescription, this.currentUser, 0, new ArrayList<>());
        if (ctrl.addPost(post, this.currentUser) == false) {
            if (FacesContext.getCurrentInstance() != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "URL gibt es bereits!"));
            }
            return null;
        }
        refreshState();
        this.showSuccessMessage();
        return BOARD;
    }

    /**
     * deletes a Post from the Model. Checks for a valid Username
     *
     * @param p Post to be deleted
     * @return redirect Sring USER_CONTROL or null if the input is invalid.
     */
    public String delete(PostDTO p) {
        refreshState();
        if (ctrl.deletePost(p.getId()) == false) {
            if (FacesContext.getCurrentInstance() != null) {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Post wurde bereits gelöscht!"));
            }
            return USER_CONTROL;
        }
        refreshState();
        this.showSuccessMessage();
        return USER_CONTROL;
    }

    /**
     * Submits a Comment to the Model.
     *
     * @return redirect String POST or null if user input is invalid or the post
     * is deleted.
     */
    public String submitComment() {
        refreshState();
        CommentDTO comment = new CommentDTO(this.inputCommentMessage, this.currentUser, this.currentPost);
        if (ctrl.addComment(comment, this.currentPost, this.currentUser) == false) {
            if (FacesContext.getCurrentInstance() != null) {
                FacesMessage notFound = new FacesMessage(FacesMessage.SEVERITY_ERROR, "DatenbankFehler", "Inhalt wurde gelöscht!");
                FacesContext.getCurrentInstance().addMessage(null, notFound);
            }
            return null;
        }
        refreshState();
        this.showSuccessMessage();
        return POST;
    }

    /**
     * Updates currentUser, postList and userList with updated data from the
     * model.
     */
    public void refreshState() {
        ctrl.refreshState();
        if (this.postId != null) {
            this.currentPost = ctrl.getPost(postId);
        }
        this.currentUser = ctrl.getSystemUser(username);
        this.postList = ctrl.getPostList();
        this.userList = ctrl.getUserList();

        ratingCollector = new HashMap();
        inputTextNumber = "0";
        ctrl.refreshState();
    }

    /**
     * Sets the current active post to the passed PostDTO object, then redirects
     * the view to this Post.
     *
     * @param i PostDTO object
     * @return redirect String POST page
     */
    public String selectPost(PostDTO i) {
        ctrl.refreshState();
        this.postList = ctrl.getPostList();
        this.currentPost = i;
        this.postId = i.getId();
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

    /**
     * Checks user input for the rating submission. After validation, the
     * submitted ratings are added to the model.
     *
     * @return redirect String BOARD or null if user input is invalid
     */
    public String submitRating() {

        if (this.currentUser == null) {
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Username benötigt.");
            FacesContext.getCurrentInstance().addMessage(null, success);
            return null;
        }
        if (validate() == true) {//method stub
            //delete every previous rating for this user           
            ctrl.deleteRating(currentUser.getUsername());
            //add individual ratings for this submit    
            for (Map.Entry<PostDTO, RatingDTO> entry : ratingCollector.entrySet()) {
                if (ctrl.addRating(entry.getKey(), entry.getValue(), currentUser) == false) {
                    FacesMessage notFound = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Post wurde gelöscht");
                    FacesContext.getCurrentInstance().addMessage(null, notFound);
                    return null;
                }
            }
            refreshState();
            this.showSuccessMessage();
            return BOARD;
        } else {
            FacesMessage fail = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Es können nur 10 Bewertungs-Punkte vergeben werden!");
            FacesContext.getCurrentInstance().addMessage(null, fail);
            return null;
        }
    }

    /**
     * validates if sum of given Ratings are between 0 and 10
     *
     * @return True if input is valid. False if input is invalid
     */
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
     * adds a Single Rating to a post. Rating is stored in a Hashmap.
     *
     * @param post Post, where the rating should be added.
     */
    public void addRating(PostDTO post) {
        System.out.println(post);
        System.out.println(inputTextNumber);
        ratingCollector.put(post, new RatingDTO(Integer.parseInt(inputTextNumber), currentUser, post));
    }

    /**
     * adds an avatar image to the model.
     *
     * @return true if upload was successful or false if given input was invalid
     */
    public boolean upload() {
        boolean res = false;
        Part uploadedFile = this.uploadedAvatar;
        InputStream bytes = null;
        if (uploadedFile != null) {
            try {
                bytes = uploadedFile.getInputStream();
                this.parsedAvatar = IOUtils.toByteArray(bytes);
                this.uploadedAvatarHash = calculateImageHash(this.parsedAvatar);

                this.persistedAvatar = new AvatarDTO(this.uploadedAvatarHash, uploadedFile.getContentType(), this.parsedAvatar);

                res = ctrl.addAvatar(this.persistedAvatar);
                this.persistedAvatar = ctrl.getAvatar(uploadedAvatarHash);

            } catch (IOException ex) {
                System.out.println("upload -> Exception");
            }
        }
        return res;
    }

    /**
     * checks the file for size and format. Filesize should be not bigger than
     * 2000000 Bytes or 2 Megabyte. The only valid FileFormat is JPEG.
     *
     * @param context FacesContext in which the File is validated
     * @param component UIComponent
     * @param value File to be validated
     * @throws ValidatorException Exception that is thrown when the input is
     * invalid.
     */
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

    /**
     * returns Arrays.HashCode(image)
     *
     * @param image
     * @return hashCode
     */
    private int calculateImageHash(byte[] image) {
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

    public String getInputTextNumber() {
        return inputTextNumber;
    }

    public void setInputTextNumber(String inputTextNumber) {
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

    public byte[] getParsedAvatar() {
        return parsedAvatar;
    }

    public void setParsedAvatar(byte[] parsedAvatar) {
        this.parsedAvatar = parsedAvatar;
    }

}
