/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import access.DTO.AvatarDTO;
import access.DTO.CommentDTO;
import access.DTO.PostDTO;
import access.DTO.RatingDTO;
import access.DTO.SystemUserDTO;
import entities.Avatar;
import entities.Comment;
import entities.Post;
import entities.Rating;
import entities.SystemUser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author philippnienhuser
 */
@Stateless
public class Persistence {

    @Inject
    private EntityManager em;

    /**
     * Deletes a Post from the database with given primary Key ID
     * @param id
     */
    public void deletePost(long id) {
        Post tmp = em.find(Post.class, id);
        em.remove(tmp);
    }

    /**
     * Adds a Post to the database. 
     * @param p PostDTO object to be added
     * @param currentUser SystemUserDTO object belonging to the post to be added
     */
    public void addPost(PostDTO p, SystemUserDTO currentUser) {
        Post post = p.toPost();

        //String url = post.getUrl();                 
        //Post addedPost = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", url).getSingleResult();
        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", currentUser.getUsername()).getSingleResult();

        su.getPosts().add(post);
        post.setAuthor(su);

        em.persist(post);
        em.merge(su);
        //em.merge(post);
    }

    /**
     * adds a RatingObject to the database.
     * @param post PostDTO post belonging to the  Rating
     * @param r RatingDTO Rating to be added
     * @param u SystemUserDTO User who submitted  the Rating
     */
    public void addRating(PostDTO post, RatingDTO r, SystemUserDTO u) throws EJBException{

        Rating rating = r.toRating();

        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", u.getUsername()).getSingleResult();
        Post p = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", post.getUrl()).getSingleResult();

        su.addRating(rating);
        p.addRating(rating);
        p.calcTotalRating();
        em.persist(rating);
        em.merge(p);
        em.merge(su);

    }

    /**
     * Deletes all Ratings for a given Username, so the User can distribute his 10 Points again
     * @param userName Username, which Ratings should be deleted
     */
    public void deleteRatings(String userName) {
        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", userName).getSingleResult();
        List<PostDTO> postList = getAllPosts();
        for (PostDTO p : postList) {
            for (RatingDTO r : p.getRatings()) {
                if (su.getUsername().equals(r.getUser().getUsername())) {
                    Post tmp = em.find(Post.class, p.getId());
                    tmp.removeRating(em.find(Rating.class, r.getId()));
                    tmp.calcTotalRating();
                    em.merge(tmp);
                }
            }
        }
        em.merge(su);
    }

    /**
     * Adds a Comment under the current Post to the Database
     * @param c CommentDTO comment to be added
     * @param post currentPost under which the Comment is added
     * @param currentUser UserObject of the comment creator
     */
    public void addComment(CommentDTO c, PostDTO post, SystemUserDTO currentUser) {
        //Post p = em.find(Post.class, c.getOwnerId());
        //p.getComments().add(c.toComment());
        //em.merge(p);
        Comment com = c.toComment();

        SystemUser su = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", currentUser.getUsername()).getSingleResult();
        Post p = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", post.getUrl()).getSingleResult();

        su.addComment(com);
        p.addComment(com);
        com.setAuthor(su);
        //com.setPost(p);
        em.persist(com);
        em.merge(p);
        em.merge(su);
    }

    /**
     * Adds a SystemUser to the database.
     * SystemUser is converted to SystemUserDTO
     * @param u SystemUserDTO object to be added
     */
    public void addSystemUser(SystemUserDTO u) {
        SystemUser tmp = u.toSystemUser();
        em.persist(tmp);
    }

    /**
     * Returns all Posts in the database as a List
     * @return List of PostDTO objects
     */
    public List<PostDTO> getAllPosts() {
        List<Post> tmp = em.createNamedQuery("Post.findAll", Post.class).getResultList();
        List<PostDTO> res = new ArrayList<>();
        for (Post p : tmp) {
            res.add(PostDTO.toPostDTO(p));
        }
        return res;
    }

    /**
     * Returns all Users in the Database as a List
     * @return List of SystemUser Objects
     */
    public List<SystemUserDTO> getAllUsers() {
        List<SystemUser> tmp = em.createNamedQuery("SystemUser.findAll", SystemUser.class).getResultList();
        List<SystemUserDTO> res = new ArrayList<>();
        for (SystemUser u : tmp) {
            res.add(SystemUserDTO.toSystemUserDTO(u));
        }
        return res;
    }

    /**
     * Updates Ratings of all the PostDTO objects in passed List.
     * totalRating is calculated for each Post and submitted to the database.
     * @param postList List ob PostDTO objects which ratings should be updated
     */
    public void updateRatings(List<PostDTO> postList) {
        for (PostDTO pdto : postList) {
            Post p = pdto.toPost();
            p.calcTotalRating();
            em.merge(p);
        }
    }

    /**
     * Searches for a matching SystemUser object in the database and updates its values with values of the passed UserObject.
     * @param u SystemUserDTO object which values are used to update the systemuser in the database
     */
    public void updateSystemUser(SystemUserDTO u) {
        List<SystemUser> tmp = em.createNamedQuery("SystemUser.findAll", SystemUser.class).getResultList();
        for (SystemUser systemUser : tmp) {
            if (systemUser.getUsername().equals(u.getUsername())) {
                systemUser.setUsername(u.getUsername());
                systemUser.setFname(u.getFname());
                systemUser.setLname(u.getLname());
                systemUser.setEmail(u.getEmail());
                systemUser.setAvatar(u.getAvatar().toAvatar());
                em.merge(systemUser);
                break;
            }
        }
        //SystemUser su = em.find(SystemUser.class, userId);
        //su = u.toSystemUser();
        //em.merge(u);
    }

    /**
     *  Converts a given AvatarDTO object to a Avatar object and adds it to the database.
     * @param avatarDTO Object to be persisted in the database.
     */
    public void addAvatar(AvatarDTO avatarDTO) {
        Avatar tmp = avatarDTO.toAvatar();
        em.persist(tmp);
    }

    /**
     * Returns Post with given URL from the database
     * @param url URL which is used to search for the Post in the database.
     * @return Post 
     */
    public Post getPost(String url) {
        Post res = null;
        try {
            res = em.createNamedQuery("Post.findByUrl", Post.class).setParameter("url", url).getSingleResult();
        } catch (EJBException e) {
            res = null;
        }
        return res;
    }

    /** 
     * Returns SystemUser with given username from the database
     * @param username is used to search for the User in the database.
     * @return SystemUser
     */
    public SystemUser getUser(String username) {
        SystemUser res = null;
        try {
            res = em.createNamedQuery("SystemUser.findByUsername", SystemUser.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            res = null;
        }
        return res;
    }

    /**
     * Returns SystemUser from the database with given userID
     * @param userId
     * @return 
     */
    public SystemUser getUser(Long userId) {
        return em.find(SystemUser.class, userId);
    }

    /**
     * Returns CommentList for given postURL and username
     * @param urlPost
     * @param username
     * @return
     */
    public List<Comment> getCommentList(String urlPost, String username) {
        return em.createNamedQuery("Comment.findByUrlAndUsername", Comment.class).setParameter("url", urlPost).setParameter("username", username).getResultList();
    }

    /**
     * Returns POSTDO object with given id from the database
     * @param id
     * @return
     */
    public PostDTO getPost(Long id) {
        return PostDTO.toPostDTO(em.find(Post.class, id));
    }

    /**
     * Returns AvatarDTO object with given avatarHash from the database.
     * @param uploadedAvatarHash
     * @return
     */
    public AvatarDTO getAvatar(int uploadedAvatarHash) {
        return AvatarDTO.toAvatarDTO(em.createNamedQuery("Avatar.findByHash", Avatar.class).setParameter("hash", uploadedAvatarHash).getSingleResult());
    }

}
